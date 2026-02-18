package com.Commands;

import com.Database.Database; 

public class LoadCommand implements Commands{
    @Override
    public String execute(Database db, String[] args) {
        if(args.length != 1) {
            return Protocol.error("ERROR: load command cannot take any arguments");
        }

        if(!(db.load())) {
            return Protocol.error("ERROR: DB failed to load"); 
        }
        return Protocol.simpleString("DB loaded sucessfully"); 
    }
}