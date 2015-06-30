package com.dev.innso.myappbum.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.dev.innso.myappbum.Providers.ServerConnection;
import com.dev.innso.myappbum.Utils.TAGs.JSONTag;
import com.dev.innso.myappbum.Utils.TAGs.SharedPrefKeys;
import com.dev.innso.myappbum.Utils.TAGs.ActivityTags;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.Utils.SharePreferences;
import com.dev.innso.myappbum.Utils.TAGs.StringTags;
import com.facebook.AccessToken;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class StartActivity extends Activity {

    @InjectView(R.id.star_facebook)
    LoginButton FacebookLogin;

    CallbackManager callbackManager;

    private String userID;
    private String userEmail;
    private String userName;
    private String usercover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_start);
        ButterKnife.inject(this);
        initFacebookButton();
    }


    @OnClick(R.id.start_access)
    protected void accessApp(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, ActivityTags.ACTIVITY_LOGIN.ordinal());
    }

    private void initFacebookButton() {
        FacebookLogin.setReadPermissions("public_profile ", "email", "user_photos");
        FacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                succesFacebookLogin(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    private void succesFacebookLogin( AccessToken Token){
        //Save Preference
        String accessToken = Token.getToken();
        String userId = Token.getUserId();
        SharePreferences.saveDataApplication(SharedPrefKeys.ACCESS_TOKEN, accessToken);
        SharePreferences.saveDataApplication(SharedPrefKeys.FACEBOOK_USERID, userId);

        //Save information of facebook account
        GraphRequest request = GraphRequest.newMeRequest(
                Token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted( JSONObject object,GraphResponse response) {
                        saveFacebookInformation( object );
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,cover");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void saveFacebookInformation(JSONObject information){
        try {
            userID = information.getString(JSONTag.JSON_USER_ID.toString());
            userEmail = information.getString(JSONTag.JSON_USER_EMAIL.toString());
            userName = information.getString(JSONTag.JSON_USER_NAME.toString());
            usercover = information.getJSONObject(JSONTag.JSON_FACEBOOK_COVER.toString()).getString("source");

            Pair<String, String> pairName = new Pair<>(JSONTag.JSON_USER_NAME.toString(),userName);
            Pair<String, String> pairEmail = new Pair<>(JSONTag.JSON_USER_EMAIL.toString(),userEmail);
            Pair<String, String> pairFacebook = new Pair<>(JSONTag.JSON_USER_IDFACE.toString(),userID);
            Pair<String, String> pairCover = new Pair<>(JSONTag.JSON_URLCOVER.toString(),usercover);

            new registerFacebookUser().execute(pairName,pairEmail,pairFacebook,pairCover);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == ActivityTags.ACTIVITY_LOGIN.ordinal() ){
            if( resultCode == RESULT_OK )
                finishSuccess();
            return;
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void savePreference(){
        SharePreferences.saveDataApplication(SharedPrefKeys.FACEBOOK_USERID, userID);
        SharePreferences.saveDataApplication(SharedPrefKeys.NAME_USER,userName);
        SharePreferences.saveDataApplication(SharedPrefKeys.COVER_USER, usercover);
    }

    private void finishSuccess(){
        setResult(RESULT_OK);
        finish();
    }





    private class registerFacebookUser extends AsyncTask<Pair<String,String>,String,String> {

        protected String doInBackground(Pair<String,String>... data){
            try{
                String response = ServerConnection.requestPOST(getResources().getString(R.string.registerService), data);
                publishProgress(response);
                JSONObject jsonObject = new JSONObject(response);
                if( !jsonObject.getString( JSONTag.JSON_RESPONSE.toString()).equals( JSONTag.JSON_SUCCESS.toString() )){
                    return null;
                }else{
                    SharePreferences.saveDataApplication(SharedPrefKeys.ID_USER, jsonObject.getString(JSONTag.JSON_USER_ID.toString()));
                    savePreference();
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
            Toast.makeText(StartActivity.this, progress[0], Toast.LENGTH_LONG).show();
        }



        protected void onPostExecute(String result) {
            if( result !=null ) {
                finishSuccess();
            }
        }

    }


}
