package Menu;

import Database.Database;
import Users.User;
import System.Log;

import java.io.IOException;

interface Command {
    void execute();

    default void logging(String method, User user) {
        Database db = Database.getInstance();
        db.getLogs().add(new Log(method, user));

        try {
            Database.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}