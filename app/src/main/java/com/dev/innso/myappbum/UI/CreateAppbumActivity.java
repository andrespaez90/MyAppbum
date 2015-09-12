package com.dev.innso.myappbum.UI;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.innso.myappbum.R;

import butterknife.OnClick;

public class CreateAppbumActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_up_in, R.anim.stay);
        setContentView(R.layout.activity_create_appbum);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_appbum, menu);
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

    @OnClick(R.id.back)
    void pressBack(){
        finish();
        this.overridePendingTransition(R.anim.stay,R.anim.slide_up_out);
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.stay,R.anim.slide_up_out);
    }
}
