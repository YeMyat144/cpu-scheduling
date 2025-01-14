import java.util.*;

public class SchedulingAlgorithms {

    public static void fcfs(List<Process> processList) {
        System.out.println("\n" + "=".repeat(32));
        System.out.println("FCFS CPU scheduling algorithm");
        System.out.println("=".repeat(32));

        processList.sort(Comparator.comparingInt(p -> p.creationTime));
        displayProcesses(processList);
        calculateAndDisplayTimes(processList, "FCFS");
    }

    public static void sjf(List<Process> processList) {
        System.out.println("\n" + "=".repeat(32));
        System.out.println("SJF CPU scheduling algorithm");
        System.out.println("=".repeat(32));

        processList.sort(Comparator.comparingInt(p -> p.burstTime));
        displayProcesses(processList);
        calculateAndDisplayTimes(processList, "SJF");
    }

    public static void priorityScheduling(List<Process> processList) {
        System.out.println("\n" + "=".repeat(32));
        System.out.println("Priority CPU scheduling algorithm");
        System.out.println("=".repeat(32));

        processList.sort(Comparator.comparingInt(p -> p.priority));
        displayProcesses(processList);
        calculateAndDisplayTimes(processList, "Priority");
    }

    public static void roundRobin(List<Process> processList) {
        System.out.println("\n" + "=".repeat(32));
        System.out.println("Round Robin CPU scheduling algorithm");
        System.out.println("=".repeat(32));
    
        try (Scanner scanner = new Scanner(System.in)) { 
            System.out.print("Enter the Time Quantum: ");
            int timeQuantum = scanner.nextInt();
    
            List<Process> queue = new ArrayList<>(processList);
            int currentTime = 0;
            StringBuilder ganttChart = new StringBuilder("Gantt chart <with starting time is zero>: \n|");
            Map<String, Integer> waitingTimes = new HashMap<>();
    
            while (!queue.isEmpty()) {
                Process current = queue.remove(0);
                int timeSpent = Math.min(current.burstTime, timeQuantum);
    
                ganttChart.append(" ").append(current.name).append("(").append(currentTime).append("-").append(currentTime + timeSpent).append(") |");
    
                current.burstTime -= timeSpent;
                currentTime += timeSpent;
    
                if (current.burstTime > 0) {
                    queue.add(current);
                } else {
                    waitingTimes.put(current.name, currentTime - current.creationTime - (current.burstTime + timeSpent));
                }
            }
    
            System.out.println(ganttChart);
            displayWaitingTimes(waitingTimes);
        } catch (InputMismatchException e) {
            System.err.println("Invalid input. Please enter an integer for the Time Quantum.");
        }
    }
    
    public static void calculateAndDisplayTimes(List<Process> processList, String algorithm) {
        StringBuilder ganttChart = new StringBuilder("Gantt chart <with starting time is zero>: \n|");
        int currentTime = 0;
        int totalWaitingTime = 0;

        for (Process p : processList) {
            ganttChart.append(" ").append(p.name).append("(").append(currentTime).append("-").append(currentTime + p.burstTime).append(") |");
            totalWaitingTime += currentTime - p.creationTime;
            currentTime += p.burstTime;
        }

        System.out.println(ganttChart);
        System.out.printf("The Average Process Waiting Time = %.2f ms\n", (double) totalWaitingTime / processList.size());
    }

    public static void displayProcesses(List<Process> processes) {
        System.out.printf("\n%-10s%-15s%-15s%-10s\n", "Process", "Creation_time", "Burst_time", "Priority");
        for (Process p : processes) {
            System.out.printf("%-10s%-15d%-15d%-10d\n", p.name, p.creationTime, p.burstTime, p.priority);
        }
    }

    public static void displayWaitingTimes(Map<String, Integer> waitingTimes) {
        int totalWaitingTime = 0;

        for (Map.Entry<String, Integer> entry : waitingTimes.entrySet()) {
            System.out.printf("Waiting time of process, %s = %d\n", entry.getKey(), entry.getValue());
            totalWaitingTime += entry.getValue();
        }

        System.out.printf("The Average Process Waiting Time = %.2f ms\n", (double) totalWaitingTime / waitingTimes.size());
    }
}
