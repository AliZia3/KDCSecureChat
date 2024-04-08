package com.example.chatapp.Frontend.Message;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Backend.MessageMangagement.SendTextMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.example.chatapp.Backend.MessageMangagement.AccessChatHistory;
import com.example.chatapp.Backend.MessageMangagement.SendTextMessage;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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

        System.out.println(receiverName);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String senderID = auth.getCurrentUser().getUid();
        String senderName = auth.getCurrentUser().getDisplayName();

        // Getting previous messages for the chat
        loadPreviousMessages(senderID,receiverName);


        messageAdapter = new MessageAdapter(messageList);
        messagesRecyclerView.setAdapter(messageAdapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString();
            if (!message.isEmpty()) {
                try {
                    saveMessages(message,senderID,receiverId);
                } catch (InvalidAlgorithmParameterException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchPaddingException e) {
                    throw new RuntimeException(e);
                } catch (IllegalBlockSizeException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (BadPaddingException e) {
                    throw new RuntimeException(e);
                } catch (InvalidKeyException e) {
                    throw new RuntimeException(e);
                }
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
                {"Ali","Hello"},
                {"Will","Hello"},
                {"Changhao","Hello"},
                {"Matthew","Hello"},
        };

        for (String[] s:strings){
            System.out.println(s[0]);
            System.out.println(receiverID);
            if (s[0].equals(receiverID)){
                messageList.add(s[1]);
            }
        }

    }

    private void saveMessages(String message, String senderID, String receiverID) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        SendTextMessage sendTextMessage = new SendTextMessage();
        System.out.println(message);
        sendTextMessage.sendTextMessage(UUID.fromString("43a5d34b-2410-4b6c-a847-ef5fd467ee13"),UUID.fromString("f759d772-9e2c-4a3e-bd8b-1d33a66e2d16"),message);
        messageList.add(message);

    }

}