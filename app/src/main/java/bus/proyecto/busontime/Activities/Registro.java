package bus.proyecto.busontime.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private AppCompatActivity contexto=this;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        conectar=new Conectar(this,"http://busontime.herokuapp.com");
        lognbtn = (LoginButton)findViewById(R.id.loginfb);
        lognbtn.setReadPermissions("email","public_profile");
        callbackManager = (CallbackManager.Factory.create());
        lognbtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userid=loginResult.getAccessToken().getUserId();
                GraphRequest graphrqst= GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        displayUserInfor(object );
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields","first_name,last_name,email,id");
                graphrqst.setParameters(parameters);
                graphrqst.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(Registro.this,"Ha cancelado el registro",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        texNombre=(EditText)findViewById(R.id.regnombre);
        texApaterno=(EditText)findViewById(R.id.regapaterno);
        texMaterno=(EditText)findViewById(R.id.regamaterno);
        texEmail=(EditText)findViewById(R.id.regemail);
        texPass=(EditText)findViewById(R.id.regcontrasena);
        texRepPass=(EditText)findViewById(R.id.regrepetir_contrasena);
        bRegistrar=(Button)findViewById(R.id.btn_registro);
        bRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pasajero pasajero=new Pasajero();
                pasajero.setNombre(texNombre.getText()+"");
                pasajero.setApaterno(texApaterno.getText()+"");
                pasajero.setAmaterno(texMaterno.getText()+"");
                pasajero.setEmail(texEmail.getText()+"");
                pasajero.setContraseña(texPass.getText()+"");
                conectar.registrarPasajero(pasajero,onError,respuesta);
            }
        });
    }
    public void displayUserInfor(JSONObject object){
        String first_name,last_name,email,id;
        first_name="";
        last_name="";
        email="";
        id="";
        try {
            first_name=object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            id = object.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Bundle datos = new Bundle();
        //TextView nombre;
        // nombre=(TextView) findViewById(R.id.TV_name);

        TextView tv_name,tv_email,tv_id;
        tv_name=(TextView)findViewById(R.id.TV_name);
        tv_email=(TextView)findViewById(R.id.TV_email);
        tv_id=(TextView)findViewById(R.id.TV_id);
        tv_name.setText(first_name+" "+last_name);
        tv_email.setText("Email: "+email);
        tv_id.setText("ID: "+id);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private Response.ErrorListener onError=new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(contexto, "Error al registrarse", Toast.LENGTH_LONG).show();
        }
    };

    private Response.Listener<String> respuesta=new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Toast.makeText(contexto, "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
            contexto.finish();
        }
    };
}
