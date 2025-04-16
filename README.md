# Hotel Management System

## Description

Hotel Management System is a text-based application for managing hotel operations. Developed in Java (version 17) and utilizing a Maven project structure, it enables users to manage rooms, handle guest check-ins and check-outs, view detailed room information, and persist application data using CSV files.

## Features & Commands

The system operates via text commands entered by the user:

* **`prices`**: Displays a list of all available rooms along with their price per night.
* **`view <room_number>`**: Shows detailed information about a specific room, including its occupancy status and guest details if occupied. An error message is displayed if the room number is invalid.
* **`checkin <room_number>`**: Registers guests into a selected room. The system verifies room availability and records stay details, such as the check-in date and the list of guests.
* **`checkout <room_number>`**: Checks guests out of a specified room and calculates the total amount due based on the check-in and check-out dates. Error messages are shown for invalid room numbers or if the room is already vacant.
* **`list`**: Displays a comprehensive list of all rooms, indicating their occupancy status and guest information for occupied rooms.
* **`save`**: Persists the current state of the hotel (room status, guest details, reservations) to the `hotel-data.csv` file.
* **`exit`**: Terminates the application.

## Project Structure

The project follows a standard Maven layout and is organized into modules and packages for better maintainability and development:

```plaintext

├── main                     # Main application module
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── pl
│   │   │   │       └── edu
│   │   │   │           └── agh
│   │   │   │               └── kis
│   │   │   │                   └── pz1
│   │   │   │                       ├── commands    # Contains command classes (CheckinCommand, ListCommand, etc.)
│   │   │   │                       ├── model       # Contains data model classes (Guest, Hotel, Room, etc.)
│   │   │   │                       ├── CsvReader.java
│   │   │   │                       ├── CsvWriter.java
│   │   │   │                       └── Main.java   # Application entry point
│   │   │   └── resources
│   │   │       └── hotel-data.csv  # Default data file
│   │   └── test                   # Unit tests for the main module
│   └── target                     # Compiled code and packaged JAR
│       └── main-1.0-SNAPSHOT.jar  # Executable JAR
├── utils                    # Utility module
│   └── src
│       ├── main
│       │   └── java             # Source code for utility classes (e.g., MyMap implementation)
│       └── test                 # Unit tests for the utils module
├── pom.xml                  # Main Maven project configuration
└── README.md                # This file
```

* **`main` Module:** Contains the core application logic.
    * `pl.edu.agh.kis.pz1.commands`: Implements the command pattern for handling user input.
    * `pl.edu.agh.kis.pz1.model`: Defines the data structures representing the hotel, rooms, guests, and reservations.
    * `pl.edu.agh.kis.pz1`: Includes the `Main` class (entry point) and CSV handling logic (`CsvReader`, `CsvWriter`).
    * `src/main/resources`: Holds resource files like the default `hotel-data.csv`.
* **`utils` Module:** Provides helper classes and data structures, such as the `MyMap` implementation used within the project.

## Getting Started

### Prerequisites

* Java Development Kit (JDK) 17 or later.
* Apache Maven.

### Installation & Running

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/xhamera1/Hotel-app.git
    cd Hotel-app
    ```

2.  **Build the Project:**
    Navigate to the root directory of the project (where the main `pom.xml` is located) and run the Maven install command. This will compile the code, run tests, and package the application.
    ```bash
    mvn clean install
    ```

3.  **Run the Application:**
    Once the build is successful, you can run the application using the generated JAR file:
    ```bash
    java -jar main/target/main-1.0-SNAPSHOT.jar
    ```
    The application will start, and you can begin using the commands listed in the [Features & Commands](#features--commands) section.

## Code Quality

The project has been analyzed using SonarQube, yielding positive results regarding code quality:

* **Test Coverage:** 92.4%
* **Issues:** 0 open issues related to reliability, security, or maintainability.

## Technologies Used

* **Language:** Java 17
* **Build Tool:** Apache Maven
* **Data Persistence:** CSV (Comma Separated Values) files
