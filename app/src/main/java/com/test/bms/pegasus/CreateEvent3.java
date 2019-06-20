package com.test.bms.pegasus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.kofigyan.stateprogressbar.StateProgressBar;

import static com.test.bms.pegasus.CreateEvent.descriptionData;

public class CreateEvent3 extends AppCompatActivity {

    EditText ecountry,ecity,estreet,epincode,estate,elandmark;
    static  String countryC,cityC,streetC,pincodeC,stateC,landmarkC;
    public void  onClickMeetingPoint(View view){
        Intent i=new Intent(CreateEvent3.this,MapsActivity.class);
        i.putExtra("key",1);
        startActivity(i);
    }

    public void onClickNext(View view){
        countryC=ecountry.getText().toString().trim();
        cityC=ecity.getText().toString().trim();
        streetC=estreet.getText().toString().trim();
        pincodeC=epincode.getText().toString().trim();
        stateC=estate.getText().toString().trim();
        landmarkC=elandmark.getText().toString().trim();
        Intent i=new Intent(CreateEvent3.this,CreateEvent4.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event3);
        ecountry=findViewById(R.id.CreateEvent_country);
        ecity=findViewById(R.id.CreateEvent_city);
        estreet=findViewById(R.id.CreateEvent_street);
        epincode=findViewById(R.id.CreateEvent_pincode);
        estate=findViewById(R.id.CreateEvent_state);
        elandmark=findViewById(R.id.CreateEvent_landmark);

        StateProgressBar stateProgressBar = findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);
    }
}
