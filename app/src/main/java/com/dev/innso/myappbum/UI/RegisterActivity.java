package com.dev.innso.myappbum.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.innso.myappbum.Models.SharedPrefKeys;
import com.dev.innso.myappbum.Providers.StringTags;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.Utils.SharePreferences;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
            finishSuccess();
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
        if(userEmail == "" || !userPassword.contains("@") || !userEmail.contains("."))
            return StringTags.REGISTER_EMAIL;
        if(userPassword.length() < 3)
            return StringTags.REGISTER_LENGHT_PASSWORD;
        if(userPassword.equals(rePassword))
            return StringTags.REGISTER_PASSWORD;
        return StringTags.ACTION_SUCCESS;
    }

    private void savePreference(){
        SharePreferences.saveDataApplication(SharedPrefKeys.NAME_USER, userName);
        SharePreferences.saveDataApplication(SharedPrefKeys.EMAIL_USER, userEmail);
    }

    private void finishSuccess(){
        setResult(RESULT_OK);
        finish();
    }



    private void showDialog(final StringTags tag){
        String title = "";
        String message = "";
        getMesagge(tag, title, message);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if ( tag == StringTags.ACTION_SUCCESS) {
                            RegisterActivity.this.finish();
                        }
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void getMesagge(StringTags Tag, String title, String message){
        if(Tag == StringTags.ACTION_SUCCESS ){
            title = getResources().getString(R.string.title_create_user);
            message =  getResources().getString(R.string.success_create_user);
            return;
        }
    }
}
