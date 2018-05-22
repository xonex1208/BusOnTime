package bus.proyecto.busontime.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import bus.proyecto.busontime.Fragments.MapFragment;
import bus.proyecto.busontime.R;
import bus.proyecto.busontime.operaciones.Conectar;
import bus.proyecto.busontime.operaciones.Pasajero;
import bus.proyecto.busontime.operaciones.SVars;

public class MapasContainer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Fragment currentFragment;
    private TextView datosUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_container);
        //Datos del usuario, Cachandolo.


        if (savedInstanceState==null){
            currentFragment= new MapFragment();
            changeFragment(currentFragment);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState==null){
            currentFragment= new MapFragment();
            SVars.conectar = new Conectar(this, "http://busontime.herokuapp.com");
            changeFragment(currentFragment);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);


        datosUsers=(TextView)header.findViewById(R.id.textViewND);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String texto= bundle.getString("datosUsu");
            //Toast.makeText(this,texto,Toast.LENGTH_LONG).show();
            datosUsers.setText("Beinvend buapo");
        }
    }

    private void changeFragment (Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
