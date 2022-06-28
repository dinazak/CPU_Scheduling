import java.util.ArrayList;
import java.util.Objects;

public class Priority {

    public ArrayList<Process> pro=new ArrayList<Process>();
    public ArrayList<Process> Gantt_chart = new ArrayList<>();
    public  int context;
    public Priority (ArrayList<Process>p,int context){
        pro=p;
        this.context=context;
    }
    boolean includes(ArrayList<Process> p, String s)
    {
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

    public void getHighProcess(int num)
    {
        int[] waitingTime = new int[(num + 1)];
        int[] turnAroundTime = new int[(num + 1)];
        int[] finishTime = new int[num + 1];
        ArrayList<Process> arrived1=new ArrayList<Process>(num);

        int time=0;
        int avgPri=0;

        int max = 0;
        for (int i = 0; i < num; i++) {
            if (pro.get(i).getArrivalTime() >= max) {
                max = pro.get(i).getArrivalTime();
            }
        }
        for (int j = 0; j < max + 1; j++) {
            for (int i = 0; i < num; i++) {
                if (pro.get(i).getArrivalTime() <= time) {
                    if (!(includes(arrived1, pro.get(i).getProcessName()))) {
                        arrived1.add(pro.get(i));
                    }
                }
            }
            time += 1;

            if (isEmpty(arrived1)) {
                time++;
            }
        }
        for (int i=0;i<num;i++){
            avgPri+=arrived1.get(i).getPriority();
        }
        avgPri=avgPri/num;
        int finish=arrived1.get(0).getBurstTime();
        Process tem=new Process();
        int [] counter=new int[num+1];
        for (int i=0;i<num;i++){
            counter[i]=0;
        }
        for(int j=0;j<num;j++) {
            for (int i = 0; i < num; i++) {
                if (arrived1.get(j).getArrivalTime() <= finish && arrived1.get(i ).getArrivalTime() <= finish) {
                    if(i!=0) {
                        int temp=0;
                        for(int k=0;k<i;k++){
                            temp+=arrived1.get(k).getBurstTime();
                        }
                        if (arrived1.get(j).getPriority()< arrived1.get(i).getPriority() && arrived1.get(j).getArrivalTime() <= temp &&counter[i]<(arrived1.get(i).getPriority()/2)) {
                            if (arrived1.get(i).getBurstTime() > avgPri) {
                                counter[i]++;
                            }
                            tem = arrived1.get(j);
                            arrived1.set(j, arrived1.get(i));
                            arrived1.set(i, tem);
                        }
                        else if(arrived1.get(j).getPriority() == arrived1.get(i ).getPriority()){
                            break;
                        }
                    }
                    else {
                        if (arrived1.get(j).getPriority() < arrived1.get(i).getPriority() && arrived1.get(j).getArrivalTime() <= arrived1.get(i ).getArrivalTime()) {
                            if (arrived1.get(j).getBurstTime() > avgPri) {
                                counter[j]++;
                            }
                            tem = arrived1.get(j);
                            arrived1.set(j, arrived1.get(i));
                            arrived1.set(i, tem);
                        }
                        else if(arrived1.get(j).getPriority() == arrived1.get(i ).getPriority()){
                            break;
                        }
                    }



                }
            }

            finish += arrived1.get(j).getBurstTime();

        }
        for (int i = 0; i < num; i++) {
            if (i == 0) {
                finishTime[i] = arrived1.get(i).getBurstTime()+context;

            } else {
                finishTime[i] = finishTime[i - 1] + arrived1.get(i).getBurstTime()+context;
            }
        }
        for (int i = 0; i < num; i++) {

            if (i == 0) {

                for (int j = 1; j <= finishTime[0]; j++) {

                    Process p = new Process(arrived1.get(0).getProcessName(), arrived1.get(0).getArrivalTime(), arrived1.get(0).getBurstTime(), arrived1.get(0).getPriority(), 0, arrived1.get(0).getColor());
                    p.setExecutedAt(j);

                    Gantt_chart.add(p);
                }

            } else {

                for (int j = finishTime[i - 1] + 1; j <= finishTime[i]; j++) {

                    Process p = new Process(arrived1.get(i).getProcessName(), arrived1.get(i).getArrivalTime(), arrived1.get(i).getBurstTime(), arrived1.get(i).getPriority(), 0, arrived1.get(i).getColor());

                    p.setExecutedAt(j);
                    Gantt_chart.add(p);

                }
            }
        }
        System.out.println("name " +"waiting time "+"turnaround time " );
        for (int i = 0; i < num; i++) {
            if (i == 0) {
                waitingTime[i] = context;
                turnAroundTime[i] = waitingTime[i] + arrived1.get(i).getBurstTime();

            } else {

                waitingTime[i] = (finishTime[i - 1]) - (arrived1.get(i).getArrivalTime());
                turnAroundTime[i] = waitingTime[i] + arrived1.get(i).getBurstTime()+context;

            }
            System.out.println(arrived1.get(i).getProcessName()+"\t\t"+ waitingTime[i]+"\t\t"+turnAroundTime[i]);
        }

        double waitAvg = 0;
        int turnAvg = 0;
        for (int i = 0; i < num; i++) {
            turnAvg += (waitingTime[i] + arrived1.get(i).getBurstTime());
            waitAvg += waitingTime[i];
        }

        waitAvg = waitAvg / num;
        turnAvg = turnAvg / num;
        System.out.println("Avg waiting time: " + waitAvg);
        System.out.println("Avg turnaround time: " + turnAvg);
    }
}

