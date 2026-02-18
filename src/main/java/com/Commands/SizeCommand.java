package com.Commands;
import com.Database.Database;
public class SizeCommand implements Commands {
    @Override
    public String execute (Database db, String[] args) {
        if (args.length != 1) {
            return Protocol.error("ERROR: SIZE command does not take any arguments.");
        }
        int size = db.size();
        return Protocol.integer(size);
    }
}
