package ru.javaops.android.tamagotchi.adapters;

import android.annotation.SuppressLint;
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
import ru.javaops.android.tamagotchi.model.Pet;

public class ChangeRVAdapter extends RecyclerView.Adapter<ChangeRVAdapter.PetViewHolder> {

    private ItemClickListener clickListener;
    private List<Pet> pets;

    public ChangeRVAdapter(List<Pet> pets) {
        this.pets = pets;
    }

    public class PetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView petName;
        TextView petLvl;
        ImageView petIcon;

        PetViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            cv = view.findViewById(R.id.cvChange);
            petName = view.findViewById(R.id.pet_name);
            petLvl = view.findViewById(R.id.pet_lvl);
            petIcon = view.findViewById(R.id.pet_icon);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
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
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_change_pet, null, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        holder.petName.setText(pets.get(position).getName());
        holder.petLvl.setText(String.format(Locale.getDefault(), "%d lvl", pets.get(position).getLvl()));
        switch (pets.get(position).getPetsType()) {
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
