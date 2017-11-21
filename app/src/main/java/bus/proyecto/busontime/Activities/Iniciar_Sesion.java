package bus.proyecto.busontime.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

import bus.proyecto.busontime.R;

public class Iniciar_Sesion extends AppCompatActivity {
    private Button inciarBtn;
    private TextInputLayout til_correo,til_pass;
    private EditText correo, pass;
    boolean corr= false, password= false;
    private  String clave;
    //Se usa la clase Pattern ya que contiene ciertas validaciones
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar__sesion);
        inciarBtn = (Button) findViewById(R.id.btnini);
        correo=(EditText) findViewById(R.id.editText_correo);
        pass=(EditText)findViewById(R.id.editText_pass);
        til_correo=(TextInputLayout)findViewById(R.id.TIL_email);
        til_pass=(TextInputLayout)findViewById(R.id.TIL_pass);


        inciarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Patterns.EMAIL_ADDRESS.matcher(correo.getText().toString()).matches()==false ){
                    til_correo.setError("Correo no registrado");
                    corr=false;
                }else{
                    corr=true;
                    til_correo.setError(null);
                }
                Pattern p= Pattern.compile("[0-9][0-9][0-9][0-9]");
                clave = pass.getText().toString();
                if (clave.length()<8|| clave.isEmpty()&&clave!=null){
                    til_pass.setError("La contraseña es incorrecta");
                    password = false;
                }else{
                    til_pass.setError(null);
                    password=true;
                }
                if (corr && password){
                    Intent intent = new Intent(Iniciar_Sesion.this,MapasContainer.class);
                    startActivity(intent);
                }

            }
        });
    }
}
