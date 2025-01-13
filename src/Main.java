import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<Process> processList = new ArrayList<>();

    public static void main(String[] args) {
        do {
            
            if (processList.isEmpty()) {
                inputProcesses();
            } else {
                System.out.print("Press S to continue with the same data or Press any key to Start: ");
                String continueChoice = scanner.next();
                if (!continueChoice.equalsIgnoreCase("S")) {
                    inputProcesses();
                }
            }

            while (true) {
                System.out.println("\n" + "=".repeat(32));
                System.out.println("CPU Scheduling Algorithms");
                System.out.println("=".repeat(32));
                System.out.println("1. FCFS\n2. SJF\n3. Priority\n4. Round Robin");
                System.out.print("Select one of the CPU scheduling algorithms by entering its choice number: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> SchedulingAlgorithms.fcfs(processList);
                    case 2 -> SchedulingAlgorithms.sjf(processList);
                    case 3 -> SchedulingAlgorithms.priorityScheduling(processList);
                    case 4 -> SchedulingAlgorithms.roundRobin(processList);
                    default -> {
                        System.out.println("Invalid choice. Try again.");
                        continue;
                    }
                }
                break;
            }

            System.out.print("Press Y to continue or any key to EXIT from the simulation: ");
        } while (scanner.next().equalsIgnoreCase("Y"));
    }

    public static void inputProcesses() {
        System.out.print("Input the number of processes <max 15>: ");
        int n = scanner.nextInt();

        System.out.print("Enter R to generate random Burst time and Priority or Press any key: ");
        String randomChoice = scanner.next();
        processList.clear();

        Random random = new Random();
        for (int i = 1; i <= n; i++) {
            String name = "P" + i;
            int creationTime = i - 1;
            int burstTime = randomChoice.equalsIgnoreCase("R") ? random.nextInt(10) + 1 : getIntInput("Enter Burst Time for " + name + ": ");
            int priority = randomChoice.equalsIgnoreCase("R") ? random.nextInt(10) + 1 : getIntInput("Enter Priority for " + name + ": ");

            processList.add(new Process(name, creationTime, burstTime, priority));
        }

        SchedulingAlgorithms.displayProcesses(processList);
    }

    public static int getIntInput(String message) {
        System.out.print(message);
        return scanner.nextInt();
    }
}
