package com.Commands;

import com.Database.Database;
public class ExistsCommand implements Commands {
    @Override
    public String execute(Database db, String[] args) {
        if (args.length != 2) {
            return Protocol.error("ERROR: EXISTS command requires exactly one argument.");
        }
        String key = args[1];
        return db.exists(key) ? "1" : "0";
    }

}