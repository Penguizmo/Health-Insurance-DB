# Health Insurance Database Management System

## Overview
The **Health Insurance Database Management System** is a Java-based application designed to manage and maintain records for a health insurance system. It provides functionalities to manage patients, doctors, prescriptions, visits, and insurance details. The system uses a MySQL database to store and retrieve data and provides a graphical user interface (GUI) for user interaction.

### Key Features:
- **Patient Management**: Add, update, delete, and search patient records.
- **Doctor Management**: Manage doctor details, including specialists.
- **Prescription Management**: Create, update, delete, and search prescriptions.
- **Visit Records**: Manage visit records, including symptoms and diagnoses.
- **Insurance Management**: Link patients to their insurance providers.
- **Database Integration**: Uses MySQL for data storage and retrieval.

## Minimum Software Requirements
To run this program, ensure the following software is installed on your system:
1. **Java Development Kit (JDK)**: Version 11 or higher.
2. **MySQL Database**: Version 8.0 or higher.
3. **IntelliJ IDEA**: Recommended IDE for development and execution.
4. **Maven**: For dependency management (if not already integrated into IntelliJ IDEA).
5. **Operating System**: Windows 10 or higher.

## Software Used
The program is built using the following technologies:
- **Java**: Core programming language for the application.
- **Swing**: For building the graphical user interface (GUI).
- **MySQL**: Database for storing and managing health insurance data.
- **JDBC**: Java Database Connectivity for database interaction.
- **IntelliJ IDEA**: Integrated Development Environment (IDE) for development.
- **Maven**: For managing dependencies and building the project.

## How to Run
1. Clone the repository or download the source code.
2. Open the project in IntelliJ IDEA.
3. Configure the database connection:
   - Update the `database.properties` file with your MySQL credentials.
   - Ensure the MySQL database `healthinsurancedb` is created and populated with the required tables.
4. Build and run the project using IntelliJ IDEA.
5. The application will launch with a GUI for managing health insurance records.

## Database Configuration
The database connection is configured in the `database.properties` file:
```ini
db.url=jdbc:mysql://localhost:3306/healthinsurancedb
db.user=root
db.password=root
