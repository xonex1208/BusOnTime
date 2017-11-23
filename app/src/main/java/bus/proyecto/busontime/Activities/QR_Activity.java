package bus.proyecto.busontime.Activities;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import bus.proyecto.busontime.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QR_Activity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_);
    }
    public void btnEscanear(View v){
        mScannerView= new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    @Override
    public void handleResult(com.google.zxing.Result result) {
        Log.v("HandleResult",result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("resultado del scan");
        builder.setMessage(result.getText());
        AlertDialog alertDialog= builder.create();
        alertDialog.show();

        mScannerView.resumeCameraPreview(this);
    }
}
