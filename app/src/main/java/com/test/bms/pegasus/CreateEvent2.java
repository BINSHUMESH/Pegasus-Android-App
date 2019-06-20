package com.test.bms.pegasus;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.sql.Timestamp;
import java.util.Calendar;

import static com.test.bms.pegasus.CreateEvent.descriptionData;

public class CreateEvent2 extends AppCompatActivity {

    EditText edate,etime;
    DatePickerDialog datePicker;
    TimePickerDialog timePicker;
    static String date,time;

    public void onClickSetDate(View view){
        final Calendar calendar=Calendar.getInstance();
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);

        datePicker=new DatePickerDialog(CreateEvent2.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edate.setText(dayOfMonth+"/"+month+"/"+year);
                date=edate.getText().toString();
            }
        },year,month,day);
        datePicker.show();

    }

    public void onClickSetTime(View view){

        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);

        timePicker = new TimePickerDialog(CreateEvent2.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        etime.setText(sHour + ":" + sMinute);
                        time=etime.getText().toString();
                    }
                }, hour, minutes, true);
        timePicker.show();

    }

    public void onClickNext(View view){
        Intent i=new Intent(CreateEvent2.this,CreateEvent3.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);

        edate=findViewById(R.id.CreateEvent_date);
        etime=findViewById(R.id.CreateEvent_time);

        StateProgressBar stateProgressBar = findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);

    }
}
