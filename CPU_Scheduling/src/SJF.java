import java.util.ArrayList;
import java.util.Objects;

public class SJF {
    public ArrayList<Process> pro = new ArrayList<Process>();
    public ArrayList<Process> GanttChart = new ArrayList<Process>();

    public SJF(ArrayList<Process> p) {
        pro = p;
    }

    boolean includes(ArrayList<Process> p, String s) {
        boolean ans = false;
        for (Process process : p) {
            if (Objects.equals(process.getProcessName(), s)) {
                ans = true;
                break;
            }
        }
        return ans;
    }

    public boolean isEmpty(ArrayList<Process> p) {
        boolean res = false;
        for (Process process : p) {
            if (process == null) {
                res = true;
            } else {
                res = false;
            }
        }
        return res;
    }

    public void getShortest(int num) {
        int[] waitingTime = new int[(num + 1)];
        int[] turnAroundTime = new int[(num + 1)];
        int[] finishTime = new int[num + 1];

        ArrayList<Process> arrived = new ArrayList<Process>(num);
        int avgBurst = 0;
        int time = 0;
        boolean result = false;
        int max = 0;
        for (int i = 0; i < num; i++) {
            if (pro.get(i).getArrivalTime() >= max) {
                max = pro.get(i).getArrivalTime();
            }
        }
        for (int j = 0; j < max + 1; j++) {
            for (int i = 0; i < num; i++) {
                if (pro.get(i).getArrivalTime() <= time) {
                    if (!(includes(arrived, pro.get(i).getProcessName()))) {
                        arrived.add(pro.get(i));
                    }
                }
            }
            time += 1;

            if (isEmpty(arrived)) {
                time++;
            }
        }

        for (int i = 0; i < num; i++) {
            avgBurst += arrived.get(i).getBurstTime();
        }
        avgBurst = avgBurst / num;
        int finish = arrived.get(0).getBurstTime();
        Process tem;
        int[] counter = new int[num + 1];
        for (int i = 0; i < num; i++) {
            counter[i] = 0;
        }

        for (int j = 0; j < num; j++) {
            for (int i = 0; i < num; i++) {
                if (arrived.get(j).getArrivalTime() <= finish && arrived.get(i).getArrivalTime() <= finish) {
                    if (i != 0) {
                        int temp = 0;
                        for (int k = 0; k < i; k++) {
                            temp += arrived.get(k).getBurstTime();
                        }
                        if (arrived.get(j).getBurstTime() < arrived.get(i).getBurstTime() && arrived.get(j).getArrivalTime() <= temp && counter[i] < (arrived.get(i).getBurstTime() / 2)) {
                            if (arrived.get(i).getBurstTime() > avgBurst) {
                                counter[i]++;
                            }
                            tem = arrived.get(j);
                            arrived.set(j, arrived.get(i));
                            arrived.set(i, tem);
                        } else if (arrived.get(j).getBurstTime() == arrived.get(i).getBurstTime()) {
                            break;
                        }
                    } else {
                        if (arrived.get(j).getBurstTime() < arrived.get(i).getBurstTime() && arrived.get(j).getArrivalTime() <= arrived.get(i).getArrivalTime()) {
                            if (arrived.get(j).getBurstTime() > avgBurst) {
                                counter[j]++;
                            }
                            tem = arrived.get(j);
                            arrived.set(j, arrived.get(i));
                            arrived.set(i, tem);
                        } else if (arrived.get(j).getBurstTime() == arrived.get(i).getBurstTime()) {
                            break;
                        }
                    }
                }
            }
            finish += arrived.get(j).getBurstTime();
        }
        for (int i = 0; i < num; i++) {
            if (i == 0) {
                finishTime[i] = arrived.get(i).getBurstTime();

            } else {
                finishTime[i] = finishTime[i - 1] + arrived.get(i).getBurstTime();
            }
        }
        for (int i = 0; i < num; i++) {
            if (i == 0) {
                for (int j = 1; j <= finishTime[0]; j++) {
                    Process p = new Process(arrived.get(0).getProcessName(), arrived.get(0).getArrivalTime(), arrived.get(0).getBurstTime(), arrived.get(0).getPriority(), 0, arrived.get(0).getColor());
                    p.setExecutedAt(j);
                    GanttChart.add(p);
                }
            } else {

                for (int j = finishTime[i - 1] + 1; j <= finishTime[i]; j++) {

                    Process p = new Process(arrived.get(i).getProcessName(), arrived.get(i).getArrivalTime(), arrived.get(i).getBurstTime(), arrived.get(i).getPriority(), 0, arrived.get(i).getColor());

                    p.setExecutedAt(j);
                    GanttChart.add(p);

                }
            }
        }
        System.out.println("name " + "waiting time " + "turnaround time ");
        for (int i = 0; i < num; i++) {
            if (i == 0) {
                waitingTime[i] = 0;
                turnAroundTime[i] = waitingTime[i] + arrived.get(i).getBurstTime();

            } else {
                waitingTime[i] = (finishTime[i - 1]) - (arrived.get(i).getArrivalTime());
                turnAroundTime[i] = finishTime[i] - arrived.get(i).getArrivalTime();

            }
            System.out.println(arrived.get(i).getProcessName() + "\t\t" + waitingTime[i] + "\t\t" + turnAroundTime[i]);
        }

        double waitAvg = 0;
        int turnAvg = 0;
        for (int i = 0; i < num; i++) {
            turnAvg += (waitingTime[i] + arrived.get(i).getBurstTime());
            waitAvg += waitingTime[i];
        }

        waitAvg = waitAvg / num;
        turnAvg = turnAvg / num;
        System.out.println("Avg waiting time: " + waitAvg);
        System.out.println("Avg turnaround time: " + turnAvg);
    }

}