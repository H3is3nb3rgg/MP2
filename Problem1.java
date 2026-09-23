import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numStudents = 0;
        while (true) {
            System.out.print("Enter number of students (1 to 10): ");
            if (scanner.hasNextInt()) {
                numStudents = scanner.nextInt();
                if (numStudents >= 1 && numStudents <= 10) {
                    break;
                }
            } else {
                scanner.next();
            }
            System.out.println("Invalid input. Please enter a number between 1 and 10.");
        }
        scanner.nextLine();

        Student[] students = new Student[numStudents];

        for (int i = 0; i < numStudents; i++) {
            System.out.println("\n--- Student " + (i + 1) + " ---");
            System.out.print("Enter Student ID: ");
            String id = scanner.nextLine().trim();

            System.out.print("Enter Full Name: ");
            String name = scanner.nextLine().trim();

            int[] scores = new int[3];
            for (int j = 0; j < 3; j++) {
                while (true) {
                    System.out.print("Enter score " + (j + 1) + " (0-100): ");
                    if (scanner.hasNextInt()) {
                        int score = scanner.nextInt();
                        if (score >= 0 && score <= 100) {
                            scores[j] = score;
                            break;
                        }
                    } else {
                        scanner.next();
                    }
                    System.out.println("Invalid score! Score must be between 0 and 100.");
                }
            }
            scanner.nextLine();

            students[i] = new Student(id, name, scores);
        }

        System.out.println("\n================ STUDENT SUMMARY TABLE ================");
        System.out.printf("%-10s %-20s %-10s %-10s%n", "ID", "Name", "Average", "Status");
        System.out.println("-------------------------------------------------------");

        Student topStudent = students[0];
        for (Student s : students) {
            double avg = s.calculateAverage();
            System.out.printf("%-10s %-20s %-10.2f %-10s%n", s.getId(), s.getName(), avg, s.getStatus());
            if (avg > topStudent.calculateAverage()) {
                topStudent = s;
            }
        }

        System.out.println("=======================================================");
        System.out.printf("Highest Average Student: %s (Average: %.2f)%n", topStudent.getName(), topStudent.calculateAverage());
        System.out.println("Total Student Objects Created: " + Student.getStudentCount());

        scanner.close();
    }
}

class Student {
    private String id;
    private String name;
    private int[] scores;
    private static int studentCount = 0;

    public Student(String id, String name, int[] scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
        studentCount++;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int[] getScores() {
        return this.scores;
    }

    public double calculateAverage() {
        int sum = 0;
        for (int score : this.scores) {
            sum += score;
        }
        return (double) sum / this.scores.length;
    }

    public String getStatus() {
        return calculateAverage() >= 75 ? "PASSED" : "FAILED";
    }

    public static int getStudentCount() {
        return studentCount;
    }
}
