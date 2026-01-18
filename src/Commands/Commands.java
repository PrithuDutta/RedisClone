package Commands;
import  Database.Database;

public interface Commands {
    String execute (Database db, String[] args);
}