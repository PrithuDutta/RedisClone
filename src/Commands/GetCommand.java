
package Commands;
import Database.Database;

public class GetCommand implements Commands {
    @Override
    public String execute (Database db, String[] args) {
        if (args.length != 2) {
            return "ERROR: GET command requires exactly one argument.";
        }
        String key = args[1];
        String value = db.get(key);
        if (value == null) {
            return "(nil)";
        }
        return value;
    }
}