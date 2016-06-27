package com.dev.innso.myappbum.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.utils.Encrypt;
import com.dev.innso.myappbum.utils.SharePreferences;
import com.dev.innso.myappbum.utils.tags.ActivityTags;
import com.dev.innso.myappbum.utils.tags.JSONTag;
import com.dev.innso.myappbum.utils.tags.SharedPrefKeys;
import com.dev.innso.myappbum.providers.ServerConnection;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity{

    @Bind(R.id.login_username)
    TextView userEmail;

    @Bind(R.id.login_password)
    TextView userPass;

    @Bind(R.id.login_error)
    TextView loginError;

    @Bind(R.id.login_singin)
    Button btnRegister;

    @Bind(R.id.login_Login)
    Button btnLogin;

    @Override public  void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.slide_right_in,R.anim.stay);
        init();
    }

    private void init(){
        userPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if( actionId == EditorInfo.IME_ACTION_GO){
                    login();
                }
                return false;
            }
        });
    }

    @OnClick(R.id.login_singin)
    protected void signin(){
        Intent intent = new Intent(this,RegisterActivity.class);
        this.startActivityForResult(intent, ActivityTags.ACTIVITY_REGISTER.ordinal());
    }

    @OnClick(R.id.login_Login)
    protected void login(){

        enableActivity(false);

        String email = userEmail.getText().toString();
        String pass = userPass.getText().toString();
        pass = Encrypt.md5(pass);

        Pair<String, String> pairEmail =  new Pair<String, String>(JSONTag.JSON_USER_EMAIL.toString() , email);
        Pair<String, String> pairPass =  new Pair<String, String>(JSONTag.JSON_USER_PASSWORD.toString() , pass);

        new loginService().execute(pairEmail, pairPass);
    }

    private void enableActivity(Boolean activate){
        userEmail.setEnabled(activate);
        userPass.setEnabled(activate);
        btnLogin.setEnabled(activate);
        btnRegister.setEnabled(activate);

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


    private class loginService extends AsyncTask<Pair<String,String>,String,String> {

        protected String doInBackground(Pair<String,String>... data){
            try{

                String response = ServerConnection.requestPOST(getResources().getString(R.string.loginService), data);
                JSONObject jsonObject = new JSONObject(response);
                if( !jsonObject.getString( JSONTag.JSON_RESPONSE.toString()).equals( JSONTag.JSON_SUCCESS.toString() )){
                    return null;
                }else{
                    SharePreferences.saveDataApplication(SharedPrefKeys.USER_ID, jsonObject.getString(JSONTag.JSON_USER_ID.toString()));
                    SharePreferences.saveDataApplication(SharedPrefKeys.FACEBOOK_USERID, jsonObject.getString(JSONTag.JSON_USER_IDFACE.toString()) );
                    SharePreferences.saveDataApplication(SharedPrefKeys.NAME_USER, jsonObject.getString(JSONTag.JSON_USER_NAME.toString()));
                    SharePreferences.saveDataApplication(SharedPrefKeys.PROFILE_USER, jsonObject.getString(JSONTag.JSON_URLPROFILE.toString()));
                    SharePreferences.saveDataApplication(SharedPrefKeys.COVER_USER, jsonObject.getString(JSONTag.JSON_URLCOVER.toString()));
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
