# Health Insurance DB

---

## Overview

The **Health Insurance DB** is a Java-based application designed to manage patient records, doctor information, prescriptions, insurance policies, and visit records. Built using **Java 21**, **Swing** for the GUI, and **MariaDB/MySQL** for database management, this project adheres to **Object-Oriented Programming (OOP)** principles and follows the **Model-View-Controller (MVC)** design pattern.

This system provides a user-friendly interface for performing CRUD (Create, Read, Update, Delete) operations and includes advanced filtering capabilities for efficient data management.

---

## Features

### 1. **Patient Management**
- **Classes**: `Patient`, `InsuredPatient`
- Manage patient records with support for CRUD operations.
- Includes functionality for insured patients via inheritance (`InsuredPatient` extends `Patient`).

### 2. **Doctor and Specialist Tracking**
- **Classes**: `Doctor`, `Specialist`
- Track doctor and specialist details, including their specialties and contact information.

### 3. **Prescription and Drug Information**
- **Classes**: `Prescription`, `Drug`
- Handle prescription details, including drug names, dosages, and associated doctors.

### 4. **Insurance Policy Management**
- **Class**: `Insurance`
- Manage insurance company details and policyholder information.

### 5. **Visit Records**
- **Class**: `Visit`
- Log and manage patient visit records, including timestamps, diagnoses, and treatments.

### 6. **Advanced Filtering**
- Search and filter records across entities (e.g., patients, doctors, prescriptions) using intuitive GUI controls.

### 7. **Data Validation**
- Validate user inputs in the GUI to ensure data integrity and prevent invalid entries.

---

## Technologies Used

- **Programming Language**: Java (Version 21)
- **GUI Framework**: Swing (`.form` and `.java` files for UI components)
- **Database**: MariaDB/MySQL (configured in `database.properties`)
- **Build Tool**: Maven (managed via `pom.xml`)

---

## Repository

The source code for this project is hosted on GitHub:

- **Repository URL**: [https://github.com/Penguizmo/Health-Insurance-DB.git](https://github.com/Penguizmo/Health-Insurance-DB.git)

---

## Setup and Installation

### Prerequisites
1. **Java 21**: Ensure Java 21 is installed on your system.
2. **MySQL**: Install and run MySQL locally or on a server.
3. **Maven**: Install Maven for dependency and build management.

### Steps
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Penguizmo/Health-Insurance-DB.git
   cd Health-Insurance-DB
   ```

2. **Configure Database**:
   - Update the `database.properties` file with your MySQL connection details:
     ```properties
     db.url=jdbc:mysql://localhost:3306/healthinsurancedb
     db.user=root
     db.password=root
     ```

3. **Initialize the Database**:
   - Run the `init.sql` script (if provided) to create the database schema and populate it with sample data:
     ```bash
     mysql -u root -p healthinsurancedb < init.sql
     ```
   - Ensure the `init.sql` script includes realistic sample data and comments explaining each step of the initialization process.

4. **Build and Run the Application**:
   - Use Maven to build and launch the application:
     ```bash
     mvn clean install
     mvn exec:java
     ```

---

## Project Structure

```
src/
├── models/               # Entity classes (e.g., Patient, Doctor, Visit)
├── database/             # DAO classes (e.g., VisitDAO, PrescriptionDAO)
├── gui/                  # Swing UI components (e.g., VisitPage, PrescriptionPage)
├── utils/                # Utility classes (e.g., DatabaseConnection)
resources/
└── database.properties   # Database configuration file
```

---

## Design Requirements

The project adheres to the following design principles to ensure maintainability, scalability, and robustness:

1. **Abstraction, Encapsulation, and Information Hiding**:
   - Abstraction is used to define clear interfaces for interacting with objects.
   - Encapsulation ensures that internal states are hidden and accessed only through well-defined methods.
   - Information hiding minimizes the exposure of implementation details.

2. **Inheritance**:
   - Inheritance is used where appropriate, such as the `InsuredPatient` class extending the `Patient` class to model specialized behavior.

3. **Polymorphism**:
   - Polymorphism is implemented through method overriding and interfaces, enabling flexible and reusable code.

4. **Private Class-Wide Variables**:
   - All class-wide variables are declared private to prevent content coupling and ensure encapsulation.

5. **Minimal Common Coupling**:
   - Class-wide variables are kept to a minimum to reduce common coupling between classes.

6. **Data Coupling**:
   - Data coupling (parameter passing) is preferred over content or common coupling to ensure loose coupling between components.

7. **Avoidance of Unnecessary Data Coupling**:
   - The program avoids excessive parameter passing, ensuring that only necessary data is shared between methods and classes.

8. **High Cohesion**:
   - Classes are designed to be highly cohesive, meaning each class has a single, well-defined responsibility.

---

## Academic Context

This project demonstrates:
1. **Database Design**: A well-structured schema implemented through entity classes and DAOs.
2. **OOP Principles**: Effective use of abstraction, encapsulation, inheritance, polymorphism, and cohesion.
3. **GUI Development**: Intuitive and functional user interfaces built with Swing.
4. **MVC Pattern**: Clear separation of concerns between models, views, and controllers.

---

## License

This project is intended for academic purposes only. Please refer to the license file for more details.
