package com.example.chatapp.Frontend;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;

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

        // Assuming you have a method to get previous messages for the chat
        loadPreviousMessages();

        messageAdapter = new MessageAdapter(messageList);
        messagesRecyclerView.setAdapter(messageAdapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString();
            if (!message.isEmpty()) {
                // Add new message to list and notify adapter
                messageList.add(message);
                messageAdapter.notifyItemInserted(messageList.size() - 1);
                messageEditText.setText("");
                messagesRecyclerView.scrollToPosition(messageList.size() - 1); // Scroll to bottom
            }
        });
    }

    private void loadPreviousMessages() {
        // Dummy data, replace with actual message loading logic
        messageList.add("Hello!");
        messageList.add("Hi, how are you?");
        messageList.add("I'm good, thanks for asking.");
    }
}