package com.dev.innso.myappbum.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.di.ApiModule;
import com.dev.innso.myappbum.di.component.AppComponent;
import com.dev.innso.myappbum.di.component.DaggerAppComponent;
import com.dev.innso.myappbum.managers.preferences.ManagerPreferences;
import com.dev.innso.myappbum.utils.Encrypt;
import com.dev.innso.myappbum.utils.tags.ActivityTags;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView userEmail;

    private TextView userPass;

    private TextView loginError;

    private Button buttonRegister;

    private Button buttonLogin;

    @Inject
    ManagerPreferences managerPreferences;

    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.slide_right_in, R.anim.stay);
        AppComponent daggerComponent = DaggerAppComponent.builder().apiModule(new ApiModule()).build();
        daggerComponent.inject(this);
        init();
        initViews();
        addListeners();
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initViews() {

        userEmail = (TextView) findViewById(R.id.login_username);

        userPass = (TextView) findViewById(R.id.login_password);

        loginError = (TextView) findViewById(R.id.login_error);

        buttonRegister = (Button) findViewById(R.id.login_button_Login);

        buttonLogin = (Button) findViewById(R.id.login_button_singin);
    }

    private void addListeners() {

        buttonLogin.setOnClickListener(this);

        buttonRegister.setOnClickListener(this);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityTags.ACTIVITY_REGISTER.ordinal() && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
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
        }
    }

    protected void signUp() {

        enableActivity(false);

        String email = userEmail.getText().toString();

        String pass = userPass.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, task -> {
            if (!task.isSuccessful()) {
                Log.e("Login", "Authentication failed." + task.getException());
            } else {
                Log.e("Login", "SuccessfulAuthentication failed." + task.getException());
            }
        });
    }

    protected void login() {

        enableActivity(false);

        String email = userEmail.getText().toString();
        String pass = userPass.getText().toString();
        pass = Encrypt.md5(pass);

        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, task -> {
            enableActivity(true);

            if (!task.isSuccessful()) {
                loginError.setText("Usuario o contraseña incorrecta");
            } else {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

}
