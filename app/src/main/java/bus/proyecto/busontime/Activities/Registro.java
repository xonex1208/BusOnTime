package bus.proyecto.busontime.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bus.proyecto.busontime.R;

import bus.proyecto.busontime.operaciones.Conectar;
import bus.proyecto.busontime.operaciones.Pasajero;

public class Registro extends AppCompatActivity {
    private String cad;
    private Spinner sp;
    private AppCompatActivity contexto = this;
    Conectar conectar;
    CallbackManager callbackManager;
    LoginButton lognbtn;
    EditText texNombre;
    EditText texApaterno;
    EditText texMaterno;
    EditText texEmail;
    EditText texPass;
    EditText texRepPass;
    Button bRegistrar;
    private TextInputLayout til_mail;
    private TextInputLayout til_nombreR;
    private TextInputLayout til_apPR;
    private TextInputLayout til_apMR;
    private TextInputLayout til_passR;
    private TextInputLayout til_repetirpassR;
    String nombreS, apPS, apMS, passS, repetirpassS;
    private boolean mail2 = false, nombre2 = false, apeP = false, apeM = false, pass = false, passR = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        conectar = new Conectar(this, "http://busontime.herokuapp.com");
        lognbtn = (LoginButton) findViewById(R.id.loginfb);
        lognbtn.setReadPermissions("email", "public_profile");
        callbackManager = (CallbackManager.Factory.create());
        lognbtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userid = loginResult.getAccessToken().getUserId();
                GraphRequest graphrqst = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        displayUserInfor(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name,last_name,email,id");
                graphrqst.setParameters(parameters);
                graphrqst.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(Registro.this, "Ha cancelado el registro", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        texNombre = (EditText) findViewById(R.id.regnombre);
        texApaterno = (EditText) findViewById(R.id.regapaterno);
        texMaterno = (EditText) findViewById(R.id.regamaterno);
        texEmail = (EditText) findViewById(R.id.regemail);
        texPass = (EditText) findViewById(R.id.regcontrasena);
        texRepPass = (EditText) findViewById(R.id.regrepetir_contrasena);
        bRegistrar = (Button) findViewById(R.id.btn_registro);
        til_mail = (TextInputLayout) findViewById(R.id.TIL_emailR);

        til_nombreR = (TextInputLayout) findViewById(R.id.TIL_nombreR);
        til_apPR = (TextInputLayout) findViewById(R.id.TIL_apPR);
        til_apMR = (TextInputLayout) findViewById(R.id.TIL_apMR);
        til_passR = (TextInputLayout) findViewById(R.id.TIL_contraR);
        til_repetirpassR = (TextInputLayout) findViewById(R.id.TIL_repetirContraR);
        bRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = texEmail.getText().toString();
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches() == false) {
                    til_mail.setError("Ingrese un correo valido");
                    mail2 = false;
                } else {
                    til_mail.setError(null);

                    mail2 = true;
                }
                nombreS = texNombre.getText().toString();
                apPS = texApaterno.getText().toString();
                apMS = texMaterno.getText().toString();
                passS = texPass.getText().toString();
                repetirpassS = texRepPass.getText().toString();
                String mensaje = "Campo obligatorio";
                if ((nombreS.isEmpty() && nombreS != null)) {
                    til_nombreR.setError(mensaje);
                    nombre2 = false;
                } else {
                    til_nombreR.setError(null);
                    nombre2 = true;

                }
                if ((apPS.isEmpty() && apPS != null)) {
                    til_apPR.setError(mensaje);
                    apeP = false;
                } else {
                    til_apPR.setError(null);
                    apeP = true;
                }

                if ((apMS.isEmpty() && apMS != null)) {
                    til_apMR.setError(mensaje);
                    apeM = false;
                } else {
                    til_apMR.setError(null);
                    apeM = true;
                }

                if ((passS.isEmpty() && passS != null)) {
                    til_passR.setError(mensaje);
                    pass = false;
                } else {
                    til_passR.setError(null);
                    pass = true;

                }

                if ((repetirpassS.isEmpty() && repetirpassS != null)) {
                    til_repetirpassR.setError(mensaje);
                    passR = false;
                } else {
                    if (!repetirpassS.equals(passS)) {
                        til_repetirpassR.setError("La contraseña es diferente");
                        passR = false;
                    } else {
                        til_repetirpassR.setError(null);
                        passR = true;
                    }

                }

                if(nombre2&&mail2&&apeP&&apeM&&pass&&passR){
                    Pasajero pasajero = new Pasajero();
                    pasajero.setNombre(texNombre.getText() + "");
                    pasajero.setApaterno(texApaterno.getText() + "");
                    pasajero.setAmaterno(texMaterno.getText() + "");
                    pasajero.setEmail(texEmail.getText() + "");
                    pasajero.setContraseña(texPass.getText() + "");
                    conectar.registrarPasajero(pasajero, onError, respuesta);
                }


            }
        });
    }


    public void displayUserInfor(JSONObject object) {
        String first_name, last_name, email, id;
        first_name = "";
        last_name = "";
        email = "";
        id = "";
        try {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            id = object.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView tv_name, tv_email, tv_id;
        tv_name = (TextView) findViewById(R.id.TV_name);
        tv_email = (TextView) findViewById(R.id.TV_email);
        tv_id = (TextView) findViewById(R.id.TV_id);
        tv_name.setText(first_name + " " + last_name);
        tv_email.setText("Email: " + email);
        tv_id.setText("ID: " + id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(contexto, "Error al registrarse, intentalo de nuevo", Toast.LENGTH_LONG).show();
        }
    };

    private Response.Listener<String> respuesta = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Toast.makeText(contexto, "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
            contexto.finish();
        }
    };
}
