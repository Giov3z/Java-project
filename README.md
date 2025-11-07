# Java Exam Project

A basic Java project demonstrating fundamental programming concepts suitable for educational and exam purposes.

## Project Structure

```
src/com/exam/project/
├── Main.java          - Main application entry point
├── Calculator.java    - Utility class for arithmetic operations
└── Student.java       - Class demonstrating OOP concepts
```

## Features

- **Calculator Class**: Demonstrates basic arithmetic operations (add, subtract, multiply, divide, power)
- **Student Class**: Shows Object-Oriented Programming principles including:
  - Encapsulation with private fields
  - Constructors
  - Getters and Setters with validation
  - Business logic methods
  - toString() method override
- **Main Class**: Entry point that demonstrates usage of all classes

## How to Compile

From the project root directory, run:

```bash
javac -d bin src/com/exam/project/*.java
```

This will compile all Java files and place the `.class` files in the `bin` directory.

## How to Run

After compiling, run the application with:

```bash
java -cp bin com.exam.project.Main
```

## Alternative: Compile and Run in One Step

If you don't want to create a separate bin directory:

```bash
# Compile
javac src/com/exam/project/*.java

# Run from src directory
cd src
java com.exam.project.Main
cd ..
```

## What You'll See

The program demonstrates:
1. Basic calculator operations
2. Student object creation and manipulation
3. Comparison of student data

## Topics Covered

- Classes and Objects
- Encapsulation
- Constructors
- Methods
- Data validation
- Exception handling (divide by zero)
- String formatting
- Conditional logic

## License

This project is licensed under the AGPL-3.0 License - see the LICENSE file for details.