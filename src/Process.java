public class Process {
    String name;
    int creationTime;
    int burstTime;
    int priority;

    public Process(String name, int creationTime, int burstTime, int priority) {
        this.name = name;
        this.creationTime = creationTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}
