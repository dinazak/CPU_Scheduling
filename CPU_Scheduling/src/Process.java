
public class Process {

    private String processName;
    private int arrivalTime;
    private int burstTime;
    private int waitingTime;
    private int turnAroundTime;
    private int priority;
    private int executedAt;
    private String color;
    static Double V1;
    static Double V2;
    int quantum;
    int AGAT_factor;

    Process() {
        processName = "";
        burstTime = 0;
        arrivalTime = 0;
        priority = 0;
        quantum = 0;
    }

    public Process(String processName, int arrivalTime, int burstTime, int priorityTime, int quantum, String color) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priorityTime;
        this.quantum = quantum;
        this.color = color;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(int executedAt) {
        this.executedAt = executedAt;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void calcAGAT_factor() {
        this.AGAT_factor =
                (int) ((10 - this.priority) + Math.ceil(this.arrivalTime / V1) + Math.ceil(this.burstTime / V2));
    }

    public int getAGAT() {
        return AGAT_factor;
    }
}