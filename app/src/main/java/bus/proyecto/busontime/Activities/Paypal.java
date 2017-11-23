package bus.proyecto.busontime.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class Paypal extends AppCompatActivity {
    TextView n_response;
    String pagar;
    PayPalConfiguration n_configuration;
    String n_paypalClientID = "AQXRF88MY7wYYULOpPcRMq-cw-r8QfLNcojKeivaEaCxFFZOK5jZcTW8aBTOFJh-O09CcR3OpKLO9ZvD";
    Intent n_service;
    int paypalRequestCode = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        n_response = (TextView) findViewById(R.id.response);
        n_configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(n_paypalClientID);
        n_service = new Intent(this, PayPalService.class);
        n_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, n_configuration);
        startService(n_service);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n_response = (TextView) findViewById(R.id.response);
        n_configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(n_paypalClientID);
        n_service = new Intent(this, PayPalService.class);
        n_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, n_configuration);
        startService(n_service);
    }

    public void pago(View view){
        PayPalPayment payment = new PayPalPayment(new BigDecimal(10), "USD", ".", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, n_configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, paypalRequestCode);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == paypalRequestCode){
            if(resultCode == Activity.RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    String state = confirmation.getProofOfPayment().getState();
                    if(state.equals("approved")){
                        n_response.setText("Pago aprobado");
                    }else{
                        n_response.setText("Error en pago");
                    }
                }
                else{
                    n_response.setText("El pago es nulo");
                }
            }
        }
    }


}
