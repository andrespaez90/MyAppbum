package com.dev.innso.myappbum.ui.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.ui.activities.MainActivity;
import com.dev.innso.myappbum.ui.ProfileActivity;
import com.dev.innso.myappbum.Utils.SharePreferences;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenu extends FrameLayout {

    private MainActivity activityMain;

    public MainMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        activityMain = (MainActivity) context;
        initView();
    }

    public MainMenu(Context context) {
        super(context);
        activityMain = (MainActivity) context;
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.view_main_menu, null);
        addView(view);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.main_menu_profile)
    void startProfile() {
        Intent i = new Intent(getContext(), ProfileActivity.class);
        activityMain.closeMenu();
        getContext().startActivity(i);
    }

    @OnClick(R.id.main_menu_notification)
    void showNotifications() {
        activityMain.closeMenu();
    }

    @OnClick(R.id.main_menu_invitation)
    void showInvitations() {
        activityMain.closeMenu();
    }

    @OnClick(R.id.main_menu_about)
    void showAbout() {
        activityMain.closeMenu();
    }

    @OnClick(R.id.main_menu_logout)
    void logOut() {
        SharePreferences.resetUser();
        activityMain.startLogin();
    }

}
