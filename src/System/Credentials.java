package System;


import Database.Database;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Credentials implements Serializable {
    private String email;
    private String password;

    public Credentials() {}


    public Credentials(String email) {
        this.email = email;
    }

    public Credentials(String email, String password) {
        this.email = email;
        this.password = generateHash(password);
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

            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }


    public static String generatePassword() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "#$^*?";

        String allCharacters = upperCaseLetters + lowerCaseLetters + digits + symbols;

        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 7; i++) {
            int randomIndex = random.nextInt(allCharacters.length());
            password.append(allCharacters.charAt(randomIndex));
        }

        return password.toString();
    }


    public boolean compareHash(String pw) {
//        return this.password.equals(generateHash(pw));
        System.out.println(this.password);
        return this.password.equals(pw);
    }


//    logic of generating email if we have collisions with them
    public static String generateEmail(String firstname, String lastname, String userType) {
        String newEmail = "";

        firstname = firstname.toLowerCase();
        lastname = lastname.toLowerCase();

        String splitCharacter = userType.equals("Teacher") ? "." : "_";

        Database db = Database.getInstance();
        for(int i = 0; i < firstname.length(); i++) {
            newEmail = firstname.substring(0, i + 1) + splitCharacter + lastname + "@kbtu.kz";
            if(db.findUserByCredentials(new Credentials(newEmail)) == null) break;
        }

        return newEmail;
    }


    public void changePassword(String oldPassword, String newPassword) {
        if(this.password.equals(Credentials.generateHash(oldPassword))) {
            this.password = generateHash(newPassword);
        }
    }

    @Override
    public String toString() {
        return "Credentials ";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Credentials credentials = (Credentials) o;

        return email.equals(credentials.email);
    }

    @Override
    public int hashCode() {
        int res = 31;
        res = res * 31 + (email != null ? email.hashCode() : 0);

        return res;
    }
}
