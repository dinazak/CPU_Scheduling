import java.util.ArrayList;

public class AGAT_Scheduling {
    public  ArrayList<Process> list1 = new ArrayList<>();
    public  ArrayList<Process> Gantt_chart = new ArrayList<>();

    // function to get the maximum Arrival time for all the processes to calculate V1
    static int getMaxArrivalTime(Process[] p) {
        int temp = p[0].getArrivalTime();
        for (Process process : p) {

            if (process.getArrivalTime() > temp) {
                temp = process.getArrivalTime();
            }
        }
        return temp;
    }

    // function to get the maximum burst time for all the processes to calculate V2
    static int getMaxBurstTime(Process[] p) {
        int temp = p[0].getBurstTime();
        for (Process process : p) {

            if (process.getBurstTime() > temp) {
                temp = process.getBurstTime();
            }
        }
        return temp;
    }

    static boolean hasFinished(Process[] p) {
        for (Process process : p) {
            if (process.getQuantum() > 0)
                return false;
        }
        return true;
    }

    static int bestProcessInRQ(Process[] p, ArrayList<Integer> readyQueue) {

        int minAGAT = Integer.MAX_VALUE;
        int index = -1;
        for (int queueIndex : readyQueue) {
            if (p[queueIndex].getAGAT() < minAGAT) {
                minAGAT = p[queueIndex].getAGAT();
                index = queueIndex;
            }
        }
        return index;
    }


    static int getNextProcess(Process[] p, ArrayList<Integer> readyQueue, int currentTime) {
        if (readyQueue.size() > 0) {
            int next = readyQueue.get(0);
            readyQueue.remove(0);
            return next;
        }
        return nextArriver(p, currentTime);
    }

    static void addProcessesInRangeInReadyQueue(Process[] p,
                                                ArrayList<Integer> readyQueue, int startTime, int endTime) {
        for (int i = 0; i < p.length; ++i) {
            if (p[i].getArrivalTime() >= startTime && p[i].getArrivalTime() <= endTime) {
                // found a process in range.
                readyQueue.add(i);
            }
        }
    }

    static int getNewlyArrivedInterpreter(Process[] p,
                                          ArrayList<Integer> readyQueue, int startTime, int endTime, int index) {
        for (int i = 0; i < p.length; ++i) {
            if (p[i].getArrivalTime() >= startTime && p[i].getArrivalTime() <= endTime) {
                // found a process in range.
                if (p[i].getAGAT() < p[index].getAGAT()) {
                    // The process will interrupt
                    return i;
                }
                // The process is worse, so add it to the end of the ReadyQueue.
                readyQueue.add(i);

            }
        }
        // No interrupter
        return -1;
    }

    void AGAT(Process[] p) {
        int index = nextArriver(p, 0);
        int currentTime = p[index].getArrivalTime();
        ArrayList<Integer> readyQueue = new ArrayList<>();
        int[] waitingTime = new int[p.length];
        int[] turnaroundTime = new int[p.length];
        int[] burst_Time = new int[p.length];
        int[] arrival_Time = new int[p.length];

        for (int i = 0; i < p.length; ++i) {
            burst_Time[i] = p[i].getBurstTime();
            arrival_Time[i] = p[i].getArrivalTime();
        }
        for (int i = 0; i < p.length; ++i) {
            waitingTime[i] = -1;
            turnaroundTime[i] = -1;
        }
        while (!hasFinished(p)) {
            //Printing quantum and AGAT history each process
            System.out.println("Quantum of each process :");
            for (Process process : p) {
                System.out.print(process.getQuantum());
                System.out.print(" ");
            }
            System.out.println(" ");
            System.out.println("AGAT factor of each process :");
            for (Process process : p) {
                System.out.print(process.getAGAT());
                System.out.print(" ");
            }
            System.out.println(" ");
            /////////////////
            Process P = new Process(p[index].getProcessName()
                    , p[index].getArrivalTime()
                    , p[index].getBurstTime(), 0, p[index].getQuantum(), p[index].getColor());
            P.setExecutedAt(currentTime);
            list1.add(P);

            waitingTime[index] = currentTime;
            double maxBurstTime = getMaxBurstTime(p);
            //Rule of calculating V2
            if (maxBurstTime > 10) {
                Process.V2 = (maxBurstTime / 10);
            } else {
                Process.V2 = 1.0;

            }
            for (Process process : p) {
                process.calcAGAT_factor();
            }
            System.out.println(p[index].getProcessName());
            System.out.println(currentTime);

            int quantum_40 = (int) Math.round(p[index].getQuantum() * 0.4);
            if (quantum_40 > p[index].getBurstTime()) {
                quantum_40 = p[index].getBurstTime();
            }
            p[index].setBurstTime(p[index].getBurstTime() - quantum_40);
            addProcessesInRangeInReadyQueue(p, readyQueue, currentTime + 1, currentTime + quantum_40);
            currentTime += quantum_40;
            if (p[index].getBurstTime() == 0) {
                p[index].setQuantum(0);
                index = getNextProcess(p, readyQueue, currentTime);
                if (index != -1 && currentTime < p[index].getArrivalTime())
                    currentTime = p[index].getArrivalTime();
                continue;
            }

            int processWithBestAGAT = bestProcessInRQ(p, readyQueue);
            if (processWithBestAGAT != -1 && p[processWithBestAGAT].getAGAT() < p[index].getAGAT()) {
                // Process interrupted
                int remainingQuantum = p[index].getQuantum() - quantum_40;
                int newQuantum = p[index].getQuantum() + remainingQuantum;
                p[index].setQuantum(newQuantum);
                readyQueue.remove(Integer.valueOf(processWithBestAGAT));
                readyQueue.add(index);
                index = processWithBestAGAT;
                continue;
            }
            // Process continues (possibly will be interrupted if another process with better AGAT arrives).
            int remainingQuantum = p[index].getQuantum() - quantum_40;
            if (remainingQuantum > p[index].getBurstTime()) {
                remainingQuantum = p[index].getBurstTime();
            }
            int interrupter = getNewlyArrivedInterpreter(p, readyQueue, currentTime + 1, currentTime + remainingQuantum, index);
            if (interrupter != -1) {
                // We found an interrupter
                int extraProcessTime = p[interrupter].getArrivalTime() - currentTime;
                p[index].setBurstTime(p[index].getBurstTime() - extraProcessTime);
                currentTime += extraProcessTime;

                remainingQuantum = p[index].getQuantum() - quantum_40 - extraProcessTime;
                int newQuantum = p[index].getQuantum() + remainingQuantum;
                p[index].setQuantum(newQuantum);

                readyQueue.add(index);
                index = interrupter;
                continue;
            }
            //No interruption
            int newQuantum = p[index].getQuantum() + 2;
            p[index].setQuantum(newQuantum);

            p[index].setBurstTime(p[index].getBurstTime() - remainingQuantum);
            if (p[index].getBurstTime() == 0) {
                p[index].setQuantum(0);
            } else {
                readyQueue.add(index);
            }
            currentTime += remainingQuantum;
            index = getNextProcess(p, readyQueue, currentTime);
            if (index != -1 && currentTime < p[index].getArrivalTime())
                currentTime = p[index].getArrivalTime();
        }
        int maxi = -1;
        int ind = 0;
        for (int i = 0; i < waitingTime.length; i++) {
            if (waitingTime[i] > maxi) {
                maxi = waitingTime[i];
                ind = i;
            }
        }
        waitingTime[ind] = currentTime;
        System.out.println(currentTime);
        Process P = new Process(p[ind].getProcessName()
                , p[ind].getArrivalTime()
                , p[ind].getBurstTime(), 0, p[ind].getQuantum(), p[ind].getColor());
        P.setExecutedAt(currentTime);
        list1.add(P);

        System.out.println("Waiting Time " + " Turnaround Time");
        double sumWaiting = 0;
        double sumTurnaround = 0;

        for (int j = 0; j < p.length; j++) {
            waitingTime[j] = waitingTime[j] - burst_Time[j] - arrival_Time[j];
            if (waitingTime[j] < 0) waitingTime[j] = 0;
            turnaroundTime[j] = waitingTime[j] + burst_Time[j];
            System.out.println(waitingTime[j]+ "\t\t" + turnaroundTime[j]);
            sumTurnaround += turnaroundTime[j];
            sumWaiting += waitingTime[j];
        }
        System.out.println("Average Waiting Time: "+(sumWaiting / waitingTime.length));
        System.out.print("Average Turnaround Time: "+sumTurnaround / turnaroundTime.length);
        System.out.println();

        //Used for Make Gantt chart
        for (int i = 0; i < list1.size() - 1; i++) {
            if (list1.get(i).getExecutedAt() == 0) {
                for (int j = 1; j <= list1.get(i + 1).getExecutedAt(); j++) {
                    Process p1 = new Process(list1.get(i).getProcessName(), list1.get(i).getArrivalTime(), list1.get(i).getBurstTime(), list1.get(i).getPriority(), list1.get(i).getQuantum(), list1.get(i).getColor());
                    p1.setExecutedAt(j);
                    Gantt_chart.add(p1);
                }
            } else {
                for (int j = list1.get(i).getExecutedAt() + 1; j <= list1.get(i + 1).getExecutedAt(); j++) {
                    Process p1 = new Process(list1.get(i).getProcessName(), list1.get(i).getArrivalTime(), list1.get(i).getBurstTime(), list1.get(i).getPriority(), list1.get(i).getQuantum(), list1.get(i).getColor());
                    p1.setExecutedAt(j);
                    Gantt_chart.add(p1);

                }
            }
        }
    }

    //Get the earliest process that hasn't arrived yet at current time
    static int nextArriver(Process[] p, int currentTime) {
        int minArrivalTime = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < p.length; ++i) {
            if (p[i].getArrivalTime() >= currentTime && p[i].getArrivalTime() < minArrivalTime) {
                minArrivalTime = p[i].getArrivalTime();
                index = i;
            }
        }
        return index;
    }
}
