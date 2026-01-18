package Database;

import java.util.concurrent.ConcurrentHashMap;

public class Database {
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

}