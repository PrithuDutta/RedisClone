
package com.Commands;
import com.Database.Database;

public class GetCommand implements Commands {
    @Override
    public String execute (Database db, String[] args) {
        if (args.length != 2) {
            return Protocol.error("ERROR: GET command requires exactly one argument.");
        }
        String key = args[1];
        return Protocol.bulkString(db.get(key));
    }
}