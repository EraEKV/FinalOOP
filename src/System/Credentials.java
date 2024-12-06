package System;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Credentials {
    private String email;
    private String password;

    public Credentials() {}


    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }


//    getter
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


//  SHA-256 algorithm for hash without using salt so hash can be weak
    public static String generateHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());

            String hexString = "";
            for (byte b : hashBytes) {
                hexString += String.format("%02x", b);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }

    public String generatePassword() {
        return "";
    }


//    logic of generating email if we have collisions with them
    public String generateEmail(String firstname, String lastname) {
        String newEmail = "";

        for(int i = 0; i < firstname.length(); i++) {
            newEmail = firstname.charAt(i) + "_" + lastname + "@kbtu.kz";
            if(checkForEmail(newEmail)) break;
        }

        return newEmail;
    }


    public static boolean verifyPassword(String originalPassword, String hashedPassword) {
        String newHash = generateHash(originalPassword);

        return newHash.equals(hashedPassword);
    }



//    return true if everything is ok
    private Boolean checkForEmail(String email) {
//        Database db = Database.getInstance();
//        HashMap<Credentials, User> = db.getUsers();

        return true;
    }
}
