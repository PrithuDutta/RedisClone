# Java Redis Clone

A lightweight, in-memory key-value store built from scratch in Java.

This project is a clean-room implementation of a Redis-like server, designed to explore core backend engineering concepts including **Network Socket Programming**, **Protocol Design**, and **Object-Oriented Architecture**.

---

## 🧠 The Learning Journey

*Note: This project was built primarily as a deep-dive educational exercise to bridge the gap between "coding" and "systems engineering."*

### Key Concepts Mastered

#### 1. Polymorphism & The Command Pattern
Initially, the server relied on massive `if/else` chains to handle inputs. I refactored this into a scalable **Command Pattern** design.
* **The Breakthrough:** By defining a strict `Commands` interface, the `Server` class no longer needs to know *how* a command works (e.g., `SET` vs `DEL`). It simply looks up the command in a `HashMap` registry and calls `.execute()`.
* **Result:** Adding a new command now requires creating a single class and registering it, with zero changes to the core server logic.

#### 2. Protocol Design (RESP Implementation)
I learned that sending raw text over a network is ambiguous. Does "OK" mean the operation succeeded, or is "OK" the value the user stored?
* **The Solution:** I implemented a subset of the **Redis Serialization Protocol (RESP)**.
    * `+` for Simple Strings (Server-to-Client status messages).
    * `$` for Bulk Strings (Binary-safe user data).
    * `:` for Integers.
* **Outcome:** A robust `Protocol` utility class that separates data logic from presentation logic, ensuring the server and client always agree on the data types being exchanged.

#### 3. Separation of Concerns
This project strictly separates duties to mimic real-world architecture:
* **`Server` (The Manager):** Handles connections and dispatches work. Owns the single source of truth (the Database).
* **`Commands` (The Workers):** stateless logic units that perform operations.
* **`Database` (The Storage):** A wrapper around the in-memory data structures, agnostic to the network layer.

---

## 🛠 Technical Architecture

### Component Overview
```mermaid
graph TD
    Client["Client (Netcat/Telnet)"] <-->|Sockets| Server
    Server -->|Dispatches| CmdMap[Command Registry]
    CmdMap -->|Instantiates| Cmd[Command Interface]
    Cmd -->|Reads/Writes| DB[(In-Memory Database)]
    Cmd -->|Formats Response| Proto[Protocol Helper]
