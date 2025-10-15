Author: Rogelis Garcia
github: rogelisdev
JAVA

# 📚 LibroNova: Library Management System

## 1. Project Overview

**LibroNova** is an internal management system for a network of libraries designed to manage the book catalog, register partners (members), and handle loans, enforced by strict role control and business validations.

### Core Features

* **Catalog Management:** Full CRUD operations for books (via `MenuMain`).
* **Partner Management:** Restricted CRUD operations for partners.
* **Access Control:** Login system and Main Menu restricted by user roles (`ADMIN`/`PARTNER`).
* **Architecture:** Explicit separation into DAO, Service, and UI layers.

---

## 2. Project Architecture and Structure

The system is strictly separated into the following packages (folders), ensuring high maintainability:

| Package | Layer Name | Responsibility | Key Classes |
| :--- | :--- | :--- | :--- |
| **`ui`** | **Presentation** | Handles all user interaction (Menus, JOptionPane) and the application flow. | `PartnerApp`, `PartnerUIImpl`, `MenuMain`. |
| **`service`** | **Business Logic** | Contains the core business rules and orchestrates data flow between UI and DAO. | `PartnerServiceImpl`, `BookServiceImpl`. |
| **`validations`** | **Validation** | Implements rules to check data integrity before processing (e.g., format, required fields). | `PartnerValidationImpl`. |
| **`dao`** | **Data Access** | Manages all SQL and direct communication with database tables. | `PartnerDAOImpl`, `BookDAOImpl`. |
| **`db`** | **Database Config** | Contains utilities for establishing and managing database connections. | `ConnectionDB` . |
| **`model`** | **Entities** | Contains the data structure classes used across all layers. | `Partner`, `Book`, `User`, `Role`. `Load`.|

---

## 3. Control de Roles and Login

Access to the main menu and CRUD functions is restricted based on the authenticated user's role.

### Login Credentials (Simulated Data)

| Role | Username | Password | Permissions |
| :--- | :--- | :--- | :--- |
| **ADMIN** | `admin` | `1234` | Full CRUD access for Partners and Books. |
| **PARTNER** | `partner` | `1234` | Read-only access (List/Search Partners). |

### Role Restrictions Summary

| Functionality | ADMIN | PARTNER |
| :--- | :--- | :--- |
| **Add/Update/Delete Partner (CRUD)**| ✅ Yes | ❌ No |
| **Manage Books** | ✅ Yes | ❌ No |
| **List/Search Partners (Read)** | ✅ Yes | ✅ Yes |

---

## 4. Getting Started

### Step 1: Create the Main Executable Class

To launch the application, ensure you have a dedicated `App.java` class:

```java

// App.java 
public class App {
    public static void main(String[] args) {
        new PartnerApp().start(); // Launches the application flow
    }
}
