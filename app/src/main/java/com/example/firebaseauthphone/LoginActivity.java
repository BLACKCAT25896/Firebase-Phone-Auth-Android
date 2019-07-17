package com.example.firebaseauthphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText phoneET;
    private Button nextBtn;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = phoneET.getText().toString().trim();
                if(phoneNumber.length() == 11){

                    Intent intent = new Intent(LoginActivity.this,VarifyActivity.class);
                    intent.putExtra("phone",phoneNumber);
                    startActivity(intent);


                }else {

                    Toast.makeText(LoginActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }

            }
        });






    }



    private void init() {
        phoneET = findViewById(R.id.phoneNumberET);
        nextBtn = findViewById(R.id.nextBtn);
    }
}
