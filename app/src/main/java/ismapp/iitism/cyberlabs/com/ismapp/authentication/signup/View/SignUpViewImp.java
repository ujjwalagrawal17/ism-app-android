package ismapp.iitism.cyberlabs.com.ismapp.authentication.signup.View;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import ismapp.iitism.cyberlabs.com.ismapp.authentication.signup.Model.SignUpResponseModel;
import ismapp.iitism.cyberlabs.com.ismapp.authentication.signup.Presenter.SignUpPresenter;
import ismapp.iitism.cyberlabs.com.ismapp.authentication.signup.Presenter.SignUpPresenterImp;
import ismapp.iitism.cyberlabs.com.ismapp.authentication.signup.Provider.SignUpProviderImp;
import ismapp.iitism.cyberlabs.com.ismapp.authentication.verifyotp.View.VerifyOtpViewImp;
import ismapp.iitism.cyberlabs.com.ismapp.R;

public class SignUpViewImp extends AppCompatActivity implements SignUpView {
    @BindView(R.id.signup_name)
    EditText name;
    @BindView(R.id.et_signup_email)
    EditText email;
    @BindView(R.id.et_signup_pass)
    EditText pass;
    @BindView(R.id.btn_signup_next)
    Button next;
    String e;

    @BindView(R.id.signup_coordlay)
    CoordinatorLayout clayout;
    @BindView(R.id.signup_name_lay)
    TextInputLayout nameLay;
    @BindView(R.id.signup_email_lay)
    TextInputLayout emailLay;
    @BindView(R.id.signup_pass_lay)
    TextInputLayout passLay;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        // nameLay.setErrorEnabled(true);
        //  emailLay.setErrorEnabled(true);
        //  passLay.setErrorEnabled(true);
        alertDialog= new AlertDialog.Builder(this).setView(LayoutInflater.from(this).inflate(R.layout.progress_bar,null)).setCancelable(false).create();
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(name.getText().toString().trim().isEmpty())
                    nameLay.setError("Enter the Name");
                if(email.getText().toString().trim().isEmpty())
                    emailLay.setError("Enter the Email-Id");
                if(!name.getText().toString().trim().isEmpty()&&!email.getText().toString().trim().isEmpty()&&pass.getText().toString().length()>=6)
                {next.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));next.setClickable(true);}
                else
                {next.setBackgroundColor(getResources().getColor(R.color.titleColorLight));next.setClickable(false);}



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!name.getText().toString().trim().isEmpty()&&!email.getText().toString().trim().isEmpty()&&pass.getText().toString().length()>=6)
                {next.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));next.setClickable(true);}
                else
                {
                    next.setBackgroundColor(getResources().getColor(R.color.titleColorLight));next.setClickable(false);}

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!name.getText().toString().trim().isEmpty()&&!email.getText().toString().trim().isEmpty()&&pass.getText().toString().length()>=6)
                {next.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));next.setClickable(true);}
                else
                {next.setBackgroundColor(getResources().getColor(R.color.titleColorLight));next.setClickable(false);}

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpPresenter signUpPresenter =new SignUpPresenterImp(SignUpViewImp.this,new SignUpProviderImp(),
                        email.getText().toString().trim(),
                        name.getText().toString().trim(),
                        pass.getText().toString());

              e=email.getText().toString().trim();
                name.setText("");
                email.setText("");
                pass.setText("");
                signUpPresenter.getSignUpResponse();

            }
        });

    }

    @Override
    public void showProgressBar(boolean show) {
        if(show)
            alertDialog.show();
        else
            alertDialog.dismiss();

    }

    @Override
    public void setIntent(SignUpResponseModel signUpResponseModel) {
        if(!signUpResponseModel.getSuccess())
            Snackbar.make(clayout,signUpResponseModel.getMessage(),Snackbar.LENGTH_LONG).setAction("Ok",null)
                    .show();
        else
        {
            Intent i=new Intent(this, VerifyOtpViewImp.class);
            i.putExtra("email",e);
            startActivity(i);
        }

    }
}
