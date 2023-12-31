package com.example.travel_logger;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.travel_logger.databinding.ActivityMapsBinding;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import android.Manifest;
import android.provider.MediaStore;
import android.widget.Button;
import android.view.View;

import android.app.Activity;
import android.graphics.Bitmap;

import com.example.travel_logger.MainPopupFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.model.PolylineOptions;
import android.location.Location;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationRequest;
import android.os.Looper;

import java.io.IOException;
import java.util.Random;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private PolylineOptions mPolylineOptions;
    private DataBase db;
    private DataDao dataDao;
    private int id;
    private Random rand = new Random();
    private int newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mPolylineOptions = new PolylineOptions();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button addButton = binding.myButton;
        //addButton.setBackgroundColor(FA8000);
        //int color = ContextCompat.getColor(getApplicationContext(), R.color.dark_orange);
        //addButton.set
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addFragment();
            }
        });

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    LatLng newPoint = new LatLng(location.getLatitude(), location.getLongitude());
                    mPolylineOptions.add(newPoint);
                    mMap.addPolyline(mPolylineOptions);
                }
            }
        };

        db = Room.databaseBuilder(getApplicationContext(),
                DataBase.class, "database-name").allowMainThreadQueries().build();
        dataDao = db.dataDao();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private void addFragment() {
        MainPopupFragment frag = new MainPopupFragment();
        frag.show(getSupportFragmentManager(), "Ashwani");
    }

    private void addInfoFragment(int id) {
        // show database fragment
        ShowInfoFragment frag2 = new ShowInfoFragment(id);
        frag2.show(getSupportFragmentManager(), "Ashwani");
    }

    private boolean checkLocationPermission() {
        // 既に位置情報のパーミッションが付与されている場合
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            // 位置情報のパーミッションをリクエスト
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (checkLocationPermission()) {
            mMap.setMyLocationEnabled(true);

            startLocationUpdates();
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                //mMap.addMarker(new MarkerOptions().position(currentLocation).title("現在位置"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void addMarkerWithImage(Uri uri, int id) {
        Bitmap imageBitmap = null;
        try {
            imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap smallBitmap = Bitmap.createScaledBitmap(imageBitmap, 100, 100, false); // 100x100にリサイズ
        Bitmap roundBitmap = getRoundedBitmap(smallBitmap,10);
        Bitmap borderedBitmap = getBorderBitmap(roundBitmap, 10);

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                newId = id;

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(currentLocation)
                        .icon(BitmapDescriptorFactory.fromBitmap(borderedBitmap)));
                marker.setTag(newId);

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        // マーカーがクリックされた時のアクション
                        newId = (int) marker.getTag();
                        addInfoFragment(newId);
                        return true;
                    }
                });
            }
        });
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000); // 10 seconds
        locationRequest.setFastestInterval(5000); // 5 seconds
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.getMainLooper());
    }

    //set insert data function
    public void insertDatabase(int feeling, int weather, String diary, String pictureUri){
        DataEntity dataEntity = new DataEntity();
        dataEntity.feeling = feeling;
        dataEntity.weather = weather;
        dataEntity.diary = diary;
        dataEntity.pictureUri = pictureUri;
        dataDao.insertData(dataEntity);
    }

    public DataEntity getDataBase(int id){
        //DataBase db = Room.databaseBuilder(getApplicationContext(),
        //        DataBase.class, "data-base").allowMainThreadQueries().build();

        DataEntity dataEntity = dataDao.getDataById(id);

        return dataEntity;
    }

    public int getLastId(){
        id = dataDao.getLatestId();
        return id;
    }

    public Bitmap getRoundedBitmap(Bitmap bitmap, int roundPx){
        if (bitmap == null) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(),
                bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public Bitmap getBorderBitmap(Bitmap bitmap, int borderWidth){
        Bitmap bmpWithBorder = Bitmap.createBitmap(bitmap.getWidth()
                        + borderWidth * 2, bitmap.getHeight() + borderWidth * 2,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpWithBorder);
        RectF rectBorder = new RectF(0, 0, bitmap.getWidth()+ borderWidth * 2,
                bitmap.getHeight()+ borderWidth * 2);
        final RectF rectFBorder = new RectF(rectBorder);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        int color = 0xfffa8000;
        paint.setColor(color);
        canvas.drawRoundRect(rectFBorder, borderWidth, borderWidth, paint);
        canvas.drawBitmap(bitmap, borderWidth, borderWidth, null);
        return bmpWithBorder;
    }

}