package System;


import Database.Database;

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
    public  String generateHash(String password) {
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


    public boolean compareHash(String password) {
        return password.equals(generateHash(password));
    }


//    logic of generating email if we have collisions with them
    public String generateEmail(String firstname, String lastname) {
        String newEmail = "";
        Database db = Database.getInstance();

        for(int i = 0; i < firstname.length(); i++) {
            newEmail = firstname.charAt(i) + "_" + lastname + "@kbtu.kz";
            if(db.findUserByEmail(newEmail) == null) break;
        }

        return newEmail;
    }


    public boolean verifyPassword(String originalPassword, String hashedPassword) {
        String newHash = generateHash(originalPassword);

        return newHash.equals(hashedPassword);
    }



    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Credentials credentials = (Credentials) o;

        return email.equals(credentials.email)
                && password.equals(credentials.password);
    }

    @Override
    public int hashCode() {
        int res = 31;
        res = res * 31 + (email != null ? email.hashCode() : 0);
        res = res * 31 + (password != null ? password.hashCode() : 0);

        return res;
    }
}
