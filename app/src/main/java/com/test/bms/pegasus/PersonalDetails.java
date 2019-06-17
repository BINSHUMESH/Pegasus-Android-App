package com.test.bms.pegasus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class PersonalDetails extends AppCompatActivity {
    EditText country,city,street,houseNo,pincode,name,dob,sportInterested,mobileNo,gender,state;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;
    int flag1=0,flag2=0;

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

    public boolean validate() {
        boolean valid = true;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/mm/yyyy");
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(dob.getText().toString().trim());
        } catch (ParseException pe) {
            valid=false;
            Log.i("Date","false");
            dob.setError("Wrong Date Format");
        }

        if(mobileNo.getText().toString().length()!=10) {
            valid = false;
            mobileNo.setError("10 Digit");
        }
        return valid;
    }

    public void UpdateUI(boolean user){
        hideProgressingView();
        if(!user){
            Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent i=new Intent(PersonalDetails.this,MainActivity.class);
            startActivity(i);
        }
    }

    public void onClickComplete(View view){
        showProgressingView();
        if(validate()) {
            Map<String, Object> user = new HashMap<>();
            user.put("dob", dob.getText().toString());
            user.put("gender", gender.getText().toString().toUpperCase());
            user.put("phoneNumber", mobileNo.getText().toString());
            user.put("rating",0);
            user.put("sportInterested", sportInterested.getText().toString().toUpperCase());
            user.put("name", name.getText().toString());
            user.put("hostEvents", null);
            user.put("joinEvents", null);
            db.collection("users").document(mAuth.getUid())
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            flag1= 1;
                            Log.i("Data added:", Integer.toString(flag1));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            flag1= 0;
                            Log.i("Data added:", Integer.toString(flag1));
                        }
                    });
            user.clear();
            user.put("country", country.getText().toString().toUpperCase());
            user.put("locality", city.getText().toString().toUpperCase());
            user.put("pincode", Integer.parseInt(pincode.getText().toString()));
            user.put("state",state.getText().toString().toUpperCase());
            user.put("street",street.getText().toString());
            user.put("House Details",houseNo.getText().toString());
            db.collection("users/" + mAuth.getUid() + "/address")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            flag2= 1;
                            Log.i("AddressAdded:",Integer.toString(flag2));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            flag2= 0;
                            Log.i("Address Added:",Integer.toString(flag2));
                        }
                    });
            UpdateUI(true);
            user.clear();
        }
        else{
            UpdateUI(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        mAuth= FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        country=findViewById(R.id.Signup_country);
        city=findViewById(R.id.Signup_city);
        street=findViewById(R.id.Signup_street);
        state=findViewById(R.id.Signup_state);
        pincode=findViewById(R.id.Signup_pincode);
        houseNo=findViewById(R.id.Signup_houseno);
        name=findViewById(R.id.Signup_name);
        dob=findViewById(R.id.Signup_dob);
        sportInterested=findViewById(R.id.Signup_sportsInterested);
        mobileNo=findViewById(R.id.Signup_phoneNumber);
        gender=findViewById(R.id.Signup_gender);
    }
}
