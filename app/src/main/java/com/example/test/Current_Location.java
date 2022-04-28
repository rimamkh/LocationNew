package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Current_Location extends AppCompatActivity {
    private TextView AddressText;
    private Button LocationButton;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        AddressText = findViewById(R.id.addressText);
        LocationButton = findViewById(R.id.locationButton);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(Current_Location.this);
        LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///check permission
                if(ActivityCompat.checkSelfPermission(Current_Location.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    getLocation();

                }else
                {
                    ActivityCompat.requestPermissions(Current_Location.this,new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });


    }
    public  void getLocation(){
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //initilaize location
                Location location=task.getResult();
                if(location != null){

                    try{
                        Geocoder geocoder=new Geocoder(Current_Location.this,
                                Locale.getDefault());
                        List<Address> addresses=geocoder.getFromLocation
                                (location.getLatitude(),location.getLongitude(),1);

                        ///we can find the getlocality,getLongitude and so on options
                        AddressText.setText("the location is "+addresses.get(0).getLocality()+"," +addresses.get(0).getCountryName());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}