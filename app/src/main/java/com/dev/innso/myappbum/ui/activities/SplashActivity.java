package com.dev.innso.myappbum.ui.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
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
import com.dev.innso.myappbum.ui.LoginActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    FirebaseAuth authManager;

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

    private void successFacebookLogin(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        authManager.signInWithCredential(credential).addOnCompleteListener(task -> completeFacebookRegister(task, token));

    }

    private void completeFacebookRegister(Task<AuthResult> task, AccessToken token) {

        if (task.isSuccessful()) {

            UpdateProfile(task.getResult().getUser());

            getAdditionaeFacebookInfo(token);

        } else {
            Log.w("SPLASH", "signInWithCredential", task.getException());
        }
    }

    private void getAdditionaeFacebookInfo(AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(token,
                (object, response) -> saveFacebookInformation(object));
        Bundle parameters = new Bundle();
        parameters.putString("fields", "cover");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void saveFacebookInformation(JSONObject response) {
        try {

            managerPreferences.set(AppPreference.COVER_USER, response.getJSONObject("cover").getString("source"));

            finishSuccess();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void UpdateProfile(FirebaseUser newUser) {

        String userName = TextUtils.isEmpty(newUser.getDisplayName()) ? newUser.getEmail() : newUser.getDisplayName();

        managerPreferences.set(AppPreference.USER_ID, newUser.getUid());

        managerPreferences.set(AppPreference.USER_NAME, userName);

        managerPreferences.set(AppPreference.IS_FACEBOOK_USER, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.REQUEST_ACTIVITY_LOGIN) {
            if (resultCode == RESULT_OK)
                finishSuccess();
            return;
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
        startActivityForResult(intent, MainActivity.REQUEST_ACTIVITY_LOGIN);
    }
}
