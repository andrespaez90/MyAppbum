package com.dev.innso.myappbum.UI;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.innso.myappbum.Providers.JSONHandler;
import com.dev.innso.myappbum.Utils.TAGs.ActivityTags;
import com.dev.innso.myappbum.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends ActionBarActivity {

    @InjectView(R.id.login_username)
    TextView userEmail;

    @InjectView(R.id.login_password)
    TextView userPass;

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

        List<NameValuePair> data = new ArrayList<NameValuePair>();

        data.add(new BasicNameValuePair("user",email));
        data.add(new BasicNameValuePair("user", email));

        String ads = JSONHandler.requestPOST("http://andrespaez90.com/Appbum/loginService.php",data);
        Toast.makeText(this,ads,Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==   ActivityTags.ACTIVITY_REGISTER.ordinal() && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
