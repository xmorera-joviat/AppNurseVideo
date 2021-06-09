package com.example.nurseapp.GestioVideos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nurseapp.R;

public class FragmentAfegirVideos extends Fragment {
    public FragmentAfegirVideos() { super(R.layout.fragment_afegir_videos); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_afegir_videos, container, false);
    }

}
