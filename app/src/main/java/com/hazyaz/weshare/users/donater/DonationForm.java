package com.hazyaz.weshare.users.donater;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hazyaz.weshare.R;

public class DonationForm extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootViewDonater = inflater.inflate(R.layout.donater_form,
                container, false);






        return rootViewDonater;

    }
}
