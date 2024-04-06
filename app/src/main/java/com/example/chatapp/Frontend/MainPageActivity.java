package com.example.chatapp.Frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chatapp.Frontend.Employee.EmployeeLoginActivity;
import com.example.chatapp.Frontend.Message.MessageListActivity;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {
    DatabaseReference database;

    String tempList;
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
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() == null) {
//            Intent intent = new Intent(MainPageActivity.this, EmployeeLoginActivity.class);
//            startActivity(intent);
//        }

        ArrayList<String> usersArrayList = new ArrayList<String>();
        database= FirebaseDatabase.getInstance().getReference("user");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users user = dataSnapshot.getValue(Users.class);
                    usersArrayList.add(user.getName());
                    System.out.println("!!!!!!!!!!!!!!");
                }
//                System.out.println(usersArrayList.toString());
                String tempList = String.join("()",usersArrayList);
//                launchMessageList();
                System.out.println("************");
                System.out.println(tempList);
//                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void launchMessageList(View view){
        //launch a new activity

        Intent intent =  new Intent(this, MessageListActivity.class);
//        String message = ((EditText)findViewById(R.id.source)).getText().toString();
        intent.putExtra("NameList",tempList);
        startActivity(intent);


    }
}