package ru.javaops.android.tamagotchi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.enums.PetsType;
import ru.javaops.android.tamagotchi.model.Pet;

public class ChangeRVAdapter extends RecyclerView.Adapter<ChangeRVAdapter.PetViewHolder> {

    private static ItemClickListener mClickListener;
    private Context context;
    private List<Pet> pets;

    public ChangeRVAdapter(Context context, List<Pet> pets) {
        this.context = context;
        this.pets = pets;
    }

    public void changeList(List<Pet> pets) {
        this.pets = pets;
        notifyDataSetChanged();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView petName;
        TextView petLvl;
        ImageView petIcon;

        PetViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = itemView.findViewById(R.id.cvChange);
            petName = itemView.findViewById(R.id.pet_name);
            petLvl = itemView.findViewById(R.id.pet_lvl);
            petIcon = itemView.findViewById(R.id.pet_icon);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public Pet getItem(int id) {
        return pets.get(id);
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_change_pet, null, false);
        return new PetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        holder.petName.setText(pets.get(position).getName());
        holder.petLvl.setText(String.format(Locale.getDefault(), "%d lvl", pets.get(position).getLvl()));
        PetsType petsType = PetsType.valueOf(pets.get(position).getType());
        switch (petsType) {
            case CAT:
                holder.petIcon.setImageResource(R.drawable.cat_icon);
                break;
            case DOG:
                holder.petIcon.setImageResource(R.drawable.dog_icon);
                break;
            case CTHULHU:
                holder.petIcon.setImageResource(R.drawable.cthulhu_icon);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }
}
