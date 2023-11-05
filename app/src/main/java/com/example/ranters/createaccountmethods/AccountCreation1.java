package com.example.ranters.createaccountmethods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ranters.LogIn;
import com.example.ranters.R;

public class AccountCreation1 extends AppCompatActivity {

    EditText fullNameEdittext;
    AppCompatButton nextBtn;
    TextView alreadyHaveAcc;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation1);
        fullNameEdittext = findViewById(R.id.fullName_Edittext);
        nextBtn = findViewById(R.id.next_Button);
        alreadyHaveAcc = findViewById(R.id.alreadyHaveAccount);

        alreadyHaveAcc.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), LogIn.class));
        });
    }
}