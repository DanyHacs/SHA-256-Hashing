import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class SHA256Hashing {

    // Method to hash a string using SHA-256
    public static String hashString(String input) throws NoSuchAlgorithmException {
        // Create a SHA-256 MessageDigest instance
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(input.getBytes()); // Compute hash
        
        // Convert the byte array into a readable Base64 encoded string
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    // Method to hash a file using SHA-256
    public static String hashFile(File file) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArray = new byte[1024];
        int bytesRead;
        
        // Read the file in chunks and update the digest with each chunk
        while ((bytesRead = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesRead);
        }
        
        fis.close();
        
        // Convert the final hash to a Base64 string
        byte[] hashedBytes = digest.digest();
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    public static void main(String[] args) {
        try {
            // Hashing a string
            String message = "This is a sample message!";
            System.out.println("Original Message: " + message);
            String hashedMessage = hashString(message);
            System.out.println("SHA-256 Hash of Message: " + hashedMessage);

            // Check file integrity by comparing hashes (you can replace the file path here)
            File file = new File("path_to_your_file.txt");
            String fileHash = hashFile(file);
            System.out.println("SHA-256 Hash of File: " + fileHash);
            
            // Example known hash (you should replace this with a real hash of the file for validation)
            String knownHash = "the_known_hash_value_of_the_file";
            
            // Validate file integrity
            if (fileHash.equals(knownHash)) {
                System.out.println("File integrity validated: Hashes match.");
            } else {
                System.out.println("File integrity failed: Hashes do not match.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
