package bus.proyecto.busontime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class Registro extends AppCompatActivity {
    private String cad;
    private Spinner sp;
    CallbackManager callbackManager;
    LoginButton lognbtn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

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
}
