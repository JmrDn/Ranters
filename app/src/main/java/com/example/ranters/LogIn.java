package com.example.ranters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ranters.createaccountmethods.AccountCreation1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    AppCompatButton loginBtn, createAccBtn;
    EditText password, email;
    ProgressBar progressBar;

    boolean passwordVisible;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_or_register);
        loginBtn = findViewById(R.id.logIn_btn);
        createAccBtn = findViewById(R.id.createAcc_Button);
        password = findViewById(R.id.password_Edittext);
        email = findViewById(R.id.email_Edittext);
        progressBar = findViewById(R.id.progressBar);

        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        loginBtn.setOnClickListener(view -> {
            loginBtn.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            String emailString = email.getText().toString();
            String passwordString = password.getText().toString();

            if (emailString.isEmpty()){
                email.setError("Enter email");
                loginBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
                email.setError("Enter valid email");
                loginBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            else if (passwordString.isEmpty()){
                password.setError("Enter password");
                loginBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            else {
                loginBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                login();
            }

        });

        createAccBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), AccountCreation1.class));
        });

        hidePassword();
    }

    private void login() {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){

                                Toast.makeText(getApplicationContext(),"Successfully log in", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), Homepage.class));
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Please verify your email", Toast.LENGTH_LONG).show();
                                loginBtn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Log in failed", Toast.LENGTH_LONG).show();
                            loginBtn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void hidePassword() {

           password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final int Right = 2;

                if (motionEvent.getAction()== MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>= password.getRight()-password.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = password.getSelectionEnd();
                        if (passwordVisible){
                            //set drawable image here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_off_24, 0);
                            // for hide password
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }
                        else {

                            //set drawable image here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_24, 0);
                            // for show password
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;

                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}