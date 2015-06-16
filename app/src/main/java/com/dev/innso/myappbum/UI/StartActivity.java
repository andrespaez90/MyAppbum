package com.dev.innso.myappbum.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.innso.myappbum.Models.SharedPrefKeys;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
