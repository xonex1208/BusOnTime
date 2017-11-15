package bus.proyecto.busontime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import bus.proyecto.busontime.operaciones.Conectar;
import bus.proyecto.busontime.operaciones.Cordenadas;
import bus.proyecto.busontime.operaciones.Marcador;

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private GoogleMap mMap;
    private MapView mapView;
    Context contexto=this;
    Conectar conectar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        conectar=new Conectar(this,"http://busontime.herokuapp.com");

        mapView=(MapView)findViewById(R.id.map);
        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,ConfiguracionBus.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //colocar opciones
        switch (id){
            case R.id.bmenuvaloranos:
                startActivity(new Intent(this,Valoranos.class));
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions marcador=new MarkerOptions();
        marcador.draggable(true);

        LatLng uapt = new LatLng(19.196516429831817, -99.51750218868256);
        //mMap.addMarker(new MarkerOptions().position(uapt).title("Hola we").draggable(true));
        mMap.setMinZoomPreference(0);
        mMap.setMaxZoomPreference(20);
        CameraPosition camera = new CameraPosition.Builder()
                .target(uapt)
                .zoom(15)
                .bearing(90)
                .tilt(45)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
        solicitar.start();
        uapt=null;
    }

    public void mostrarAutobuses(String respuesta) {
        ArrayList<Cordenadas> autobuses=conectar.convertirJson(respuesta);
        Log.d("Actualizando","cordenadas obtenidas");
        mMap.clear();
        for (int i=0; i < autobuses.size(); i++) {
            MarkerOptions marcador=new MarkerOptions();
            LatLng posicion=new LatLng(autobuses.get(i).getLatitud(),autobuses.get(i).getLongitud());
            marcador.position(posicion);
            marcador.title(autobuses.get(i).getId()+"");
            mMap.addMarker(marcador);
            Log.d("Añadido","Cordenada: "+autobuses.get(i).desp());
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
