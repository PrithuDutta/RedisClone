package com.Commands;

import com.Database.Database;
public class DelCommand implements Commands {
    @Override
    public String execute(Database db, String[] args) {
        if (args.length != 2) {
            return Protocol.error("ERROR: DEL command requires exactly one argument.");
        }
        String key = args[1];
        boolean deleted = db.del(key);
        if (deleted) {
            return Protocol.simpleString("Key Deleted"); // Key was deleted
        } else {
            return Protocol.simpleString("Key Not Found"); // Key did not exist
        }
    }

}