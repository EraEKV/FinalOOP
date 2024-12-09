package Test;

import System.Credentials;

public class TestCredentials {
    public static void main(String[] args) {
        Credentials c = new Credentials();

        String res = c.generateHash("foerwkof");
        System.out.println(res);
    }
}
