import java.io.File;

public class ExamDesk1 {
    public static void main(String[] args) {
        try {
            // Get the directory where ExamDesk1 is located
            String currentDir = new File(ExamDesk1.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();

            // Set the classpath to the directory where ExamDesk1 is located
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", currentDir, "ExamTimetable");

            // Inherit the IO so the output/error is shown in the console
            processBuilder.inheritIO();

            // Start the process
            Process process = processBuilder.start();

            // Wait for the process to finish
            int exitCode = process.waitFor();

            System.out.println("ExamTimetable executed with exit code: " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}