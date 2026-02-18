package com.Commands;
import com.Database.Database;

public interface Commands {
    String execute (Database db, String[] args);
}