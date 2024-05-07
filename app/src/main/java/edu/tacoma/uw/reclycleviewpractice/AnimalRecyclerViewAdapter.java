package edu.tacoma.uw.reclycleviewpractice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.tacoma.uw.reclycleviewpractice.databinding.FragmentAnimalBinding;

public class AnimalRecyclerViewAdapter extends
        RecyclerView.Adapter<AnimalRecyclerViewAdapter.ViewHolder> {

    private final List<Animal> mValues;

    public AnimalRecyclerViewAdapter(List<Animal> animalList) {
        mValues = animalList;
    }

    @NonNull
    @Override
    public AnimalRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_animal, parent, false ));

    }

    @Override
    public void onBindViewHolder(@NonNull AnimalRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.setItem(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentAnimalBinding binding;
        public Animal mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentAnimalBinding.bind(view);
        }

        public void setItem(final Animal item) {
            mItem = item;
            binding.animalId.setText(Integer.valueOf(item.getId()).toString());
            binding.animalName.setText(item.getName());
            binding.animalName.setOnClickListener(view ->
            {
                AnimalListFragmentDirections.ActionAnimalListFragmentToAnimalDetailFragment directions =
                        AnimalListFragmentDirections.actionAnimalListFragmentToAnimalDetailFragment(item);

                Navigation.findNavController(mView)
                        .navigate(directions);
            });

        }
    }
}
