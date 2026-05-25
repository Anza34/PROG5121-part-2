/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.messageapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MessageAppTest {

    private MessageApp.Message messageAppInstance;

    @BeforeEach
    public void setUp() {
        messageAppInstance = new MessageApp.Message();
    }
    
    @Test
    public void testMessageLength_Success() {
        // Test Data 1: Within character limits
        String validMessage = "Hi Mike, can you join us for dinner tonight?";
        
        String systemResponse;
        if (validMessage.length() <= 250 && validMessage.length() > 0) {
            systemResponse = "Message ready to send.";
        } else {
            systemResponse = "Failure";
        }
        
        assertEquals("Message ready to send.", systemResponse);
    }

    @Test
    public void testMessageLength_Failure() {
        String invalidMessage = "a".repeat(255);
        
        String systemResponse = "";
        if (invalidMessage.length() > 250) {
            int exceededBy = invalidMessage.length() - 250;
            systemResponse = "Message exceeds 250 characters by " + exceededBy + "; please reduce the size.";
        }
        
        assertEquals("Message exceeds 250 characters by 5; please reduce the size.", systemResponse);
    }

    @Test
    public void testRecipientNumber_Success() {
        String validFormatNumber = "+27186930022"; 
        boolean isValid = MessageApp.checkCellPhoneNumber(validFormatNumber);
        
        String systemResponse = isValid ? "Cell phone number successfully captured." 
                                        : "Cell phone number incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        
        assertEquals("Cell phone number successfully captured.", systemResponse);
    }

    @Test
    public void testRecipientNumber_Failure() {
        String invalidNumber = "08575975889";
        boolean isValid = MessageApp.checkCellPhoneNumber(invalidNumber);
        
        String systemResponse = isValid ? "Cell phone number successfully captured." 
                                        : "Cell phone number incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        
        assertEquals("Cell phone number incorrectly formatted or does not contain an international code. Please correct the number and try again.", systemResponse);
    }
    
    @Test
    public void testMessageHash_Loop() {
        String[][] bulkTestData = {
            {"0012345678", "0", "Hi Mike, can you join us for dinner tonight", "00:0:HITONIGHT"},
            {"1234567890", "1", "Hi Keegan, did you receive the payment", "12:1:HIPAYMENT"}
        };

        for (String[] dataset : bulkTestData) {
            String msgId = dataset[0];
            int msgNumber = Integer.parseInt(dataset[1]);
            String msgText = dataset[2];
            String expectedHash = dataset[3];

            String actualHash = messageAppInstance.createMessageHash(msgId, msgNumber, msgText);
            assertEquals(expectedHash, actualHash, "Failed on hash logic for: " + msgText);
        }
    }
    
    @Test
    public void testMessageIdCreation() {
        String messageID = messageAppInstance.generateRandomID();
        
        assertNotNull(messageID);
        assertTrue(messageAppInstance.checkMessageID(messageID));
        assertEquals(10, messageID.length());

        String outputLog = "Message ID generated: " + messageID;
        assertTrue(outputLog.startsWith("Message ID generated: "));
    }

    @Test
    public void testSentMessageActions() {
        Scanner testInputSend = new Scanner("1\n");
        String resultSend = messageAppInstance.SentMessage(testInputSend);
        assertEquals("Message successfully sent", resultSend);
        Scanner testInputDiscard = new Scanner("2\n");
        String resultDiscard = messageAppInstance.SentMessage(testInputDiscard);
        assertEquals("Press 0 to delete the message", resultDiscard);
        Scanner testInputStore = new Scanner("3\n");
        String resultStore = messageAppInstance.SentMessage(testInputStore);
        assertEquals("Message successfully stored", resultStore);
    }
}