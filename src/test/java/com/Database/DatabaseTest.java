package com.Database;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


public class DatabaseTest {
    private Database db;

    @BeforeEach
    public void setUp() {
        db = new Database();
    }

    @Test
    void testSetAndGet() {
        // Arrange & Act
        db.set("user", "prithu");
        String result = db.get("user");

        // Assert
        assertEquals("prithu", result, "Database should return the value we just set");
    }
}
