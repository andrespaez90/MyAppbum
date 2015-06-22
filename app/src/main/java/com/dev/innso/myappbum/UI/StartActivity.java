package com.dev.innso.myappbum.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dev.innso.myappbum.Models.SharedPrefKeys;
import com.dev.innso.myappbum.Utils.ActivityTags;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.Utils.SharePreferences;
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
            String userID = information.getString("id");
            String userEmail = information.getString("email");
            String userName = information.getString("name");
            String usercover = information.getJSONObject("cover").getString("source");

            SharePreferences.saveDataApplication(SharedPrefKeys.FACEBOOK_USERID, userID);
            SharePreferences.saveDataApplication(SharedPrefKeys.NAME_USER,userName);
            SharePreferences.saveDataApplication(SharedPrefKeys.COVER_USER,usercover);

            setResult(RESULT_OK);
            finish();

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

    private void finishSuccess(){
        setResult(RESULT_OK);
        finish();
    }
}
