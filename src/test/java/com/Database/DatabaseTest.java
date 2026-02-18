package com.Database;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;


public class DatabaseTest {
    private Database db;

    @BeforeEach
    public void setUp() {
        db = new Database();
    }

   @Test
    @DisplayName("Should correctly store and retrieve standard strings")
    void testBasicOperations() {
        db.set("username", "prithu_dutta");
        db.set("status", "active");

        assertAll("Basic Checks",
            () -> assertEquals("prithu_dutta", db.get("username")),
            () -> assertEquals("active", db.get("status")),
            () -> assertTrue(db.exists("username")),
            () -> assertFalse(db.exists("password")) // Should not exist
        );
    }

    @Test
    @DisplayName("Should correctly overwrite existing keys")
    void testOverwriteBehavior() {
        db.set("counter", "10");
        assertEquals("10", db.get("counter"));

        // Overwrite
        db.set("counter", "20");
        assertEquals("20", db.get("counter"), "Value should be updated to 20");
        
        assertEquals(1, db.size()); 
    }

    @Test
    @DisplayName("Should handle empty strings as valid values")
    void testEmptyStrings() {
        db.set("empty_val", "");
        db.set("", "empty_key_val");

        assertEquals("", db.get("empty_val"), "Should store empty string value");
        assertEquals("empty_key_val", db.get(""), "Should allow empty string as key");
    }

    @Test
    @DisplayName("Should handle whitespace and casing correctly")
    void testWhitespaceAndCasing() {
        // Redis keys are case-sensitive. "User" != "user"
        db.set("User", "Admin");
        db.set("user", "Guest");

        assertNotEquals(db.get("User"), db.get("user"), "Keys must be case-sensitive");
        
        // Keys with spaces (Simulating "Get 'My Files'")
        db.set("My Files", "data.txt");
        assertEquals("data.txt", db.get("My Files"), "Should handle keys with spaces");
    }

    @Test
    @DisplayName("Should handle special characters and Emojis")
    void testSpecialCharacters() {
        String key = "user:🔥";
        String value = "Prithu 🚀";

        db.set(key, value);
        assertEquals(value, db.get(key), "Database must support UTF-8/Emojis");
        
        // Test newline characters (common injection attack vector)
        db.set("line_break", "Line1\nLine2");
        assertEquals("Line1\nLine2", db.get("line_break"));
    }

    @Test
    @DisplayName("Should handle very large payloads without crashing")
    void testLargePayload() {
        // Generate a 1MB string
        StringBuilder largeVal = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            largeVal.append("DATA");
        }
        String bigString = largeVal.toString();

        db.set("big_data", bigString);
        
        String retrieved = db.get("big_data");
        assertEquals(bigString.length(), retrieved.length(), "Should store 40KB+ string without truncation");
        assertEquals(bigString, retrieved);
    }

    @Test
    @DisplayName("Should return null for non-existent keys")
    void testNullBehavior() {
        assertNull(db.get("fake_key"), "Get on missing key should return null");
        
        // Delete non-existent key (Should not crash)
        assertDoesNotThrow(() -> db.del("fake_key"));
    }

}
