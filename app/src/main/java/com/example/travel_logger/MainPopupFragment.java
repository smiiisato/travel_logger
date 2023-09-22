package com.example.travel_logger;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.travel_logger.databinding.FragmentMainPopupFragmentBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainPopupFragment extends BottomSheetDialogFragment {

    private FragmentMainPopupFragmentBinding binding;
    private EditText editTextInput;
    private int REQUEST_IMAGE_CAPTURE = 1;
    private static final String IMAGE_FILE_NAME = "captured_image.jpg";
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private ImageView imageView;
    private FusedLocationProviderClient fusedLocationClient;
    private Bitmap imageBitmap;
    private static final int REQUEST_CODE = 1234;
    private Uri uri = null;
    private int feeling = 0;
    private int weather = 0;
    private String diary = null;
    private String pictureUri = null;
    private Button normalFace;
    private Button happyFace;
    private Button sadFace;
    private Button sunButton;
    private Button cloudButton;
    private Button rainButton;


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
        normalFace = binding.normalFace;
        happyFace = binding.happyFace;
        sadFace = binding.sadFace;
        sunButton = binding.sunButton;
        cloudButton = binding.cloudButton;
        rainButton = binding.rainButton;

        editTextInput = binding.editTextInput;
        View view = binding.getRoot();


        Button button1 = binding.button1;
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                raiseDiary();
            }
        });

        Button button2 = binding.button2;
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                raiseFeeling();
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

        imageView = binding.imageView;


        return view;
    }


    public void onDetach(){
        super.onDetach();

        MapsActivity activity = (MapsActivity) getActivity();
        if(activity instanceof MapsActivity) {
            activity.addMarkerWithImage(uri);
            diary = binding.editTextInput.toString();
            pictureUri = uri.toString();
            activity.insertDatabase(feeling, weather, diary, pictureUri);
        }
    }

    private void raiseDiary() {
        // fill in this
        editTextInput.setVisibility(View.VISIBLE);
    }
    private void raiseCamera() {
        // fill in this
        // check permission

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");

            uri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void raiseWeather() {
        // fill in this
        LinearLayout buttonContainer2 = binding.buttonContainer2;
        buttonContainer2.setVisibility(View.VISIBLE);

        sunButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                raiseSunButton();
            }
        });

        cloudButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                raiseCloudButton();
            }
        });

        rainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                raiseRainButton();
            }
        });
    }

    private void raiseFeeling() {
        // fill in this
        LinearLayout buttonContainer = binding.buttonContainer;
        buttonContainer.setVisibility(View.VISIBLE);

        //Button normalFace = binding.normalFace;
        normalFace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                raiseNormalFace();
            }
        });

        //Button happyFace = binding.happyFace;
        happyFace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                raiseHappyFace();
            }
        });

        //Button sadFace = binding.sadFace;
        sadFace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                raiseSadFace();
            }
        });
    }

    private void raiseSunButton(){
        weather = 1;
        cloudButton.setVisibility(View.GONE);
        rainButton.setVisibility(View.GONE);
    }

    private void raiseCloudButton(){
        weather = 2;
        sunButton.setVisibility(View.GONE);
        rainButton.setVisibility(View.GONE);
    }

    private void raiseRainButton(){
        weather = 3;
        cloudButton.setVisibility(View.GONE);
        sunButton.setVisibility(View.GONE);
    }

    private void raiseNormalFace(){
        feeling = 1;
        happyFace.setVisibility(View.GONE);
        sadFace.setVisibility(View.GONE);
    }

    private void raiseHappyFace(){
        feeling = 2;
        normalFace.setVisibility(View.GONE);
        sadFace.setVisibility(View.GONE);
    }

    private void raiseSadFace(){
        feeling = 3;
        happyFace.setVisibility(View.GONE);
        normalFace.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
        //    imageBitmap = (Bitmap) data.getExtras().get("data");
        //    //saveBitmapToFile(imageBitmap, IMAGE_FILE_NAME);
        //    imageView.setImageBitmap(imageBitmap);
        //}
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            try {
                pictureUri = uri.toString();
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                imageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void saveBitmapToFile(Bitmap bitmap, String fileName) {
        File root = getContext().getExternalFilesDir(null);
        File file = new File(root, fileName);

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}