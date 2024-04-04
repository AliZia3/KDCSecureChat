package com.example.chatapp.Frontend.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chatapp.Frontend.Employee.EmployeeLoginActivity;
import com.example.chatapp.R;

public class SupervisorLoginActivity extends AppCompatActivity {

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


        Button supervisorLoginButton = findViewById(R.id.action_supervisor_login);
        supervisorLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to SupervisorActionsActivity
                Intent intent = new Intent(SupervisorLoginActivity.this, SupervisorActionsActivity.class);
                startActivity(intent);
            }
        });


        Button employeeLoginButton = findViewById(R.id.action_employee_login_redirect);
        employeeLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to EmployeeLoginActivity
                Intent intent = new Intent(SupervisorLoginActivity.this, EmployeeLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
