import java.util.ArrayList;
import java.util.Objects;

public class Shortest_Remaining_Time {
    public ArrayList<Process> processesArray = new ArrayList<>();
    private final int contextSwitch;
    ArrayList<Process> orderedProcesses = new ArrayList<Process>();
    public ArrayList<Process> Gantt_chart = new ArrayList<>();


    public Shortest_Remaining_Time(ArrayList<Process> processes, int contextSwitch) {
        processesArray = processes;
        this.contextSwitch = contextSwitch;
    }

    public void getShortestRemainingTime() {
        int[] remainingTime = new int[processesArray.size()];

        int check = 0, counter = 0;

        for (int i = 0; i < processesArray.size(); i++) {
            remainingTime[i] = processesArray.get(i).getBurstTime();
        }

        int minimum, index = 0;
        boolean flag = true;
        int factor=3;
        while (check != processesArray.size()) {
            int c = 0;
            minimum = 1111111111;
            // hashof 2a2al process with arrival num
            for (int i = 0; i < processesArray.size(); i++) { //[8,4,9,5]
                if ((processesArray.get(i).getArrivalTime() <= counter) && (remainingTime[i] < minimum) && remainingTime[i] > 0) {
                    if ((factor + (remainingTime[i] * 2)) < counter) {
                        index = i;
                        break;
                    } else {
                        minimum = remainingTime[i];
                        index = i;

                    }
                }
            }
            remainingTime[index]--;
            // lw al process 5laset
            if (remainingTime[index] == 0) {
                orderedProcesses.add(processesArray.get(index));
                check++;
            }
            counter++;
            if (flag) {
                //Used for Make Gantt chart

                Process P = new Process(processesArray.get(index).getProcessName()
                        , processesArray.get(index).getArrivalTime()
                        , processesArray.get(index).getBurstTime(), 0, 0, processesArray.get(index).getColor());
                P.setExecutedAt(counter + contextSwitch);
                Gantt_chart.add(P);
                counter += contextSwitch;
                flag = false;
                c = 1;
            }
            if (!Objects.equals(processesArray.get(index).getProcessName(), Gantt_chart.get(Gantt_chart.size() - 1).getProcessName())) {
                counter += contextSwitch;
            }
            if (c == 0) {
                //Used for Make Gantt chart
                Process P = new Process(processesArray.get(index).getProcessName()
                        , processesArray.get(index).getArrivalTime()
                        , processesArray.get(index).getBurstTime()
                        , 0, 0, processesArray.get(index).getColor());
                P.setExecutedAt(counter);
                Gantt_chart.add(P);
            }
        }
        for (int i = 0; i < processesArray.size(); i++) {
            int completeTime = 0;
            for (Process value : Gantt_chart) {
                if (Objects.equals(processesArray.get(i).getProcessName(), value.getProcessName())) {
                    completeTime = value.getExecutedAt();
                }
            }
            //waiting time
            processesArray.get(i).setWaitingTime(completeTime - processesArray.get(i).getBurstTime()
                    - processesArray.get(i).getArrivalTime());

            if (processesArray.get(i).getWaitingTime() < 0)
                processesArray.get(i).setWaitingTime(0);

            //TurnAround Time
            for (Process process : processesArray)
                process.setTurnAroundTime(process.getBurstTime() + process.getWaitingTime());
        }
        display();
    }

    void display() {
        System.out.println("Process Name " + " Waiting Time " + " Turnaround Time");
        int total_waiting_time = 0, total_turnaround_time = 0;
        for (Process process : orderedProcesses) {
            total_waiting_time = total_waiting_time + process.getWaitingTime();
            total_turnaround_time = total_turnaround_time + process.getTurnAroundTime();
            System.out.println(" " + process.getProcessName() + "\t\t " + process.getWaitingTime() + "\t\t"
                + process.getTurnAroundTime());
    }
        System.out.println("Average Waiting Time = " + (float) total_waiting_time / (float) orderedProcesses.size());
        System.out.println("Average Turnaround Time = " + (float) total_turnaround_time / (float) orderedProcesses.size());
        System.out.println();
    }
}
