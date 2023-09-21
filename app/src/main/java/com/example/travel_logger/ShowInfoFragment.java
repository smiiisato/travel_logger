package com.example.travel_logger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travel_logger.databinding.FragmentMainPopupFragmentBinding;
import com.example.travel_logger.databinding.FragmentShowInfoBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ShowInfoFragment extends BottomSheetDialogFragment {

    private FragmentShowInfoBinding binding;

    public ShowInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShowInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }
}