package ru.javaops.android.tamagotchi.adapters;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.javaops.android.tamagotchi.BR;
import ru.javaops.android.tamagotchi.model.Pet;
import ru.javaops.android.tamagotchi.utils.CompareUtils;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    public interface ItemClickListener {
        void onItemClick(long itemId);
    }

    private ItemClickListener clickListener;
    private List<Pet> pets;
    private SparseBooleanArray deleteMap = new SparseBooleanArray();
    private int layout;

    public PetAdapter(List<Pet> pets, ItemClickListener itemClickListener, @LayoutRes int layout) {
        if (pets != null) {
            CompareUtils.sort(pets, 0);
        } else {
            pets = new ArrayList<>();
        }
        this.pets = pets;
        this.clickListener = itemClickListener;
        this.layout = layout;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout, null, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        final Pet item = pets.get(position);
        holder.getBinding().setVariable(BR.pet, item);
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public void updateData(List<Pet> pets, int spinnerPosition) {
        this.pets.clear();
        if (pets != null) {
            this.pets.addAll(pets);
            CompareUtils.sort(pets, spinnerPosition);
            notifyDataSetChanged();
        }
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

    public void clearDeleteMap() {
        deleteMap.clear();
    }

    private void putDeleteMap(int position) {
        boolean check = deleteMap.get(position, false);
        deleteMap.put(position, !check);
        notifyItemChanged(position);
    }

    class PetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ViewDataBinding binding;

        PetViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
            view.setOnClickListener(this);
        }

        public ViewDataBinding getBinding() {
            return binding;
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
