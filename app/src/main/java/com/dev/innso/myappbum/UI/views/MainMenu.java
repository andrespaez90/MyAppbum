package com.dev.innso.myappbum.UI.views;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.dev.innso.myappbum.Animation.GuillotineAnimation;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.UI.MainActivity;
import com.dev.innso.myappbum.UI.ProfileActivity;
import com.dev.innso.myappbum.Utils.SharePreferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by INNSO SAS on 05/09/2015.
 */
public class MainMenu extends FrameLayout {

    private MainActivity activityMain;

    public MainMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        activityMain = (MainActivity)context;
        initView();
    }

    public MainMenu(Context context) {
        super(context);
        activityMain = (MainActivity)context;
        initView();
    }

    private void initView() {
        View view = inflate(getContext(),R.layout.view_main_menu, null);
        addView(view);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.main_menu_profile)
    void startProfile(){
        Intent i = new Intent(getContext(), ProfileActivity.class);
        activityMain.closeMenu();
        getContext().startActivity(i);
    }

    @OnClick(R.id.main_menu_notification)
    void showNotifications(){
        activityMain.closeMenu();
    }

    @OnClick(R.id.main_menu_invitation)
    void showInvitations(){
        activityMain.closeMenu();
    }

    @OnClick(R.id.main_menu_about)
    void showAbout() {
        activityMain.closeMenu();
    }

    @OnClick(R.id.main_menu_logout)
    void logOut(){
        SharePreferences.resetUser();
        activityMain.startLogin();
    }

}
