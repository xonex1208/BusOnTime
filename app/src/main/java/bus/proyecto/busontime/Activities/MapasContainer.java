package bus.proyecto.busontime.Activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bus.proyecto.busontime.Fragments.MapFragment;
import bus.proyecto.busontime.R;

public class MapasContainer extends AppCompatActivity {
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_container);
        if (savedInstanceState==null){
            currentFragment= new MapFragment(this);
            changeFragment(currentFragment);
        }
    }

    private void changeFragment (Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }
}
