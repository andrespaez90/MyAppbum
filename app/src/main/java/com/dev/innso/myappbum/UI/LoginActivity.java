package com.dev.innso.myappbum.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.innso.myappbum.Providers.Functions;
import com.dev.innso.myappbum.Providers.ServerConnection;
import com.dev.innso.myappbum.Utils.SharePreferences;
import com.dev.innso.myappbum.Utils.TAGs.ActivityTags;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.Utils.TAGs.JSONTag;
import com.dev.innso.myappbum.Utils.TAGs.SharedPrefKeys;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends ActionBarActivity{

    @InjectView(R.id.login_username)
    TextView userEmail;

    @InjectView(R.id.login_password)
    TextView userPass;

    @InjectView(R.id.login_error)
    TextView loginError;

    @InjectView(R.id.login_singin)
    Button btnRegister;

    @InjectView(R.id.login_Login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.login_singin)
    protected void signin(){
        Intent intent = new Intent(this,RegisterActivity.class);
        this.startActivityForResult(intent, ActivityTags.ACTIVITY_REGISTER.ordinal());
    }

    @OnClick(R.id.login_Login)
    protected void login(){

        enbleActivity(false);

        String email = userEmail.getText().toString();
        String pass = userPass.getText().toString();
        pass = Functions.md5(pass);

        Pair<String, String> pairEmail =  new Pair<String, String>(JSONTag.JSON_USER_EMAIL.toString() , email);
        Pair<String, String> pairPass =  new Pair<String, String>(JSONTag.JSON_USER_PASSWORD.toString() , pass);

        new loginService().execute(pairEmail, pairPass);
    }

    private void enbleActivity(Boolean activate){
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


    private class loginService extends AsyncTask<Pair<String,String>,String,String> {

        protected String doInBackground(Pair<String,String>... data){
            try{

                String response = ServerConnection.requestPOST(getResources().getString(R.string.loginService), data);
                JSONObject jsonObject = new JSONObject(response);
                if( !jsonObject.getString( JSONTag.JSON_RESPONSE.toString()).equals( JSONTag.JSON_SUCCESS.toString() )){
                    return null;
                }else{
                    SharePreferences.saveDataApplication(SharedPrefKeys.ID_USER, jsonObject.getString(JSONTag.JSON_USER_ID.toString()));
                    SharePreferences.saveDataApplication(SharedPrefKeys.FACEBOOK_USERID, jsonObject.getString(JSONTag.JSON_USER_IDFACE.toString()) );
                    SharePreferences.saveDataApplication(SharedPrefKeys.NAME_USER, jsonObject.getString(JSONTag.JSON_USER_NAME.toString()));
                    SharePreferences.saveDataApplication(SharedPrefKeys.PROFILE_USER, jsonObject.getString(JSONTag.JSON_URLPROFILE.toString()));
                    SharePreferences.saveDataApplication(SharedPrefKeys.COVER_USER, jsonObject.getString(JSONTag.JSON_URLCOVER.toString()));
                }
                publishProgress(response);
                return JSONTag.JSON_SUCCESS.toString();
            }
            catch (Exception e) {
                Log.d("ReadWeatherJSONFeedTask", e.getMessage());
                publishProgress(e.getMessage());
            }
            return null;
        }


        protected void onProgressUpdate(String... progress) {
             //Toast.makeText(LoginActivity.this,progress[0],Toast.LENGTH_LONG).show();
        }



        protected void onPostExecute(String result) {
            if( result ==null ) {
                loginError.setText("Usuario o contraseña incorrecta");
            }
            else if( result.equals(JSONTag.JSON_SUCCESS.toString())){
                setResult(RESULT_OK);
                finish();
            }
            enbleActivity(true);
        }

    }
}
