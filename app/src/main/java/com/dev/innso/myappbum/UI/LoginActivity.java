package com.dev.innso.myappbum.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.innso.myappbum.Providers.ServerConnection;
import com.dev.innso.myappbum.Utils.TAGs.ActivityTags;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.Utils.TAGs.JSONTag;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends ActionBarActivity {

    @InjectView(R.id.login_username)
    TextView userEmail;

    @InjectView(R.id.login_password)
    TextView userPass;

    @InjectView(R.id.login_error)
    TextView loginError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        String email = userEmail.getText().toString();
        String pass = userPass.getText().toString();

        Pair<String, String> pairEmail =  new Pair<String, String>(JSONTag.USER_EMAIL.toString() , email);
        Pair<String, String> pairPass =  new Pair<String, String>(JSONTag.USER_PASSWORD.toString() , pass);

        new loginService().execute(pairEmail, pairPass);
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

                String response = ServerConnection.requestPOST("http://andrespaez90.com/Appbum/loginService.php", data);
                JSONObject jsonObject = new JSONObject(response);
                if( !jsonObject.getString( JSONTag.JSON_RESPONSE.toString()).equals( JSONTag.JSON_SUCCESS.toString() )){
                    return null;
                }
                publishProgress(response);
                return "Success";

            }
            catch (Exception e) {
                Log.d("ReadWeatherJSONFeedTask", e.getMessage());
            }
            return null;
        }


        protected void onProgressUpdate(String... progress) {
             Toast.makeText(LoginActivity.this,progress[0],Toast.LENGTH_LONG).show();
        }



        protected void onPostExecute(String result) {
            if( result ==null )
                loginError.setText( "Usuario o contraseņa incorrecta" );
        }

    }
}
