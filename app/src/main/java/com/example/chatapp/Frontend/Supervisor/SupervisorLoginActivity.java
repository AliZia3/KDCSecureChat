package com.example.chatapp.Frontend.Supervisor;

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

import com.example.chatapp.Frontend.Employee.EmployeeLoginActivity;
import com.example.chatapp.Frontend.MainActivity;
import com.example.chatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SupervisorLoginActivity extends AppCompatActivity {

    String supervisorEmailLoginKey = "admin@gmail.com";
    String supervisorPasswordLoginKey = "123456";
    Button loginButton, employeeLoginRedirectButton ;
    EditText supervisorEmail, password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_supervisor_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        loginButton = findViewById(R.id.action_supervisor_login);
        supervisorEmail = findViewById(R.id.login_supervisorEmail);
        password = findViewById(R.id.login_supervisorPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = supervisorEmail.getText().toString();
                String pass = password.getText().toString();

                if ((TextUtils.isEmpty(email))){
                    Toast.makeText(SupervisorLoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass)){
                    Toast.makeText(SupervisorLoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)){
                    supervisorEmail.setError("Incorrect Email Address Form");
                } else if (password.length()<5){
                    password.setError("More Then 5 Characters");
                    Toast.makeText(SupervisorLoginActivity.this, "Incorrect Password Length", Toast.LENGTH_SHORT).show();
                } else {
                    if (email.equals(supervisorEmailLoginKey) && pass.equals(supervisorPasswordLoginKey)) {
                        // Credentials are correct, navigate to the next page
                        try {
                            Intent intent = new Intent(SupervisorLoginActivity.this, SupervisorActionsActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(SupervisorLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Credentials are incorrect
                        Toast.makeText(SupervisorLoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        // Redirect to Employee Login screen
        employeeLoginRedirectButton = findViewById(R.id.action_employee_login_redirect);
        employeeLoginRedirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to EmployeeLoginActivity
                Intent intent = new Intent(SupervisorLoginActivity.this, EmployeeLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
