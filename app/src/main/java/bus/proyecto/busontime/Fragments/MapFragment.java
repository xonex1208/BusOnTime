package bus.proyecto.busontime.Fragments;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import bus.proyecto.busontime.R;


public class MapFragment extends Fragment implements OnMapReadyCallback{
    private View rootView;
    private MapView mapView;
    private GoogleMap gMap;
    private Address address;
    private Geocoder geocoder;
    private  MarkerOptions marker;

    public MapFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_map, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView=(MapView)rootView.findViewById(R.id.map);
        if (mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng lugar = new LatLng(18.95952686779156, -99.58184854534306);
        LatLng sydney = new LatLng(-34, 151);
        marker= new MarkerOptions();
        marker.position(lugar);
        marker.title("Parada 1");
        marker.draggable(false);
        marker.snippet("Para ubicada en Tenancingo");
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_paradas));
        gMap.addMarker(marker);

        //Para customizar el punto
        //El nivel 15 es de calles, 10 de ciudades,etc.
        //tilt de la camara hacia X grados
        CameraPosition cameraPosition= new CameraPosition.Builder()
                .target(lugar)
                .zoom(16) //limit -->21
                .bearing(300)  //orientacion de la camara hacia el este 0--365°
                .tilt(30)  //entre 0 y 90
                .build();
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}
