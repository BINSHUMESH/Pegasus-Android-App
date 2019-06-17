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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    EditText email,password;
    FirebaseAuth mAuth;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;
    FirebaseFirestore db;


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

    public void UpdateUI(FirebaseUser user){
        hideProgressingView();
        if(user==null){
            Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent i=new Intent(SignUp.this,PersonalDetails.class);
            startActivity(i);
        }
    }
    public void onClicktextlogin(View view) {
        Intent i = new Intent(SignUp.this, Login.class);
        startActivity(i);
    }

    public boolean validate() {
        boolean valid = true;
        String temail = email.getText().toString();
        String tpassword = password.getText().toString();

        if (temail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(temail).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (tpassword.isEmpty() || tpassword.length() < 4 || tpassword.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;
    }

    public void onClickSignUp(View view){
        showProgressingView();
        if(validate()) {
            String temail = email.getText().toString();
            String tpassword = password.getText().toString();
            mAuth.createUserWithEmailAndPassword(temail, tpassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                Log.i("User Creation", "successfull");
                                UpdateUI(currentUser);
                            } else {
                                Log.i("User Creation", "successfull");
                                UpdateUI(null);
                            }
                        }
                    });
        }
        else{
            UpdateUI(null);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email=findViewById(R.id.Signup_email);
        password=findViewById(R.id.Signup_password);

        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
    }
}
