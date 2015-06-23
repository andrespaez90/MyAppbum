package com.dev.innso.myappbum.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dev.innso.myappbum.Utils.TAGs.SharedPrefKeys;
import com.dev.innso.myappbum.Utils.TAGs.StringTags;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.Utils.SharePreferences;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends ActionBarActivity {

    @InjectView(R.id.register_name)
    TextView tv_name;

    @InjectView(R.id.register_email)
    TextView tv_email;

    @InjectView(R.id.register_password)
    TextView tv_passwords;

    @InjectView(R.id.register_rpasswords)
    TextView tv_rpassword;

    private String userName;
    private String userEmail;
    private String userPassword;
    private String rePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.register_button)
    protected void Register(){
        getData();
        StringTags responseAction = confirmData();
        if( responseAction == StringTags.ACTION_SUCCESS ){
            savePreference();
            //Save on server
            showDialog(responseAction);
        }else{
            showDialog(responseAction);
        }
    }

    private void getData(){
        userName = tv_name.getText().toString();
        userEmail = tv_email.getText().toString();
        userPassword = tv_passwords.getText().toString();
        rePassword = tv_rpassword.getText().toString();
        userEmail = userEmail.toLowerCase();
        userName = userName.toLowerCase();
    }

    private StringTags confirmData() {
        if(userName == "" || userName.length() < 3)
            return StringTags.REGISTER_NAME;
        if( !validateEmail() )
            return StringTags.REGISTER_EMAIL;
        if(userPassword.length() < 3)
            return StringTags.REGISTER_LENGHT_PASSWORD;
        if(!userPassword.equals(rePassword))
            return StringTags.REGISTER_PASSWORD;
        return StringTags.ACTION_SUCCESS;
    }


    private boolean validateEmail(){
        if(userEmail == "" || !userEmail.contains("@") )
            return false;
        else{
            if( userEmail.contains(" ") ){
                return false;
            }
            String dominio = userEmail.substring( userEmail.indexOf("@") );
            if( !dominio.contains(".") )
                return false;
        }
        return true;
    }

    private void savePreference(){
        SharePreferences.saveDataApplication(SharedPrefKeys.NAME_USER, userName);
        SharePreferences.saveDataApplication(SharedPrefKeys.EMAIL_USER, userEmail);
    }

    private void finishSuccess(){
        setResult(RESULT_OK);
        finish();
    }


    private String title = "";
    String message = "";

    private void showDialog(final StringTags tag){
        getMesagge(tag);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (tag == StringTags.ACTION_SUCCESS) {
                            finishSuccess();
                        }
                    }
                })
                .show();
    }

    private void getMesagge(StringTags Tag){
        if(Tag == StringTags.ACTION_SUCCESS ){
            title = getResources().getString(R.string.title_create_user);
            message =  getResources().getString(R.string.success_create_user);
            return;
        }else{
            title = getResources().getString(R.string.title_error);
            if( Tag == StringTags.REGISTER_NAME){
                message = getResources().getString(R.string.error_name);
                return;
            }
            if( Tag == StringTags.REGISTER_EMAIL){
                message =  getResources().getString(R.string.errirn_email);
                return;
            }
            if( Tag == StringTags.REGISTER_LENGHT_PASSWORD){
                message = getResources().getString(R.string.error_lenght_pass);
                return;
            }
            if( Tag == StringTags.REGISTER_PASSWORD){
                message = getResources().getString(R.string.error_passwords);
                return;
            }
        }
    }
}
