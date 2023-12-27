package com.example.sanpablook_establishment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ProfileFragment extends Fragment {

    ImageButton btnSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnSettings = view.findViewById(R.id.settingsButton);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSettings(view);
            }
        });

        return view;
    }

    private void goToSettings(View view) {
        Intent intent = new Intent(getActivity(), ProfileSettingsActivity.class);
        startActivity(intent);
    }
}