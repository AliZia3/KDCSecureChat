package com.example.chatapp;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.chatapp.Backend.MessageMangagement.AccessChatHistory;
import com.google.firebase.FirebaseApp;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class TestMain {

    @Test
    public void testGetRawMessages() throws InterruptedException {
        // Initialize FirebaseApp
        Context appContext = InstrumentationRegistry.getTargetContext();
        FirebaseApp.initializeApp(appContext);

        // Your UUIDs here
        UUID senderId = UUID.fromString("43a5d34b-2410-4b6c-a847-ef5fd467ee13");
        UUID receiverId = UUID.fromString("f759d772-9e2c-4a3e-bd8b-1d33a66e2d16");

        // Use a CountDownLatch to wait for the Firebase callback
        final CountDownLatch latch = new CountDownLatch(1);

        AccessChatHistory chatHistory = new AccessChatHistory();
        chatHistory.getRawMessages(senderId, receiverId, new AccessChatHistory.RawMessagesListener() {
            @Override
            public void onRawMessagesReceived(List<String> messages) {
                // Assertions here
                assertNotNull(messages);
                assertFalse(messages.isEmpty());
                System.out.println(messages);
                for (String message : messages) {
                    System.out.println("Message: " + message);
                }
                latch.countDown();
            }

            @Override
            public void onError(String error) {
                fail("Failed to fetch messages: " + error);
                latch.countDown();
            }
        });

        // Wait for Firebase to respond
        latch.await(10, TimeUnit.SECONDS);

    }
}
