package com.dev.innso.myappbum.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.di.ApiModule;
import com.dev.innso.myappbum.di.component.AppComponent;
import com.dev.innso.myappbum.di.component.DaggerAppComponent;
import com.dev.innso.myappbum.managers.AppPreference;
import com.dev.innso.myappbum.managers.preferences.ManagerPreferences;
import com.dev.innso.myappbum.providers.ServerConnection;
import com.dev.innso.myappbum.utils.Encrypt;
import com.dev.innso.myappbum.utils.tags.ActivityTags;
import com.dev.innso.myappbum.utils.tags.JSONTag;

import org.json.JSONObject;

import javax.inject.Inject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView userEmail;

    private TextView userPass;

    private TextView loginError;

    private Button buttonRegister;

    private Button buttonLogin;

    @Inject
    ManagerPreferences managerPreferences;

    @Override public  void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.slide_right_in,R.anim.stay);
        AppComponent daggerComponent = DaggerAppComponent.builder().apiModule(new ApiModule()).build();

        daggerComponent.inject(this);
        initViews();
        addListeners();
    }

    private void initViews() {

        userEmail = (TextView) findViewById(R.id.login_username);

        userPass = (TextView) findViewById(R.id.login_password);

        loginError = (TextView) findViewById(R.id.login_error);

        buttonRegister = (Button) findViewById(R.id.login_button_Login);

        buttonLogin = (Button) findViewById(R.id.login_button_singin);
    }

    private void addListeners() {

        buttonLogin.setOnClickListener(this);

        buttonRegister.setOnClickListener(this);

        userPass.setOnEditorActionListener((v, actionId, event) -> {
            if( actionId == EditorInfo.IME_ACTION_GO){
                login();
            }
            return false;
        });
    }

    private void enableActivity(Boolean activate){
        userEmail.setEnabled(activate);
        userPass.setEnabled(activate);
        buttonLogin.setEnabled(activate);
        buttonRegister.setEnabled(activate);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==   ActivityTags.ACTIVITY_REGISTER.ordinal() && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.stay,R.anim.slide_right_out);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId){
            case R.id.login_button_Login:
                signin();
                break;
            case R.id.login_button_singin:
                login();
                break;
        }
    }

    protected void signin(){
        Intent intent = new Intent(this,RegisterActivity.class);
        this.startActivityForResult(intent, ActivityTags.ACTIVITY_REGISTER.ordinal());
    }

    protected void login(){

        enableActivity(false);

        String email = userEmail.getText().toString();
        String pass = userPass.getText().toString();
        pass = Encrypt.md5(pass);

        Pair<String, String> pairEmail =  new Pair<String, String>(JSONTag.JSON_USER_EMAIL.toString() , email);
        Pair<String, String> pairPass =  new Pair<String, String>(JSONTag.JSON_USER_PASSWORD.toString() , pass);

        new loginService().execute(pairEmail, pairPass);
    }


    private class loginService extends AsyncTask<Pair<String,String>,String,String> {

        protected String doInBackground(Pair<String,String>... data){
            try{

                String response = ServerConnection.requestPOST(getResources().getString(R.string.loginService), data);
                JSONObject jsonObject = new JSONObject(response);
                if( !jsonObject.getString( JSONTag.JSON_RESPONSE.toString()).equals( JSONTag.JSON_SUCCESS.toString() )){
                    return null;
                }else{
                    managerPreferences.set(AppPreference.USER_ID, jsonObject.getString(JSONTag.JSON_USER_ID.toString()));
                    managerPreferences.set(AppPreference.FACEBOOK_USERID, jsonObject.getString(JSONTag.JSON_USER_IDFACE.toString()) );
                    managerPreferences.set(AppPreference.NAME_USER, jsonObject.getString(JSONTag.JSON_USER_NAME.toString()));
                    managerPreferences.set(AppPreference.PROFILE_USER, jsonObject.getString(JSONTag.JSON_URLPROFILE.toString()));
                    managerPreferences.set(AppPreference.COVER_USER, jsonObject.getString(JSONTag.JSON_URLCOVER.toString()));
                }
                publishProgress(response);
                return JSONTag.JSON_SUCCESS.toString();
            }
            catch (Exception e) {
                publishProgress(e.getMessage());
            }
            return null;
        }


        protected void onProgressUpdate(String... progress) {
             //Toast.makeText(LoginActivity.this,progress[0],Toast.LENGTH_LONG).show();
        }

        protected void onPostExecute(String result) {
            if( result ==null ) {
                loginError.setText("Usuario o contrase�a incorrecta");
            }
            else if( result.equals(JSONTag.JSON_SUCCESS.toString())){
                setResult(RESULT_OK);
                finish();
            }
            enableActivity(true);
        }

    }
}
