package com.dev.innso.myappbum.ui.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.animation.GeneralAnimations;
import com.dev.innso.myappbum.di.ApiModule;
import com.dev.innso.myappbum.di.component.AppComponent;
import com.dev.innso.myappbum.di.component.DaggerAppComponent;
import com.dev.innso.myappbum.managers.AppPreference;
import com.dev.innso.myappbum.managers.preferences.ManagerPreferences;
import com.dev.innso.myappbum.providers.ServerConnection;
import com.dev.innso.myappbum.ui.LoginActivity;
import com.dev.innso.myappbum.utils.tags.ActivityTags;
import com.dev.innso.myappbum.utils.tags.JSONTag;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class SplashActivity extends Activity implements View.OnClickListener {

    private LoginButton FacebookLogin;

    private LinearLayout layoutActionButtons;

    private RelativeLayout layoutCenterImage;

    private CallbackManager callbackManager;

    private Button buttonAccess;

    private String userID;
    private String userEmail;
    private String userName;
    private String usercover;

    private GeneralAnimations generalAnimations;

    @Inject
    ManagerPreferences managerPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        AppComponent daggerComponent = DaggerAppComponent.builder().apiModule(new ApiModule()).build();
        daggerComponent.inject(this);
        initView();
        addListeners();
        launchHandler();
    }

    private void initView() {

        FacebookLogin = (LoginButton) findViewById(R.id.star_facebook);

        layoutActionButtons = (LinearLayout) findViewById(R.id.splash_layout_buttons);

        layoutCenterImage = (RelativeLayout) findViewById(R.id.layout_center_image);

        buttonAccess = (Button) findViewById(R.id.start_access);

        generalAnimations = new GeneralAnimations();
    }

    private void addListeners() {
        buttonAccess.setOnClickListener(this);
    }

    private void launchHandler() {
        final Handler handler = new Handler();
        handler.postDelayed(this::validateUser, 2000);
    }

    private void validateUser() {
        String userId = managerPreferences.getString(AppPreference.USER_ID);
        if (TextUtils.isEmpty(userId)) {
            initFacebookButton();
            animateButtons();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void animateButtons() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animationButton = (ObjectAnimator) generalAnimations.getAppearFromBottom(layoutActionButtons, 1000, 0);
        ObjectAnimator animationCenterImage = (ObjectAnimator) generalAnimations.getTranslationY(layoutCenterImage, 1000, 0, getMoveHeight());
        animatorSet.playTogether(animationButton, animationCenterImage);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();
    }

    private float getMoveHeight() {
        return -layoutActionButtons.getMeasuredHeight() / 2;
    }


    private void initFacebookButton() {
        FacebookLogin.setReadPermissions("public_profile ", "email", "user_photos");
        FacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                successFacebookLogin(loginResult.getAccessToken());
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

    private void successFacebookLogin(AccessToken Token) {
        //Save Preference
        String accessToken = Token.getToken();
        String userId = Token.getUserId();
        managerPreferences.set(AppPreference.ACCESS_TOKEN, accessToken);
        managerPreferences.set(AppPreference.FACEBOOK_USERID, userId);

        //Save information of facebook account
        GraphRequest request = GraphRequest.newMeRequest(
                Token,
                (object, response) -> saveFacebookInformation(object));
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,cover");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void saveFacebookInformation(JSONObject information) {
        try {
            userID = information.getString(JSONTag.JSON_USER_ID.toString());
            userEmail = information.getString(JSONTag.JSON_USER_EMAIL.toString());
            userName = information.getString(JSONTag.JSON_USER_NAME.toString());
            usercover = information.getJSONObject(JSONTag.JSON_FACEBOOK_COVER.toString()).getString("source");

            Pair<String, String> pairName = new Pair<>(JSONTag.JSON_USER_NAME.toString(), userName);
            Pair<String, String> pairEmail = new Pair<>(JSONTag.JSON_USER_EMAIL.toString(), userEmail);
            Pair<String, String> pairFacebook = new Pair<>(JSONTag.JSON_USER_IDFACE.toString(), userID);
            Pair<String, String> pairCover = new Pair<>(JSONTag.JSON_URLCOVER.toString(), usercover);

            new registerFacebookUser().execute(pairName, pairEmail, pairFacebook, pairCover);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityTags.ACTIVITY_LOGIN.ordinal()) {
            if (resultCode == RESULT_OK)
                finishSuccess();
            return;
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void savePreference() {
        managerPreferences.set(AppPreference.FACEBOOK_USERID, userID);
        managerPreferences.set(AppPreference.NAME_USER, userName);
        managerPreferences.set(AppPreference.COVER_USER, usercover);
    }

    private void finishSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.start_access:
                accessApp();
                break;
        }
    }

    protected void accessApp() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, ActivityTags.ACTIVITY_LOGIN.ordinal());
    }


    private class registerFacebookUser extends AsyncTask<Pair<String, String>, String, String> {

        protected String doInBackground(Pair<String, String>... data) {
            try {
                String response = ServerConnection.requestPOST(getResources().getString(R.string.registerService), data);
                publishProgress(response);
                JSONObject jsonObject = new JSONObject(response);
                if (!jsonObject.getString(JSONTag.JSON_RESPONSE.toString()).equals(JSONTag.JSON_SUCCESS.toString())) {
                    return null;
                } else {
                    managerPreferences.set(AppPreference.USER_ID, jsonObject.getString(JSONTag.JSON_USER_ID.toString()));
                    savePreference();
                }
                publishProgress(response);
                return JSONTag.JSON_SUCCESS.toString();
            } catch (Exception e) {
                Log.d("ReadWeatherJSONFeedTask", e.getMessage());
                publishProgress(e.getMessage());
            }
            return null;
        }


        protected void onProgressUpdate(String... progress) {
            //Toast.makeText(SplashActivity.this, progress[0], Toast.LENGTH_LONG).show();
        }


        protected void onPostExecute(String result) {
            if (result != null) {
                finishSuccess();
            }
        }

    }


}
