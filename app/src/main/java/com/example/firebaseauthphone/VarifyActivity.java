package com.example.firebaseauthphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VarifyActivity extends AppCompatActivity {
    private EditText varificationCodeET;
    private Button loginBtn;
    private String phoneNumber;
    private String verificationId;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varify);

        init();

        phoneNumber = getIntent().getStringExtra("phone");


        SendOpt();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = varificationCodeET.getText().toString();
                if(code.length()==6){
                    varify(code);
                    

                }else {

                    Toast.makeText(VarifyActivity.this, "Invalid code", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    



    private void SendOpt() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+88" + phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                callbacks);        // OnVerificationStateChangedCallbacksPhoneAuthActivity.java
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                varificationCodeET.setText(code);
                varify(code);

            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {


            Toast.makeText(VarifyActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }


    };

    private void varify(String code) {


            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

            signInWithCredential(credential);


        }

    private void signInWithCredential(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Intent intent = new Intent(VarifyActivity.this,MainActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

            }
        });
    }


    private void init() {

        varificationCodeET = findViewById(R.id.varificationCodeET);
        loginBtn = findViewById(R.id.loginBtn);
        firebaseAuth = FirebaseAuth.getInstance();
    }
}
