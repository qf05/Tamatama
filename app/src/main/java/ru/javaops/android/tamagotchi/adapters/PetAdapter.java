package ru.javaops.android.tamagotchi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.model.Pet;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private ItemClickListener clickListener;
    private List<Pet> pets;

    public PetAdapter(List<Pet> pets, ItemClickListener itemClickListener) {
        this.pets = pets;
        this.clickListener = itemClickListener;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pet, null, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        holder.petName.setText(pets.get(position).getName());
        holder.petLvl.setText(String.format(Locale.getDefault(),
                holder.itemView.getContext().getString(R.string.level_number),
                pets.get(position).getLvl()));
        int imageResource = 0;
        switch (pets.get(position).getPetsType()) {
            case CAT:
                imageResource = R.drawable.cat_icon;
                break;
            case DOG:
                imageResource = R.drawable.dog_icon;
                break;
            case CTHULHU:
                imageResource = R.drawable.cthulhu_icon;
                break;
        }
        holder.petIcon.setImageResource(imageResource);
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public Pet getItem(int id) {
        return pets.get(id);
    }

    class PetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView petName;
        TextView petLvl;
        ImageView petIcon;

        PetViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            petName = view.findViewById(R.id.pet_name);
            petLvl = view.findViewById(R.id.pet_lvl);
            petIcon = view.findViewById(R.id.pet_icon);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
