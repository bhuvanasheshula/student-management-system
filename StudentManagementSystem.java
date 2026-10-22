import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/** Console application demonstrating object-oriented student management. */
public class StudentManagementSystem {
    private final StudentService studentService = new StudentService();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new StudentManagementSystem().run();
    }

    private void run() {
        System.out.println("=== Student Management System ===");
        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> listStudents();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 0 -> running = false;
                default -> System.out.println("Please choose a number from 0 to 5.");
            }
        }
        System.out.println("Goodbye!");
        scanner.close();
    }

    private void showMenu() {
        System.out.println("\n1. Add student\n2. List students\n3. Search by ID\n4. Update student\n5. Delete student\n0. Exit");
    }

    private void addStudent() {
        int id = readInt("Student ID: ");
        String name = readNonBlank("Name: ");
        int age = readInt("Age: ");
        String course = readNonBlank("Course: ");
        try {
            studentService.addStudent(new Student(id, name, age, course));
            System.out.println("Student added.");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void listStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.printf("%-8s %-24s %-8s %-24s%n", "ID", "NAME", "AGE", "COURSE");
        System.out.println("------------------------------------------------------------------");
        students.forEach(System.out::println);
    }

    private void searchStudent() {
        Optional<Student> student = studentService.findStudent(readInt("Student ID: "));
        System.out.println(student.map(Student::toString).orElse("Student not found."));
    }

    private void updateStudent() {
        int id = readInt("Student ID to update: ");
        Optional<Student> found = studentService.findStudent(id);
        if (found.isEmpty()) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Current: " + found.get());
        String name = readNonBlank("New name: ");
        int age = readInt("New age: ");
        String course = readNonBlank("New course: ");
        try {
            studentService.updateStudent(id, name, age, course);
            System.out.println("Student updated.");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void deleteStudent() {
        int id = readInt("Student ID to delete: ");
        System.out.println(studentService.deleteStudent(id) ? "Student deleted." : "Student not found.");
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Enter a whole number.");
            }
        }
    }

    private String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("This field cannot be empty.");
        }
    }
}

class Student {
    private final int id;
    private String name;
    private int age;
    private String course;

    public Student(int id, String name, int age, String course) {
        if (id <= 0) throw new IllegalArgumentException("ID must be greater than zero.");
        this.id = id;
        setName(name);
        setAge(age);
        setCourse(course);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCourse() { return course; }
    public void setName(String name) { this.name = requireText(name, "Name"); }
    public void setCourse(String course) { this.course = requireText(course, "Course"); }
    public void setAge(int age) {
        if (age < 1 || age > 120) throw new IllegalArgumentException("Age must be between 1 and 120.");
        this.age = age;
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " cannot be empty.");
        return value.trim();
    }

    @Override
    public String toString() {
        return String.format("%-8d %-24s %-8d %-24s", id, name, age, course);
    }
}

class StudentService {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        if (findStudent(student.getId()).isPresent()) throw new IllegalArgumentException("A student with that ID already exists.");
        students.add(student);
    }

    public List<Student> getAllStudents() { return List.copyOf(students); }

    public Optional<Student> findStudent(int id) {
        return students.stream().filter(student -> student.getId() == id).findFirst();
    }

    public boolean updateStudent(int id, String name, int age, String course) {
        Optional<Student> result = findStudent(id);
        if (result.isEmpty()) return false;
        Student student = result.get();
        student.setName(name);
        student.setAge(age);
        student.setCourse(course);
        return true;
    }

    public boolean deleteStudent(int id) {
        return students.removeIf(student -> student.getId() == id);
    }
}
