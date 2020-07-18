package com.example.healthapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.example.healthapplication.R;

import java.util.ArrayList;

import static android.os.Looper.getMainLooper;

public class MapsFragment extends Fragment  implements  OnMapReadyCallback{

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST = 500;
    private FusedLocationProviderClient mFusedLocationClient;
    private Boolean mLocationPermissionsGranted = false;
    private static final String TAG = "Map";
    private static final long FASTEST_INTERVAL=1000 * 40 * 1;
    private static final long INTERVAL= 1000*50*1;
    ArrayList<LatLng> listPoints;
    private LocationRequest mLocationRequest;
    MarkerOptions markerOptions= new MarkerOptions();
    private int flag=0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate View");
        View view=inflater.inflate(R.layout.fragment_walk, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated");
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listPoints = new ArrayList<>();
        callPermissions();
    }


    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public void callPermissions(){
        Log.e(TAG,"callPermissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        Permissions.check(requireContext()/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                Log.e(TAG,"callPermissions");
                requestLocationUpdates();
            }
            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions){
                super.onDenied(context, deniedPermissions);
                callPermissions();
            }
        });
    }
    public void requestLocationUpdates(){
        Log.e(TAG,"requestLocationUpdates");
        if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient = new FusedLocationProviderClient(requireActivity());
            mLocationRequest = new LocationRequest();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
            mLocationRequest.setInterval(INTERVAL);
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    LatLng a= new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                    Log.e(TAG,"@###############33");
                    listPoints.add(a);
                    if(listPoints.size() !=0){
                        markerOptions.position(listPoints.get(listPoints.size()-1));
                        if(flag==0) {
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            flag=1;
                        }
                        else if(flag==1){
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            flag=2;
                        }
                        else{
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            flag=0;
                        }
                        mMap.addMarker(markerOptions);
                    }
                    moveCamera(a,15f);
                    Log.e(TAG, "lat: "+locationResult.getLastLocation().getLatitude()+" long: "+ locationResult.getLastLocation().getLongitude());
                }
            }, getMainLooper());  //execute the code on main UI
        }

        else{
            callPermissions();
        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.e(TAG,"onMapReady");
        // Add a marker in Sydney and move the camera
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){
            @Override
            public void onMapLongClick(LatLng latLng){
                //Reset when more than 2
                if(listPoints.size() ==2){
                    listPoints.clear();
                    mMap.clear();
                }
                //Save first point selected
                listPoints.add(latLng);
                //Create Marker
                MarkerOptions markerOptions= new MarkerOptions();
                markerOptions.position(latLng);

                if(listPoints.size()==1){
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else{
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
                mMap.addMarker(markerOptions);

            }
        });
    }

}