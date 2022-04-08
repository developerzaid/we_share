package com.hazyaz.weshare.users.areaincharge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hazyaz.weshare.R;

public class AIRegister extends Fragment {

    String area[] = {"Mumbai-Central", "Mahim", "Nallasopara", "Andheri", "Byculla", "Jogeshwari"};
    EditText ai_name, ai_password, ai_email,ai_city, ai_phone, ai_;
    Spinner AreaSpinner;
    Button register_ai_button;


    AIRegister(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootViewAI = inflater.inflate(R.layout.ai_register,
                container, false);

//
//        ai_name = rootViewAI.findViewById(R.id.ai_name);
//        ai_password = rootViewAI.findViewById(R.id.ai_password);
//        ai_email = rootViewAI.findViewById(R.id.ai_email);
//        ai_phone = rootViewAI.findViewById(R.id.ai_PhoneNo);
//        ai_city = rootViewAI.findViewById(R.id.ai_city);
//        register_ai_button = rootViewAI.findViewById(R.id.ai_register);



        AreaSpinner = rootViewAI.findViewById(R.id.area_spinner_ai_register);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item,
                        area); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        AreaSpinner.setAdapter(spinnerArrayAdapter);



        return rootViewAI;
    }
}
