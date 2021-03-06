package bus.proyecto.busontime.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import bus.proyecto.busontime.Activities.PedirParadas;
import bus.proyecto.busontime.R;
import bus.proyecto.busontime.operaciones.Conectar;
import bus.proyecto.busontime.operaciones.Cordenadas;
import bus.proyecto.busontime.operaciones.Marcador;
import bus.proyecto.busontime.operaciones.SVars;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    private View rootView;
    private MapView mapView;
    private GoogleMap gMap;
    private Address address;
    private Geocoder geocoder;
    private MarkerOptions marker;
    private Conectar conectar;
    private ArrayList<Marcador> marcadores = new ArrayList();
    private Context contexto;

    public MapFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) rootView.findViewById(R.id.map);

        if (conectar == null) {
            conectar = SVars.conectar;
            contexto = conectar.getContexto();
        }
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setCompassEnabled(false);
        gMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gMap.setMyLocationEnabled(true);
        LatLng lugar = new LatLng(18.95952686779156, -99.58184854534306);
        LatLng sydney = new LatLng(-34, 151);
        marker= new MarkerOptions();
        marker.position(lugar);
        marker.title("Parada 1");
        marker.draggable(false);
        marker.snippet("Para ubicada en Tenancingo");
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_paradas));
        gMap.addMarker(marker);

        if(SVars.posicion==null) {
            Location pos = gMap.getMyLocation();
            if(pos!=null) {
                LatLng posicion = new LatLng(pos.getLatitude(), pos.getLongitude());
                SVars.posicion = new CameraPosition.Builder()
                        .target(posicion)
                        .zoom(16) //limit -->21
                        .bearing(0)  //orientacion de la camara hacia el este 0--365°
                        .tilt(30)  //entre 0 y 90
                        .build();

            }else{
                SVars.posicion = new CameraPosition.Builder()
                        .target(lugar)
                        .zoom(16) //limit -->21
                        .bearing(0)  //orientacion de la camara hacia el este 0--365°
                        .tilt(30)  //entre 0 y 90
                        .build();
            }

        }

        gMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                SVars.posicion=gMap.getCameraPosition();
            }
        });
        //Para customizar el puntod
        //El nivel 15 es de calles, 10 de ciudades,etc.
        //tilt de la camara hacia X grados
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(SVars.posicion));
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                startActivity(new Intent(contexto, PedirParadas.class));
                return true;
            }
        });

        solicitar.start();
    }

    public void mostrarAutobuses(String respuesta) {
        ArrayList<Cordenadas> autobuses=conectar.convertirJson(respuesta);
        int i=0;
        Log.d("Actualizando","cordenadas obtenidas");
        for (;i<marcadores.size();i++){
            if(i>=autobuses.size()){
                for(;i<marcadores.size();i++){
                    marcadores.get(i).getMarcador().remove();
                    marcadores.remove(i);
                }
            }else{
                if(autobuses.get(i).getId().equals(marcadores.get(i).getId())){
                    marcadores.get(i).setPosicion(autobuses.get(i));
                }else{
                    marcadores.get(i).getMarcador().remove();
                    marcadores.remove(i);
                    i--;
                }
            }
        }
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.draggable(false);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        for(;i<autobuses.size();i++){
            Cordenadas cor=autobuses.get(i);
            markerOptions.position(new LatLng(cor.getLatitud(),cor.getLongitud()));
            Marker maker=gMap.addMarker(markerOptions);
            marcadores.add(new Marcador(cor.getId(),maker));
        }
        Log.d("Actualizado","Los marcadores se actualizaron");
    }

    Response.Listener<String> actualizarAutobuses=new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            mostrarAutobuses(response);
        }
    };

    Thread solicitar=new Thread(){
        @Override
        public void run() {
            try{
                while(true){
                    conectar.get("/OBTENERCORDENADAS2",actualizarAutobuses);
                    sleep(1000);
                }
            }catch (Exception e){
                Log.d("Error",e.getMessage());
            }
        }
    };
}
