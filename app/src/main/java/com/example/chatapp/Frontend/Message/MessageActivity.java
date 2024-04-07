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
        String receiverName = i.getStringExtra("Receiver_NAME");
        String receiverId = i.getStringExtra("Receiver_ID");
        System.out.println("==========================================================================");
        System.out.println(receiverName);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String senderID = auth.getCurrentUser().getUid();
        String senderName = auth.getCurrentUser().getDisplayName();
        System.out.println(senderName);
        System.out.println(senderID);
        System.out.println("+_=-=--=-=-=-=-=-=-=-=");
        // Getting previous messages for the chat
        loadPreviousMessages(senderID,receiverName);


        messageAdapter = new MessageAdapter(messageList);
        messagesRecyclerView.setAdapter(messageAdapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString();
            if (!message.isEmpty()) {
                saveMessages(message,senderID,receiverId);
                // Add new message to list and notify adapter
//                messageList.add(message);
                messageAdapter.notifyItemInserted(messageList.size() - 1);
                messageEditText.setText("");
                messagesRecyclerView.scrollToPosition(messageList.size() - 1); // Scroll to bottom
            }
        });
    }

    private void loadPreviousMessages(String senderID, String receiverID) {
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
            System.out.println(receiverID);
            if (s[0].equals(receiverID)){
                messageList.add(s[1]);
            }
        }

    }

    private void saveMessages(String message, String senderID, String receiverID) {
        messageList.add(message);
        System.out.println(message);
        System.out.println(senderID);
        System.out.println(receiverID);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
//        messageAdapter.notifyItemInserted(messageList.size() - 1);
//        messagesRecyclerView.scrollToPosition(messageList.size() - 1); // Scroll to bottom
    }

}