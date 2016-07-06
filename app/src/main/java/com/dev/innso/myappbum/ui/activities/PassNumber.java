package com.dev.innso.myappbum.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dev.innso.myappbum.R;


public class PassNumber extends Activity implements View.OnClickListener {

    private EditText pass_1;
    private EditText pass_2;
    private EditText pass_3;
    private EditText pass_4;

    private String Key;

    private int type;
    private int passNumber;
    private String firstPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passnumber);
        overridePendingTransition(R.anim.slide_up_in, R.anim.stay);
        initViews();
        init();
    }

    private void initViews() {
        pass_1 = (EditText) findViewById(R.id.pass_1);
        pass_2 = (EditText) findViewById(R.id.pass_2);
        pass_3 = (EditText) findViewById(R.id.pass_3);
        pass_4 = (EditText) findViewById(R.id.pass_4);
    }

    private void init() {
        type = getIntent().getIntExtra("type", 0);
        passNumber = getIntent().getIntExtra("Pass",0);
        firstPass ="-1";
        Key = "";
        addListeners();
    }

    private void addListeners(){
        findViewById(R.id.pass_num1).setOnClickListener(this);
        findViewById(R.id.pass_num2).setOnClickListener(this);
        findViewById(R.id.pass_num3).setOnClickListener(this);
        findViewById(R.id.pass_num4).setOnClickListener(this);
        findViewById(R.id.pass_num5).setOnClickListener(this);
        findViewById(R.id.pass_num6).setOnClickListener(this);
        findViewById(R.id.pass_num7).setOnClickListener(this);
        findViewById(R.id.pass_num8).setOnClickListener(this);
        findViewById(R.id.pass_num9).setOnClickListener(this);
        findViewById(R.id.pass_num0).setOnClickListener(this);
        findViewById(R.id.pass_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pass_delete) {
            deleteNumber();
            return;
        }
        addPassNumber();
        addKey(v.getId());
        validateKey();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        close();
    }

    private void close(){
        finish();
        overridePendingTransition(R.anim.stay,R.anim.slide_up_out);
    }

    private void validateKey() {
        if (Key.length() == 4 && type == 0) {
            if(Integer.parseInt(Key) == passNumber){
                setResult(RESULT_OK);
                close();
            }else{
                LinearLayout Layout = (LinearLayout)findViewById(R.id.activity_pass_lay);
                Animation animator = AnimationUtils.loadAnimation(this, R.anim.wrong_paassnumber);
                animator.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        deleteKey();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                Layout.startAnimation(animator);

            }
        } else if (Key.length() == 4 && type == 1) {
            //TODO:Create the appbum with passnumber
        }
    }

    private void addKey(int viewId) {
        if (viewId == R.id.pass_num1)
            Key=Key+"1";
        if (viewId == R.id.pass_num2)
            Key=Key+"2";
        if (viewId == R.id.pass_num3)
            Key=Key+"3";
        if (viewId == R.id.pass_num4)
            Key=Key+"4";
        if (viewId == R.id.pass_num5)
            Key=Key+"5";
        if (viewId == R.id.pass_num6)
            Key=Key+"6";
        if (viewId == R.id.pass_num7)
            Key=Key+"7";
        if (viewId == R.id.pass_num8)
            Key=Key+"8";
        if (viewId == R.id.pass_num9)
            Key=Key+"9";
        if (viewId == R.id.pass_num0)
            Key=Key+"0";
    }

    private void addPassNumber() {
        switch (Key.length()) {
            case 0:
                pass_1.setText("1");
                break;
            case 1:
                pass_2.setText("2");
                break;
            case 2:
                pass_3.setText("3");
                break;
            default:
                pass_4.setText("4");
                break;
        }
    }

    private void deleteKey(){
        pass_1.setText("");
        pass_2.setText("");
        pass_3.setText("");
        pass_4.setText("");
        Key="";
    }

    private void deleteNumber() {
        int count = Key.length();
        if(count > 0){
            switch (count) {
                case 1:
                    pass_1.setText("");
                    break;
                case 2:
                    pass_2.setText("");
                    break;
                default:
                    pass_3.setText("");
                    break;
            }
            Key = Key.substring(0, Key.length()-1);
        }
    }


}
