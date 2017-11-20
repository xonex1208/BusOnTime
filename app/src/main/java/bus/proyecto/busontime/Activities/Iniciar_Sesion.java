package bus.proyecto.busontime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import bus.proyecto.busontime.R;

public class Iniciar_Sesion extends AppCompatActivity {
    private Button inciarBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar__sesion);
        inciarBtn = (Button) findViewById(R.id.btnini);
        inciarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Iniciar_Sesion.this,MapasContainer.class);
                startActivity(intent);
            }
        });
    }
}
