# student-management-system
A Student Management System developed using Java, Swing, JDBC, and MySQL for managing student records.
# Student Management System

A simple, menu-driven **Java console application** for managing student records. The project demonstrates object-oriented programming (OOP) concepts using a `Student` model and a `StudentService` for managing records.

## Features

- Add a student
- View all students
- Search for a student by ID
- Update student details
- Delete a student
- Validate student IDs, names, courses, and ages
- Prevent duplicate student IDs

## Requirements

- Java Development Kit (JDK) 17 or newer

Check your Java installation with:

```bash
java -version
javac -version
```

## Run the application

Open a terminal in the project folder and compile the source:

```bash
javac -d out src/StudentManagementSystem.java
```

Then start the application:

```bash
java -cp out StudentManagementSystem
```

On Windows PowerShell, the same commands work with Windows-style paths:

```powershell
javac -d out src\StudentManagementSystem.java
java -cp out StudentManagementSystem
```

Use the numbered menu to manage student records. Choose `0` to exit.

## OOP concepts demonstrated

- **Classes and objects:** `Student`, `StudentService`, and `StudentManagementSystem`
- **Encapsulation:** student data is private and accessed or changed through methods
- **Constructors:** student objects are initialized with validated values
- **Separation of responsibilities:** the application handles interaction, while the service manages student records

## Project structure

```text
student-management-system/
├── README.md
└── src/
    └── StudentManagementSystem.java
```

## Data storage

Student records are stored in memory and are cleared when the application exits. This project does not use a database or save records to a file.

