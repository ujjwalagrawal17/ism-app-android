package ismapp.iitism.cyberlabs.com.ismapp.Authentication.Forgot_password.View;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ismapp.iitism.cyberlabs.com.ismapp.Authentication.Forgot_password.Model.Otp_Response_Model;
import ismapp.iitism.cyberlabs.com.ismapp.Authentication.Forgot_password.Presenter.Presenter_Interface;
import ismapp.iitism.cyberlabs.com.ismapp.Authentication.Forgot_password.Presenter.presenter_imple;
import ismapp.iitism.cyberlabs.com.ismapp.Authentication.Forgot_password.Provider.Retrofit_forgot_implementaion;
import ismapp.iitism.cyberlabs.com.ismapp.R;

public class forgot_email_view extends AppCompatActivity implements View_interface {
     EditText ed_email;
     Button bt_send;
     ProgressBar progressBar;
     Dialog dialog;
     boolean connected;
     Presenter_Interface presenter_interface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        initialise();


    }

    private void initialise() {
        ed_email = (EditText)findViewById(R.id.forgot_email);
        bt_send = (Button) findViewById(R.id.send_email);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        dialog = new Dialog(this);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
    }

    @Override
    public void showProgressBar(boolean check) {
        if(check == true){
            progressBar.setVisibility(View.GONE);
        }
         else{
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showOtpResponse(Otp_Response_Model Otp_Response_Model) {
        if(Otp_Response_Model.isSuccess()){
            //intent to otp page
        }else{
            Toast.makeText(forgot_email_view.this,Otp_Response_Model.getMessage().toString(),Toast.LENGTH_LONG);
        }
    }
    public void proceed(View v) {
        String email = ed_email.getText().toString().trim();
       if(emailInvalid(email)){
            Toast.makeText(this, "ENTER CORRECT EMAIL ID!",
                    Toast.LENGTH_LONG).show();
        }
        else {
              presenter_interface = new presenter_imple(new Retrofit_forgot_implementaion(), this);
              presenter_interface.getResponse(email);


            hideKeyboard();
        }

    }
    @Override
    public void checkConnection() {
        //check internet connection
        if(connected == false){
            //show dialog box;
        }


    }

    @Override
    public void showError(String msg) {
             Toast.makeText(this,msg,Toast.LENGTH_SHORT);
    }
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public boolean emailInvalid(String email) {
        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        boolean a = matcher.matches();
        return !a;
    }
}
