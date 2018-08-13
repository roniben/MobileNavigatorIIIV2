package com.example.idgaf.mobilenavigatoriii.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.idgaf.mobilenavigatoriii.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView logo;
    private Button loginbtn, signupbtn;
    private EditText loginemail, loginpassword;
    private ProgressBar progressBar;
    private Timer timer;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        logo = (ImageView) findViewById(R.id.imageView);

        loginemail = (EditText) findViewById(R.id.login_email);
        loginpassword = (EditText) findViewById(R.id.login_password);

        signupbtn = (Button) findViewById(R.id.login_signupButton);
        loginbtn = (Button) findViewById(R.id.login_loginButton);
        progressBar = (ProgressBar) findViewById(R.id.login_progressbar);

        loginbtn.setOnClickListener(this);
        signupbtn.setOnClickListener(this);
        loginemail.setOnClickListener(this);
        loginpassword.setOnClickListener(this);

    }


    private void signupUser(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    private void loginUser(){
        String m_email = loginemail.getText().toString().trim();
        String m_password = loginpassword.getText().toString().trim();

        if (TextUtils.isEmpty(m_email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(m_password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(m_email, m_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_loginButton:
                progressBar.setVisibility(View.VISIBLE);
                loginUser();
                break;
            case R.id.login_signupButton:
                signupUser();
                finish();
                break;
        }
    }
}
