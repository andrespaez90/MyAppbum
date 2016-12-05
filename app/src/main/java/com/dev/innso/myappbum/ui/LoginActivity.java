package com.dev.innso.myappbum.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView userEmail;

    private TextView userPass;

    private TextView loginError;

    private TextView textViewForgotPassword;

    private Button buttonRegister;

    private Button buttonLogin;

    @Inject
    ManagerPreferences managerPreferences;

    @Inject
    FirebaseAuth authManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.slide_right_in, R.anim.stay);
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

        textViewForgotPassword = (TextView) findViewById(R.id.textView_forgot_password);
    }

    private void addListeners() {

        buttonLogin.setOnClickListener(this);

        buttonLogin.setOnClickListener(this);

        textViewForgotPassword.setOnClickListener(this);

        userPass.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                login();
            }
            return false;
        });
    }

    private void enableActivity(Boolean activate) {
        userEmail.setEnabled(activate);
        userPass.setEnabled(activate);
        buttonLogin.setEnabled(activate);
        buttonRegister.setEnabled(activate);

    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.stay, R.anim.slide_right_out);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.login_button_Login:
                login();
                break;
            case R.id.login_button_singin:
                signUp();
                break;
            case R.id.textView_forgot_password:
                recoverPassword();
        }
    }


    private void signUp() {

        enableActivity(false);

        String email = userEmail.getText().toString();

        String pass = userPass.getText().toString();

        authManager.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, task -> {
            if (!task.isSuccessful()) {
                Log.e("Login", "Authentication failed." + task.getException());
            } else {
                UpdateProfile(task.getResult().getUser());
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void login() {

        enableActivity(false);

        String email = userEmail.getText().toString();
        String pass = userPass.getText().toString();

        authManager.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, task -> {
            enableActivity(true);

            if (!task.isSuccessful()) {
                loginError.setText("Usuario o contraseña incorrecta");
            } else {
                UpdateProfile(task.getResult().getUser());
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void recoverPassword() {
        if (!TextUtils.isEmpty(userEmail.getText().toString())) {
            authManager.sendPasswordResetEmail(userEmail.getText().toString());
        } else {
            loginError.setText("Ingrese el correo de su cuenta");
        }
    }

    private void UpdateProfile(FirebaseUser currentUser) {

        String userName = TextUtils.isEmpty(currentUser.getDisplayName()) ? currentUser.getEmail() : currentUser.getDisplayName();

        managerPreferences.set(AppPreference.USER_ID, currentUser.getUid());

        managerPreferences.set(AppPreference.USER_NAME, userName);
    }

}
