package com.dev.innso.myappbum.UI.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dev.innso.myappbum.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by INNSO SAS on 22/09/2015.
 */
public class PassNumber extends Activity implements View.OnClickListener {

    @Bind(R.id.pass_num1)
    Button btn_1;

    @Bind(R.id.pass_num2)
    Button btn_2;

    @Bind(R.id.pass_num3)
    Button btn_3;

    @Bind(R.id.pass_num4)
    Button btn_4;

    @Bind(R.id.pass_num5)
    Button btn_5;

    @Bind(R.id.pass_num6)
    Button btn_6;

    @Bind(R.id.pass_num7)
    Button btn_7;

    @Bind(R.id.pass_num8)
    Button btn_8;

    @Bind(R.id.pass_num9)
    Button btn_9;

    @Bind(R.id.pass_num0)
    Button btn_0;

    @Bind(R.id.pass_delete)
    Button btn_delete;

    @Bind(R.id.pass_1)
    EditText pass_1;

    @Bind(R.id.pass_2)
    EditText pass_2;

    @Bind(R.id.pass_3)
    EditText pass_3;

    @Bind(R.id.pass_4)
    EditText pass_4;

    private String Key;

    private int Type; // if type is for nuew passnumber the value is 1
    private int passNumber;
    private String firstPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passnumber);
        overridePendingTransition(R.anim.slide_up_in, R.anim.stay);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Type = getIntent().getIntExtra("Type", 0);
        passNumber = getIntent().getIntExtra("Pass",0);
        firstPass ="-1";
        Key = "";
        addListeners();
    }

    private void addListeners(){
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_0.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btn_delete.getId()){
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
        if(Key.length() == 4 && Type == 0){
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
        }else if(Key.length() == 4 && Type == 1){
            //TODO:Create the appbum with passnumber
        }
    }

    private void addKey(int viewId) {
        if(viewId == btn_1.getId())
            Key=Key+"1";
        if(viewId == btn_2.getId())
            Key=Key+"2";
        if(viewId == btn_3.getId())
            Key=Key+"3";
        if(viewId == btn_4.getId())
            Key=Key+"4";
        if(viewId == btn_5.getId())
            Key=Key+"5";
        if(viewId == btn_6.getId())
            Key=Key+"6";
        if(viewId == btn_7.getId())
            Key=Key+"7";
        if(viewId == btn_8.getId())
            Key=Key+"8";
        if(viewId == btn_9.getId())
            Key=Key+"9";
        if(viewId == btn_0.getId())
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
