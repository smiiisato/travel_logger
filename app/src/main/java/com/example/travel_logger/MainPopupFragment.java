package com.example.travel_logger;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.travel_logger.databinding.ActivityMapsBinding;
import com.example.travel_logger.databinding.FragmentMainPopupFragmentBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.example.travel_logger.databinding.ActivityMapsBinding;
import android.content.Intent;
import android.widget.EditText;


public class MainPopupFragment extends BottomSheetDialogFragment {

    private FragmentMainPopupFragmentBinding binding;
    private EditText editTextInput;

    public MainPopupFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainPopupFragmentBinding.inflate(inflater, container, false);

        editTextInput = binding.editTextInput;

        Button button1 = binding.button1;
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                raiseDiary();
            }
        });

        Button button2 = binding.button2;
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                raiseMusic();
            }
        });
        Button button3 = binding.button3;
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                raiseWeather();
            }
        });
        Button button4 = binding.button4;
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                raiseCamera();
            }
        });

        return inflater.inflate(R.layout.fragment_main_popup_fragment , container , false);
    }

    private void raiseDiary() {
        // fill in this
        editTextInput.setVisibility(View.VISIBLE);
    }
    private void raiseCamera() {
        // fill in this
    }
    private void raiseWeather() {
        // fill in this
    }
    private void raiseMusic() {
        // fill in this
    }
}