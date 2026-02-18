package com.Commands;
import com.Database.Database;
public class SetCommand implements Commands {
    @Override
    public String execute (Database db, String[] args) {
        if (args.length != 3) {
            return Protocol.error("ERROR: SET command requires exactly two arguments.");
        }
        String key = args[1];
        String value = args[2];
        db.set(key, value);
        return Protocol.simpleString("OK");
    }
    
}