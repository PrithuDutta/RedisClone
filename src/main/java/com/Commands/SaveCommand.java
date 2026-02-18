package com.Commands;

import com.Database.Database; 

public class SaveCommand implements Commands {
    @Override
    public String execute(Database db, String[] args) {
        if(args.length != 1) {
            return Protocol.error("ERROR: save command does not take any arguments");
        }
        if(!(db.save())) {
            return Protocol.error("ERROR: DB Failed to save");
        } 
        return Protocol.simpleString("DB Saved");
    }
}