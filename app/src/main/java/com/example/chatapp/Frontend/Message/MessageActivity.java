package com.example.chatapp.Frontend.Message;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.example.chatapp.Backend.MessageMangagement.AccessChatHistory;
import com.example.chatapp.Backend.MessageMangagement.SendTextMessage
        ;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
        System.out.println("==========================================================================");
        System.out.println(receiverName);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String senderID = auth.getCurrentUser().getUid();
        String senderName = auth.getCurrentUser().getDisplayName();
        System.out.println(senderName);
        System.out.println(senderID);
        System.out.println("+_=-=--=-=-=-=-=-=-=-=");
//         Getting previous messages for the chat
//        try {
//            loadPreviousMessages("43a5d34b-2410-4b6c-a847-ef5fd467ee13","f759d772-9e2c-4a3e-bd8b-1d33a66e2d16");
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


        messageAdapter = new MessageAdapter(messageList);
        messagesRecyclerView.setAdapter(messageAdapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchMessages("43a5d34b-2410-4b6c-a847-ef5fd467ee13","f759d772-9e2c-4a3e-bd8b-1d33a66e2d16");
        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString();
            System.out.println(message);
            System.out.println("--------------------------------------------------------------");
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

    private void loadPreviousMessages(String senderId, String receiverId) throws InterruptedException {
        // Dummy data, replace with actual message loading logic

//        AccessChatHistory accessChatHistory = new AccessChatHistory();
//        accessChatHistory.getRawMessages(senderID,receiverID,);
//        String[][] strings =  {
//                {"Jake","Hello"},
//                {"Ali","What's up"},
//                {"Will","Hi, how are you?"},
//                {"Changhao","Hi, how are you?"},
//                {"Ali","I'm good, thanks for asking."},
//                {"Matthew","Come on"},
//        };
//
//        for (String[] s:strings){
//            System.out.println(s[0]);
//            System.out.println(receiverID);
//            if (s[0].equals(receiverID)){
//                messageList.add(s[1]);
//            }
//        }
        final CountDownLatch latch = new CountDownLatch(1);

        AccessChatHistory chatHistory = new AccessChatHistory();
        chatHistory.getRawMessages(UUID.fromString(senderId), UUID.fromString(receiverId), new AccessChatHistory.RawMessagesListener() {
            @Override
            public void onRawMessagesReceived(List<String> messages) {
                // Assertions here
                System.out.println(messages);
                System.out.println("=========--------------------------==========================================");
                for (String message : messages) {
                    System.out.println("Message: " + message);
                    messageList.add(message);
                }
                latch.countDown();
            }

            @Override
            public void onError(String error) {
//                fail("Failed to fetch messages: " + error);
                latch.countDown();
            }
        });

        // Wait for Firebase to respond
//        latch.await(10, TimeUnit.SECONDS);

    }

    private void saveMessages(String message, String senderID, String receiverID) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        SendTextMessage sendTextMessage = new SendTextMessage();
        System.out.println(message);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        sendTextMessage.sendTextMessage(UUID.fromString("43a5d34b-2410-4b6c-a847-ef5fd467ee13"),UUID.fromString("f759d772-9e2c-4a3e-bd8b-1d33a66e2d16"),message);


//        messageList.add(message);
        System.out.println(message);
        System.out.println(senderID);
        System.out.println(receiverID);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
//        messageAdapter.notifyItemInserted(messageList.size() - 1);
//        messagesRecyclerView.scrollToPosition(messageList.size() - 1); // Scroll to bottom
    }

    private void fetchMessages(String senderId, String receiverId) {
        // Assuming senderId and receiverId are available
//        UUID senderId = UUID.randomUUID(); // Placeholder
//        UUID receiverId = UUID.randomUUID(); // Placeholder
        AccessChatHistory chatHistory = new AccessChatHistory();
        new AccessChatHistory().getRawMessages(UUID.fromString("43a5d34b-2410-4b6c-a847-ef5fd467ee13"), UUID.fromString("f759d772-9e2c-4a3e-bd8b-1d33a66e2d16"), new AccessChatHistory.RawMessagesListener() {
            @Override
            public void onRawMessagesReceived(List<String> messages) {
                System.out.println(messages);
                System.out.println("**********************************************************");
                runOnUiThread(() -> messageAdapter.setMessages(messages));
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> Toast.makeText(MessageActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show());
            }
        });



    }




}