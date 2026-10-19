package quickchat;
import java.util.UUID;


public class Message {
// Fields that hold message information
    private final String messageId;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    
// Message data is setup by a constructor
    
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageId = createTenDigitId();
        this.messageHash = createMessageHash();
    }

    // UUID provides random characters; digits are taken until 10 are collected
    private String createTenDigitId() {
        String source = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        while (source.length() < 10) {
            source += UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        }
        return source.substring(0, 10);
    }

   
     // Evaluates if the tracking number is successfully populated and adheres to length bounds.
    
    public boolean checkMessageID() {
        return messageId != null && messageId.length() <= 10;
    }

    
     //  ensuring international code formatting (+27 followed by 9 digits).
     
    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("\\+27[0-9]{9}")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    
     // Rejects empty text and checks standard 250-character limit bounds.
     
    public String checkMessageLength() {
        if (messageText == null) {
            return "Please enter a message.";
        }
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        return "Message exceeds 250 characters by " + (messageText.length() - 250) + "; please reduce the size.";
    }

    // Creates the message hush code 
    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");
        String firstWord = cleanWord(words[0]);
        String lastWord = cleanWord(words[words.length - 1]);
        
        return messageId.substring(0, 2) + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
    }

    // No speacial characters
    
    private String cleanWord(String word) {
        return word.replaceAll("[^A-Za-z0-9]", "");
    }

    // Returns a status phrase for processing selections
    public String SentMessage() {
        return "Choose Send Message, Disregard Message, or Store Message.";
    }

   // Display all attributes
    public String printMessages() {
        return "Message ID: " + messageId
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }
// Return total messages
    
    public int returnTotalMessagess() {
        return 1;
    }

    
     // Converts properties dynamically into an escaped standard JSON object string format.
     
    public String toJson() {
        return "{\"messageId\":\"" + jsonEscape(messageId)
                + "\",\"messageHash\":\"" + jsonEscape(messageHash)
                + "\",\"recipient\":\"" + jsonEscape(recipient)
                + "\",\"message\":\"" + jsonEscape(messageText) + "\"}";
    }

    // Standard character escape utility to protect target JSON storage structural readability.
     
    private String jsonEscape(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    // Standard Getters methods to retrieve the field 
    
    public String getMessageId() { return messageId; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
}



    