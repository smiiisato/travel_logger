package com.example.travel_logger;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.travel_logger.databinding.FragmentMainPopupFragmentBinding;
import com.example.travel_logger.databinding.FragmentShowInfoBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ShowInfoFragment extends BottomSheetDialogFragment {

    private FragmentShowInfoBinding binding;
    private DataEntity dataEntity;
    private int id;
    private int feeling;
    private int weather;
    private String diary;
    private Uri pictureUri;
    private TextView textView;
    private Button happyFace;
    private Button normalFace;
    private Button sadFace;

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

        // idを取得

        MapsActivity activity = (MapsActivity) getActivity();
        if(activity instanceof MapsActivity) {
            // get info from database
            dataEntity = activity.getDataBase(id);
        }

        feeling = dataEntity.getFeeling();
        weather = dataEntity.getWeather();
        diary = dataEntity.getDiary();
        pictureUri = Uri.parse(dataEntity.getPictureUri());

        // textに表示
        textView.setText(diary);

        happyFace = binding.happyFace2;
        normalFace = binding.normalFace2;
        sadFace = binding.sadFace2;
        // feelingの値に応じてアイコン可視化
        if(feeling == 1){
            happyFace.setVisibility(View.VISIBLE);
        }else if (feeling == 2){
            normalFace.setVisibility(View.VISIBLE);
        }else if(feeling == 3){
            sadFace.setVisibility(View.VISIBLE);
        }

        return view;
    }
}