package bus.proyecto.busontime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import bus.proyecto.busontime.R;
import bus.proyecto.busontime.operaciones.Conectar;
import bus.proyecto.busontime.operaciones.SVars;

public class MainActivity extends AppCompatActivity {
    private View btn;
    private View btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().hide();
        btn2= (Button) findViewById(R.id.btn_login);
        btn = (Button) findViewById(R.id.btn_registro);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Registro.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this,Iniciar_Sesion.class);
                startActivity(intent);
            }
        });
        if(SVars.conectar==null){
            SVars.conectar=new Conectar(this,"http://busontime.herokuapp.com");
        }
    }
}
