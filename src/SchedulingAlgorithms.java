import java.util.*;

public class SchedulingAlgorithms {

    public static void fcfs(List<Process> processList) {
        System.out.println("\n=================");
        System.out.println("FCFS CPU Scheduling Algorithm");
        System.out.println("=================");

        processList.sort(Comparator.comparingInt(p -> p.creationTime));
        displayProcesses(processList);
        calculateAndDisplayTimes(processList);
    }

    public static void roundRobin(List<Process> processList) {
        System.out.println("\n=================");
        System.out.println("Round Robin CPU Scheduling Algorithm");
        System.out.println("=================");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Time Quantum: ");
        int timeQuantum = scanner.nextInt();

        List<Process> queue = new ArrayList<>(processList);
        int currentTime = 0;
        StringBuilder ganttChart = new StringBuilder("Gantt Chart <with starting time is zero>: \n|");
        Map<String, Integer> waitingTimes = new HashMap<>();
        Map<String, Integer> remainingBurstTimes = new HashMap<>();

        for (Process p : processList) {
            remainingBurstTimes.put(p.name, p.burstTime);
            waitingTimes.put(p.name, 0);
        }

        while (!queue.isEmpty()) {
            Process current = queue.remove(0);
            int timeSpent = Math.min(remainingBurstTimes.get(current.name), timeQuantum);

            ganttChart.append(" ").append(current.name).append("(").append(currentTime).append("-").append(currentTime + timeSpent).append(") |");

            currentTime += timeSpent;
            remainingBurstTimes.put(current.name, remainingBurstTimes.get(current.name) - timeSpent);

            for (Process p : queue) {
                waitingTimes.put(p.name, waitingTimes.get(p.name) + timeSpent);
            }

            if (remainingBurstTimes.get(current.name) > 0) {
                queue.add(current);
            } else {
                waitingTimes.put(current.name, currentTime - current.creationTime - current.burstTime);
            }
        }

        System.out.println(ganttChart);
        displayWaitingTimes(waitingTimes);
    }

    public static void calculateAndDisplayTimes(List<Process> processList) {
        StringBuilder ganttChart = new StringBuilder("Gantt Chart <with starting time is zero>: \n|");
        int currentTime = 0;
        int totalWaitingTime = 0;
        Map<String, Integer> waitingTimes = new HashMap<>();

        for (Process p : processList) {
            ganttChart.append(" ").append(p.name).append("(").append(currentTime).append("-").append(currentTime + p.burstTime).append(") |");
            waitingTimes.put(p.name, currentTime - p.creationTime);
            totalWaitingTime += waitingTimes.get(p.name);
            currentTime += p.burstTime;
        }

        System.out.println(ganttChart);
        displayWaitingTimes(waitingTimes);
    }

    public static void displayProcesses(List<Process> processes) {
        System.out.printf("\n%-10s%15s%15s%10s\n", "Process", "Creation_time", "Burst_time", "Priority");
        for (Process p : processes) {
            System.out.printf("%-10s%15d%15d%10d\n", p.name, p.creationTime, p.burstTime, p.priority);
        }
    }
    
    public static void displayWaitingTimes(Map<String, Integer> waitingTimes) {
        int totalWaitingTime = 0;

        for (Map.Entry<String, Integer> entry : waitingTimes.entrySet()) {
            System.out.printf("Waiting time of process %s = %d ms\n", entry.getKey(), entry.getValue());
            totalWaitingTime += entry.getValue();
        }

        System.out.printf("The Average Process Waiting Time = %.2f ms\n", (double) totalWaitingTime / waitingTimes.size());
    }
}