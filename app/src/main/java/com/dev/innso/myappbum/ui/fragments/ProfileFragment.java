package com.dev.innso.myappbum.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dev.innso.myappbum.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentProfileBinding.inflate(getActivity().getLayoutInflater());
    }
}
