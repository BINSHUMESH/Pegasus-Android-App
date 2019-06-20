package com.test.bms.pegasus;



import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.ArrayList;

public class CreateEvent extends AppCompatActivity implements OnItemSelectedListener {
    static String gender,upperage,lowerage,eventName,membersLimit;
    EditText eupperage,elowerage,eeventName,emembersLimit;
    Spinner spinner;
    ArrayList<String> spinnerOptionList=new ArrayList<>();
    static String[] descriptionData = {"General\nDetails", "Date/Time", "Location", "Confirm"};
    boolean valid;

    public boolean validate(){
        valid=true;
        if(Integer.parseInt(lowerage)>Integer.parseInt(upperage)){
            elowerage.setError("Lower age < upper Age");
            valid=false;
        }

        if(upperage.isEmpty())
                eupperage.setError("Cannot be empty");
        if(lowerage.isEmpty())
                elowerage.setError("Cannot be empty");
        if(eventName.isEmpty())
                eeventName.setError("Cannot be empty");
        if(membersLimit.isEmpty())
                emembersLimit.setError("Cannot be empty");
        return valid;
    }

    public  void onNextClick(View view){
        upperage=eupperage.getText().toString().trim();
        lowerage=elowerage.getText().toString().trim();
        eventName=eeventName.getText().toString().trim();
        membersLimit=emembersLimit.getText().toString();
        if(validate()) {
            Intent i = new Intent(CreateEvent.this, CreateEvent2.class);
            startActivity(i);
        }
        else
            Toast.makeText(this, "Wrong Details", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        spinner=findViewById(R.id.CreateEvent_spinner);
        eupperage=findViewById(R.id.CreateEvent_upperage);
        elowerage=findViewById(R.id.CreateEvent_lowerage);
        eeventName=findViewById(R.id.CreateEvent_name);
        emembersLimit=findViewById(R.id.CreateEvent_memberslimit);
        gender="Male";

        spinnerOptionList.add("Male");
        spinnerOptionList.add("Female");
        spinnerOptionList.add("Both");

        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,spinnerOptionList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);


        StateProgressBar stateProgressBar = findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        gender=parent.getSelectedItem().toString();
    }
}
