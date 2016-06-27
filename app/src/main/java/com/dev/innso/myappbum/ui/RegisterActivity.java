package com.dev.innso.myappbum.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.utils.Encrypt;
import com.dev.innso.myappbum.utils.SharePreferences;
import com.dev.innso.myappbum.utils.tags.JSONTag;
import com.dev.innso.myappbum.utils.tags.SharedPrefKeys;
import com.dev.innso.myappbum.utils.tags.StringTags;
import com.dev.innso.myappbum.providers.ServerConnection;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends ActionBarActivity {

    @Bind(R.id.register_name)
    TextView tv_name;

    @Bind(R.id.register_email)
    TextView tv_email;

    @Bind(R.id.register_password)
    TextView tv_passwords;

    @Bind(R.id.register_rpasswords)
    TextView tv_rpassword;

    @Bind(R.id.register_button)
    Button btn_register;
    String message = "";
    private String userName;
    private String userEmail;
    private String userPassword;
    private String rePassword;
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.scale_center_in, R.anim.stay);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    protected void Register(){
        enableActivity( false );
        getData();
        StringTags responseAction = confirmData();
        if( responseAction == StringTags.ACTION_SUCCESS ){

            userPassword = Encrypt.md5(userPassword);
            Pair<String,String> pairName = new Pair<>(JSONTag.JSON_USER_NAME.toString(),userName);
            Pair<String,String> pairEmail = new Pair<>(JSONTag.JSON_USER_EMAIL.toString(),userEmail);
            Pair<String,String> pairPass = new Pair<>(JSONTag.JSON_USER_PASSWORD.toString(),userPassword);

            new registerService().execute(pairName,pairEmail,pairPass);

        }else{
            showDialog(responseAction);
            enableActivity( true );
        }
    }

    private void enableActivity( Boolean enable ){
        tv_name.setEnabled( enable );
        tv_email.setEnabled( enable );
        tv_passwords.setEnabled( enable );
        tv_rpassword.setEnabled( enable );
        btn_register.setEnabled( enable );
    }

    private void getData(){
        userName = tv_name.getText().toString();
        userEmail = tv_email.getText().toString();
        userPassword = tv_passwords.getText().toString();
        rePassword = tv_rpassword.getText().toString();
        userEmail = userEmail.toLowerCase();
        userName = userName.toLowerCase();
    }

    private StringTags confirmData() {
        if(userName == "" || userName.length() < 3)
            return StringTags.REGISTER_NAME;
        if( !validateEmail() )
            return StringTags.REGISTER_EMAIL;
        if(userPassword.length() < 3)
            return StringTags.REGISTER_LENGHT_PASSWORD;
        if(!userPassword.equals(rePassword))
            return StringTags.REGISTER_PASSWORD;
        return StringTags.ACTION_SUCCESS;
    }

    private boolean validateEmail(){
        if(userEmail == "" || !userEmail.contains("@") )
            return false;
        else{
            if( userEmail.contains(" ") ){
                return false;
            }
            String dominio = userEmail.substring( userEmail.indexOf("@") );
            if( !dominio.contains(".") )
                return false;
        }
        return true;
    }

    private void savePreference(){
        SharePreferences.saveDataApplication(SharedPrefKeys.NAME_USER, userName);
        SharePreferences.saveDataApplication(SharedPrefKeys.EMAIL_USER, userEmail);
    }

    private void finishSuccess(){
        setResult(RESULT_OK);
        finish();
    }

    private void showDialog(final StringTags tag){
        getMesagge(tag);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (tag == StringTags.ACTION_SUCCESS) {
                            finishSuccess();
                        }
                    }
                })
                .show();
    }

    private void getMesagge(StringTags Tag){
        if(Tag == StringTags.ACTION_SUCCESS ){
            title = getResources().getString(R.string.title_create_user);
            message =  getResources().getString(R.string.success_create_user);
            return;
        }else{
            title = getResources().getString(R.string.title_error);
            if( Tag == StringTags.REGISTER_NAME){
                message = getResources().getString(R.string.error_name);
                return;
            }
            if( Tag == StringTags.REGISTER_EMAIL){
                message =  getResources().getString(R.string.error_email);
                return;
            }
            if( Tag == StringTags.REGISTER_LENGHT_PASSWORD){
                message = getResources().getString(R.string.error_lenght_pass);
                return;
            }
            if( Tag == StringTags.REGISTER_PASSWORD){
                message = getResources().getString(R.string.error_passwords);
                return;
            }
        }
    }

    private class registerService extends AsyncTask<Pair<String,String>,String,String> {

        protected String doInBackground(Pair<String,String>... data){
            try{
                String response = ServerConnection.requestPOST(getResources().getString(R.string.registerService), data);
                publishProgress(response);
                JSONObject jsonObject = new JSONObject(response);
                if( !jsonObject.getString( JSONTag.JSON_RESPONSE.toString()).equals( JSONTag.JSON_SUCCESS.toString() )){
                    return null;
                }else{
                    SharePreferences.saveDataApplication(SharedPrefKeys.USER_ID, jsonObject.getString(JSONTag.JSON_USER_ID.toString()));
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
            //Toast.makeText(RegisterActivity.this, progress[0], Toast.LENGTH_LONG).show();
        }



        protected void onPostExecute(String result) {
            if( result ==null ) {
                //loginError.setText("Usuario o contrase�a incorrecta");
            }
            else if( result.equals(JSONTag.JSON_SUCCESS.toString())){
                RegisterActivity.this.showDialog(StringTags.ACTION_SUCCESS);
            }
            enableActivity(true);
        }

    }

}