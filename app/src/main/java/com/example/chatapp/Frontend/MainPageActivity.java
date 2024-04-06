package com.example.chatapp.Frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chatapp.Frontend.Employee.EmployeeLoginActivity;
import com.example.chatapp.Frontend.Message.MessageListActivity;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // If current user not logged in, redirects to the Employee login screen
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(MainPageActivity.this, EmployeeLoginActivity.class);
            startActivity(intent);
        }
    }

    public void launchMessageList(View view){
        //launch a new activity

        Intent intent =  new Intent(this, MessageListActivity.class);
//        String message = ((EditText)findViewById(R.id.source)).getText().toString();
//        intent.putExtra("COOL",message);
        startActivity(intent);


    }
}