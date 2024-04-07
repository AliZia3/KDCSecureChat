package com.example.chatapp.Frontend.Message;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private List<String> messageList = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private RecyclerView messagesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        EditText messageEditText = findViewById(R.id.messageEditText);
        Button sendButton = findViewById(R.id.sendButton);

        Intent i = getIntent();
        String name = i.getStringExtra("Receiver_NAME");
        System.out.println("==========================================================================");
        System.out.println(name);
        // Assuming you have a method to get previous messages for the chat
        loadPreviousMessages(name);

        messageAdapter = new MessageAdapter(messageList);
        messagesRecyclerView.setAdapter(messageAdapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString();
            if (!message.isEmpty()) {
//                FirebaseAuth auth = FirebaseAuth.getInstance();
//                String senderID = auth.getCurrentUser().getUid();

                saveMessages(message);
                // Add new message to list and notify adapter
//                messageList.add(message);
//                messageAdapter.notifyItemInserted(messageList.size() - 1);
//                messageEditText.setText("");
//                messagesRecyclerView.scrollToPosition(messageList.size() - 1); // Scroll to bottom
            }
        });
    }

    private void loadPreviousMessages(String name) {
        // Dummy data, replace with actual message loading logic
        String[][] strings =  {
                {"Jake","Hello"},
                {"Ali","What's up"},
                {"Will","Hi, how are you?"},
                {"Changhao","Hi, how are you?"},
                {"Ali","I'm good, thanks for asking."},
                {"Matthew","Come on"},
        };

        for (String[] s:strings){
            System.out.println(s[0]);
            System.out.println(name);
            if (s[0].equals(name)){
                messageList.add(s[1]);
            }
        }
    }

    private void saveMessages(String message) {
        messageList.add(message);
//        messageAdapter.notifyItemInserted(messageList.size() - 1);
//        messagesRecyclerView.scrollToPosition(messageList.size() - 1); // Scroll to bottom
    }

}