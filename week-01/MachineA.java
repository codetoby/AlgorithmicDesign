import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.io.File;

public class MachineA {

    public static void main(String[] args) throws IOException {
        // File infile = new File("test1.txt");
        // Read in Filename
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        scanner.close();

        File infile = new File(filename);

        // Read in File
        Scanner input = new Scanner(infile);
        int n = input.nextInt();

        List<Task> tasks = new ArrayList<>();

        // Read in Tasks
        for (int i = 0; i < n; i++) {
            int start = input.nextInt();
            int end = input.nextInt();
            Task task = new Task(start, end);
            tasks.add(task);
        }
        input.close();

        Comparator<Task> taskStartTimeComparator = new TaskStartTimeComparator();
        Comparator<Task> taskDurationComparator = new TaskDurationComparator();
        Comparator<Task> taskComparator = taskStartTimeComparator.thenComparing(taskDurationComparator);

        // Sort Tasks by Start Time and Duration
        Collections.sort(tasks, taskComparator);


        int currentEndTime = 0;
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            // Check if the Timeslot is Availible for the Next Task
            if (task.getStart() >= currentEndTime) {
                currentEndTime = task.getEnd();
                result.add(task);
            }
        }

        // Output Schedueled Tasks
        for (Task task : result) {
            System.out.println(task);
        }
    }

}

class TaskStartTimeComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        return o1.getStart() - o2.getStart();
    }
}

class TaskDurationComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        return o1.getDuration() - o2.getDuration();
    }
}


// Represent a Task
class Task {

    private int start;
    private int end;

    public Task(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getDuration() {
        return end - start;
    }
    
    @Override
    public String toString() {
        return start + " " + end;
    }

}

