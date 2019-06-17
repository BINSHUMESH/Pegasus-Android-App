package com.test.bms.pegasus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView goToRegister;
    private FirebaseAuth mAuth;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;

    public void showProgressingView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (!isProgressShowing) {
            isProgressShowing = true;
            progressView = (ViewGroup) getLayoutInflater().inflate(R.layout.progressbar_layout, null);
            View v = this.findViewById(android.R.id.content).getRootView();
            ViewGroup viewGroup = (ViewGroup) v;
            viewGroup.addView(progressView);
        }
    }

    public void hideProgressingView() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        View v = this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }

    public void UpdateUI(FirebaseUser currentUser) {
        hideProgressingView();
        if (currentUser == null) {
            Toast.makeText(this, "Authentication failed! Please Try again", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(Login.this, MainActivity.class);
            i.putExtra("Welcome Message", currentUser.getDisplayName());
            startActivity(i);
        }
    }

    public void onClickRegister(View view) {
        Intent i = new Intent(Login.this, SignUp.class);
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

    public void onClickLogin(View view) {
        showProgressingView();
        if (validate()) {
            String temail = email.getText().toString();
            String tpassword = password.getText().toString();
            mAuth.signInWithEmailAndPassword(temail, tpassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                UpdateUI(currentUser);
                            } else {
                                UpdateUI(null);
                            }
                        }
                    });
            }
            else {
                UpdateUI(null);
            }
        }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            mAuth = FirebaseAuth.getInstance();
            setContentView(R.layout.activity_login);
            email = findViewById(R.id.Login_email);
            password = findViewById(R.id.Login_password);
            login = findViewById(R.id.Login_b_login);
            goToRegister = findViewById(R.id.Login_b_register);

        }


}

