package com.example.chatapp.Frontend.Employee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chatapp.Frontend.MainActivity;
import com.example.chatapp.R;
import com.example.chatapp.Frontend.Supervisor.SupervisorLoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class EmployeeLoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button loginButton, supervisorLoginRedirectButton;
    EditText employeeEmail, password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        auth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.action_employee_login);
        employeeEmail = findViewById(R.id.login_employeeEmail);
        password = findViewById(R.id.login_employeePassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = employeeEmail.getText().toString();
                String pass = password.getText().toString();

                if ((TextUtils.isEmpty(email))){
                    Toast.makeText(EmployeeLoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass)){
                    Toast.makeText(EmployeeLoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)){
                    employeeEmail.setError("Incorrect Email Address Form");
                } else if (password.length()<5){
                    password.setError("Password >=5 Character");
                } else {
                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                try {
                                    Intent intent = new Intent(EmployeeLoginActivity.this , MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }catch (Exception e){
                                    Toast.makeText(EmployeeLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(EmployeeLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // Redirect to Supervisor login screen
        supervisorLoginRedirectButton = findViewById(R.id.action_supervisor_login_redirect);
        supervisorLoginRedirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to SupervisorLoginActivity
                Intent intent = new Intent(EmployeeLoginActivity.this, SupervisorLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
