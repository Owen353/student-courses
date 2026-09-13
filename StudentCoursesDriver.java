import java.io.*;
import java.util.*;

public class StudentCoursesDriver {

    public static void main(String[] args) throws Exception {

        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Enrollment> enrollments = new ArrayList<>();

        // ---------- read Students ----------
        BufferedReader br1 = new BufferedReader(new FileReader("Students.txt"));
        String line;

        while ((line = br1.readLine()) != null) {
            String[] p = line.split(",");
            int id = Integer.parseInt(p[0]);
            students.add(new Student(id, p[1], p[2]));
        }
        br1.close();

        // ---------- read Enrolled ----------
        BufferedReader br2 = new BufferedReader(new FileReader("Enrolled.txt"));

        while ((line = br2.readLine()) != null) {
            String[] p = line.split(",");
            int id = Integer.parseInt(p[0]);
            enrollments.add(new Enrollment(id, p[1]));
        }
        br2.close();

        // ---------- manual JOIN ----------
        for (Student s : students) {

            System.out.print(s.name + ", " + s.major + " -> ");

            boolean found = false;

            for (Enrollment e : enrollments) {
                if (e.sId == s.sId) {
                    System.out.print(e.cId + " ");
                    found = true;
                }
            }

            if (!found) {
                System.out.print("no courses");
            }

            System.out.println();
        }
    }
}
