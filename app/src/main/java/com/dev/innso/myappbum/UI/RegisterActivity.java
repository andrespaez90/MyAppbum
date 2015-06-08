package com.dev.innso.myappbum.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.innso.myappbum.Models.SharedPrefKeys;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterActivity extends ActionBarActivity {

    @InjectView(R.id.register_facebook)
    LoginButton FacebookLogin;

    @InjectView(R.id.register_name)
    TextView tv_name;



    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        initFacebookButton();
        init();
    }

    private void init(){
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
                            tv_name.setText(object.toString());
                        Toast.makeText(RegisterActivity.this,object.toString(),Toast.LENGTH_LONG).show();

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
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
