package com.example.chatapp.Frontend.Message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.Frontend.Users;
import com.example.chatapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageListActivity extends AppCompatActivity {

    DatabaseReference database;
    ListView listView;

//    String[] nameList = {};
    String[] nameList= {"Alice", "Bob", "Charlie", "Dave", "Frank"};

    ArrayList<String> usersArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message_list);
        Intent i = getIntent();
        String names = i.getStringExtra("NameList");
//        String[] neee = names
        System.out.println(names);
        System.out.println("eeeeeeeee");
        listView = findViewById(R.id.friends_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameList);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = nameList[position];

                Intent intent = new Intent(MessageListActivity.this, MessageActivity.class);
                intent.putExtra("FRIEND_NAME", selectedName); // Pass the name to MessageActivity
                startActivity(intent);
            }
        });
    }
}



//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });