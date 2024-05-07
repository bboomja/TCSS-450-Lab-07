package edu.tacoma.uw.reclycleviewpractice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.tacoma.uw.reclycleviewpractice.databinding.FragmentAnimalListBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimalListFragment extends Fragment {

    private AnimalViewModel mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(AnimalViewModel.class);
        mModel.getAnimals();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animal_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        @NonNull FragmentAnimalListBinding binding = FragmentAnimalListBinding.bind(getView());

        mModel.addAnimalListObserver(getViewLifecycleOwner(), animalList -> {
            if (!animalList.isEmpty()) {
                binding.layoutRoot.setAdapter(
                        new AnimalRecyclerViewAdapter(animalList)
                );
            }
        });
    }
}