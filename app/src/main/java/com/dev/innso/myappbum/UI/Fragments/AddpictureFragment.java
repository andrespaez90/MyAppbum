package com.dev.innso.myappbum.UI.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.innso.myappbum.R;

import butterknife.ButterKnife;

/**
 * Created by INNSO SAS on 21/05/2015.
 */
public class AddpictureFragment extends Fragment {

    public static android.support.v4.app.Fragment newInstance() {
        return new AddpictureFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_addpicture, container, false);
        return rootView;
    }
}
