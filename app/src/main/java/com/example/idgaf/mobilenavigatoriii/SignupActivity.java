package com.example.idgaf.mobilenavigatoriii.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.idgaf.mobilenavigatoriii.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView logo;
    private ImageButton backbtn;
    private EditText username, email, password;
    private Button signupbtn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        logo = (ImageView) findViewById(R.id.signupLogo);
        backbtn = (ImageButton) findViewById(R.id.signup_backbtn);
        username = (EditText) findViewById(R.id.signup_username);
        email = (EditText) findViewById(R.id.signup_email);
        password = (EditText) findViewById(R.id.signup_password);
        signupbtn = (Button) findViewById(R.id.signup_signupButton);

        email.setOnClickListener(this);
        password.setOnClickListener(this);
        signupbtn.setOnClickListener(this);
        backbtn.setOnClickListener(this);


    }

    private void signupUser() {
        String m_email = email.getText().toString().trim();
        String m_password = password.getText().toString().trim();

        if (TextUtils.isEmpty(m_email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(m_password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }


        auth.createUserWithEmailAndPassword(m_email, m_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Registration success", Toast.LENGTH_SHORT).show();
                            email.setText("");
                            password.setText("");
                            username.setText("");
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            email.setText("");
                            password.setText("");
                            username.setText("");
                        }
                    }

                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_signupButton:
                signupUser();
                break;
            case R.id.signup_backbtn:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
