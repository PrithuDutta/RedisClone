package Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;



public class Database implements Serializable{
    private static final long serialVersionUID = 1L; 
    private final ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();

    public String get(String key) {
        return store.get(key);
    }

    public void set(String key, String value) {
        store.put(key, value);
    }

    public boolean del(String key) {
        return store.remove(key) != null;
    }

    public boolean exists(String key) {
        return store.containsKey(key);
    }
    
    public int size() {
        return store.size();
    }

    public boolean save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("db.rdb"))) {
            out.writeObject(store); 
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public boolean load() {
        File rdb = new File("db.rdb");
        if (!rdb.exists()) {
            System.out.println("No save file found. Starting with empty database.");
            return true;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(rdb))) {
            ConcurrentHashMap<String, String> loadedStore = (ConcurrentHashMap<String, String>) in.readObject();
        
            store.clear();
            store.putAll(loadedStore);
            
            System.out.println("DB loaded: " + store.size() + " keys.");
            return true; 
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading DB: " + e.getMessage());
            return false; 
        }
    }
}