package ru.javaops.android.tamagotchi.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.model.Pet;

@SuppressLint("InflateParams")
public class DeleteRVAdapter extends RecyclerView.Adapter<DeleteRVAdapter.PetViewHolder> {

    private ItemClickListener clickListener;
    private List<Pet> pets;

    public DeleteRVAdapter(List<Pet> pets) {
        this.pets = pets;
    }

    public class PetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView petName;
        TextView petLvl;
        ImageView petIcon;
        CheckBox checkBox;

        PetViewHolder(View view) {
            super(view);
            cv = view.findViewById(R.id.cvDelete);
            petName = view.findViewById(R.id.pet_name_del);
            petLvl = view.findViewById(R.id.pet_lvl_del);
            petIcon = view.findViewById(R.id.pet_icon_del);
            checkBox = view.findViewById(R.id.checkbox_delete);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    @NonNull
    @Override
    public DeleteRVAdapter.PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_delete, null, false);
        return new DeleteRVAdapter.PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteRVAdapter.PetViewHolder holder, int position) {
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

