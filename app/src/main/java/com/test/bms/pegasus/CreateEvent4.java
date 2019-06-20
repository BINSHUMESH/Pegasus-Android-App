package com.test.bms.pegasus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.test.bms.pegasus.CreateEvent.descriptionData;

public class CreateEvent4 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ArrayList<String> spinnerOptionList=new ArrayList<>();
    String toParticipate,description;
    EditText edescription;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String documentid;


    public void showProgressingView() {

        if (!isProgressShowing) {
            isProgressShowing = true;
            progressView = (ViewGroup) getLayoutInflater().inflate(R.layout.progressbar_layout, null);
            View v = this.findViewById(android.R.id.content).getRootView();
            ViewGroup viewGroup = (ViewGroup) v;
            viewGroup.addView(progressView);
        }
    }

    public void hideProgressingView() {
        View v = this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }

    public void onClickCreateEvent(View view) {
        showProgressingView();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
            description = edescription.getText().toString();
            Date date = new Date();
            try {
                date = formatter.parse(CreateEvent2.date + " " + CreateEvent2.time + ":00");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final Map<String, Object> event = new HashMap<>();
            event.put("gender", CreateEvent.gender);
            event.put("lowerage", Double.parseDouble(CreateEvent.lowerage));
            event.put("upperage", Double.parseDouble(CreateEvent.upperage));
            event.put("memberslimit", Double.parseDouble(CreateEvent.membersLimit));
            event.put("organiserId", mAuth.getUid());
            event.put("sport", CreateEvent.eventName);
            event.put("time", CreateEvent2.time);
            event.put("date", CreateEvent2.date);
            event.put("description", description);

            db.collection("events")
                    .add(event)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.i("Event", "Added Successfully");
                            documentid = documentReference.getId();

                            event.clear();
                            event.put("country", CreateEvent3.countryC);
                            event.put("landmark", CreateEvent3.landmarkC);
                            event.put("city", CreateEvent3.cityC);
                            event.put("pincode", Double.parseDouble(CreateEvent3.pincodeC));
                            event.put("state", CreateEvent3.stateC);
                            event.put("street", CreateEvent3.streetC);
                            event.put("latitude", MapsActivity.meetingLocation.getLatitude());
                            event.put("longitude", MapsActivity.meetingLocation.getLongitude());

                            db.collection("events/" + documentid + "/location")
                                    .add(event)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.i("Event", "location added");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("Event", "location Failed");
                                        }
                                    });

                            event.clear();
                            event.put("eventId", documentid);
                            db.collection("users/" + mAuth.getUid() + "/hostEvents")
                                    .add(event)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.i("Event", "host event added");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("Event", "host event failed");
                                        }
                                    });

                            event.clear();
                            if (toParticipate.equals("yes")) {
                                event.put("eventId", documentid);
                                db.collection("users/" + mAuth.getUid() + "/joinEvents")
                                        .add(event)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.i("Event", "host event added");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("Event", "host event failed");
                                            }
                                        });

                                event.clear();
                                event.put("Participant id", mAuth.getUid());
                                db.collection("events/" + documentid + "/participants")
                                        .add(event)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.i("Event", "location added");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("Event", "location Failed");
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("Event", "Failed");
                        }
                    });

            Intent i = new Intent(CreateEvent4.this, MainActivity.class);
            startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event4);
        spinner=findViewById(R.id.CreateEvent_spinner);
        edescription=findViewById(R.id.CreateEvent_description);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();


        StateProgressBar stateProgressBar = findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);

        spinnerOptionList.add("Yes");
        spinnerOptionList.add("No");

        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,spinnerOptionList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        toParticipate= parent.getItemAtPosition(position).toString().toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        toParticipate=parent.getSelectedItem().toString();
    }
}
