package com.example.localsurveys.localsurveys.login;

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

import com.example.localsurveys.localsurveys.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnReset, btnBack;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        auth = FirebaseAuth.getInstance();
        inputEmail = findViewById(R.id.email);
        btnReset = findViewById(R.id.btn_reset);
        btnBack = findViewById(R.id.btn_back);
        progressBar = findViewById(R.id.progressbar);
        image = findViewById(R.id.image);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Please enter your e-mail");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                image.setVisibility(View.INVISIBLE);

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())      {
                                    Toast.makeText(ResetPasswordActivity.this, "We have send you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                                } else {
                                    Toast.makeText(ResetPasswordActivity.this, "Failed to send reset mail", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.INVISIBLE);
                                image.setVisibility(View.VISIBLE);
                            }
                        });

            }
        });

    }
}
