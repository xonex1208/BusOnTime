package bus.proyecto.busontime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class PedirParadas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_paradas);
    }

    public void resp(){
        Toast.makeText(this, "Ha solictado parada", Toast.LENGTH_LONG).show();
    }
}
