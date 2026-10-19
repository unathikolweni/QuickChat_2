package quickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    //Verifies that messages under 250 characters return "Message ready to send."
    
    @Test
    public void testMessageLengthSuccess() {
        // Test Data for Task 1 from Assignment Sheet
        String recipient = "+27718693002";
        String content = "Hi Mike, can you join us for dinner tonight?";
        
        Message message = new Message(0, recipient, content);
        
        assertEquals("Message ready to send.", message.checkMessageLength());
    }

  
     // Verifies that messages exceeding 250 characters return the precise character overage text.
    
    @Test
    public void testMessageLengthFailure() {
        // Construct an intentionally long string (258 characters long)
        String longText = "This is a very long text message component that is designed to exceed the "
                        + "standard maximum character allowance limit set by the assignment instructions. "
                        + "It must cross over two hundred and fifty individual characters to cause the validation "
                        + "method check to trigger an overage exception metric.";
        
        int excessAmount = longText.length() - 250;
        Message message = new Message(0, "+27718693002", longText);
        
        String expectedFailureMessage = "Message exceeds 250 characters by " + excessAmount + "; please reduce the size.";
        
        assertEquals(expectedFailureMessage, message.checkMessageLength());
    }

    
     // Verifies that international layout numbers (+27 followed by 9 digits) are captured successfully.
    
    @Test
    public void testRecipientNumberSuccess() {
        // Test Data for Task 1 from Assignment Sheet
        String validRecipient = "+27718693002";
        Message message = new Message(0, validRecipient, "Hi Mike");
        
        assertEquals("Cell phone number successfully captured.", message.checkRecipientCell());
    }

    
     // Verifies that incorrectly formatted cell numbers return the full troubleshooting error text.
     
    @Test
    public void testRecipientNumberFailure() {
        // Cell number missing the required '+' international prefix structure
        String invalidRecipient = "0718693002";
        Message message = new Message(0, invalidRecipient, "Hi Mike");
        
        String expectedFailureMessage = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        
        assertEquals(expectedFailureMessage, message.checkRecipientCell());
    }

   
     // Verifies the core hash extraction rules and tests the remainder of message hashes using a loop.
     
    @Test
    public void testMessageHash() {
        // 1. Verify Test Case 1 matching expected logic component ending rules (":0:HITONIGHT")
        String recipient = "+27718693002";
        String content = "Hi Mike, can you join us for dinner tonight?";
        Message testCase1 = new Message(0, recipient, content);
        
        String generatedHash1 = testCase1.createMessageHash();
        
        // verify the correct text parsing format loop tags directly:
        assertTrue(generatedHash1.endsWith(":0:HITONIGHT"), 
                "Hash should end with index number and capitalized words. Found: " + generatedHash1);

        // Test the remainder of message hashes inside a loop array block
        String[] sampleTexts = {
            "I love you", 
            "I am going out", 
            "I want to drive that car"
        };
        
        String[] expectedHashEndings = {
            ":1:IYOU",
            ":2:IOUT",
            ":3:ICAR"
        };

        for (int i = 0; i < sampleTexts.length; i++) {
            Message loopedMsg = new Message(i + 1, recipient, sampleTexts[i]);
            String currentHash = loopedMsg.createMessageHash();
            
            assertTrue(currentHash.endsWith(expectedHashEndings[i]), 
                    "Looped hash mismatch at index " + i + ". Found: " + currentHash);
        }
    }

    
     //  Validates that the auto-generated tracking ID satisfies boolean structural validations.
    
    @Test
    public void testMessageIdGeneration() {
        Message message = new Message(0, "+27718693002", "Hi Mike");
        
        // Assert that the boolean checkpoint returns true (Id exists and is <= 10 digits long)
        assertTrue(message.checkMessageID());
        assertNotNull(message.getMessageId());
        assertEquals(10, message.getMessageId().length());
    }

     // Confirms that the status instruction hints method is functioning as defined.
     
    @Test
    public void testSentMessagePlaceholder() {
        Message message = new Message(0, "+27718693002", "Hi Mike");
        
        // Matches line 91 of your actual Message.java file implementation output text exactly
        String expectedMessage = "Choose Send Message, Disregard Message, or Store Message.";
        
        assertEquals(expectedMessage, message.SentMessage());
    }
}
   