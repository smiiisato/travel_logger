package com.example.travel_logger;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.travel_logger.databinding.FragmentShowInfoBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class ShowInfoFragment extends BottomSheetDialogFragment {

    private FragmentShowInfoBinding binding;
    private DataEntity dataEntity;
    private int feeling;
    private int weather;
    private String diary;
    private Uri pictureUri;
    private TextView textView;
    private Button happyFace;
    private Button normalFace;
    private Button sadFace;
    private int id;
    private Button rainButton;
    private Button sunButton;
    private Button cloudButton;
    private ImageView imageView;

    public ShowInfoFragment(int id) {
        // Required empty public constructor
        this.id=id;
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
        textView = binding.textView;

        MapsActivity activity = (MapsActivity) getActivity();
        //id = ((MapsActivity) getActivity()).genId();

        if(activity instanceof MapsActivity) {
            // get info from database
            dataEntity = activity.getDataBase(id);
        }

        feeling = dataEntity.getFeeling();
        weather = dataEntity.getWeather();
        diary = dataEntity.getDiary();
        if(dataEntity.getPictureUri() != "null"){
            pictureUri = Uri.parse(dataEntity.getPictureUri());
        }

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

        rainButton = binding.rainButton2;
        sunButton = binding.sunButton2;
        cloudButton = binding.cloudButton2;
        if (weather == 1){
            sunButton.setVisibility(View.VISIBLE);
        }else if (weather== 2){
            cloudButton.setVisibility(View.VISIBLE);
        }else if(weather == 3){
            rainButton.setVisibility(View.VISIBLE);
        }

        imageView = binding.imageView;
        Glide.with(this)
                .load(pictureUri)
                .into(imageView);

        return view;
    }
}