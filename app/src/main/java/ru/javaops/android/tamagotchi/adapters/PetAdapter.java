package ru.javaops.android.tamagotchi.adapters;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.javaops.android.tamagotchi.R;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.CompareUtils;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    public interface ItemClickListener {
        void onItemClick(long itemId);
    }

    private ItemClickListener clickListener;
    private List<Pet> pets;
    private SparseBooleanArray deleteMap = new SparseBooleanArray();

    public PetAdapter(List<Pet> pets, ItemClickListener itemClickListener) {
        CompareUtils.sort(pets, 0);
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
        holder.petIcon.setImageResource(pets.get(position).getPetsType().getIconDrawableResource());
        if (clickListener == null) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(deleteMap.get(position, false));
        }
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public void updateData(List<Pet> pets, int spinnerPosition) {
        CompareUtils.sort(pets, spinnerPosition);
        this.pets.clear();
        this.pets.addAll(pets);
        deleteMap.clear();
        notifyDataSetChanged();
    }

    public List<Pet> getDeleteList() {
        List<Pet> deleteList = new ArrayList<>();
        for (int i = 0; i < pets.size(); i++) {
            if (deleteMap.get(i, false)) {
                deleteList.add(pets.get(i));
            }
        }
        return deleteList;
    }

    private void putDeleteMap(int position) {
        boolean check = deleteMap.get(position, false);
        deleteMap.put(position, !check);
        notifyItemChanged(position);
    }

    class PetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView petName;
        TextView petLvl;
        ImageView petIcon;
        CheckBox checkBox;

        PetViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            petName = view.findViewById(R.id.pet_name);
            petLvl = view.findViewById(R.id.pet_lvl);
            petIcon = view.findViewById(R.id.pet_icon);
            checkBox = view.findViewById(R.id.checkbox_delete);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClick(pets.get(getAdapterPosition()).getId());
            } else {
                putDeleteMap(getAdapterPosition());
            }
        }
    }
}
