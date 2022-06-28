import org.jfree.data.xy.XYDataset;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static Color getColorByName(String name) {
        try {
            return (Color)Color.class.getField(name.toUpperCase()).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Please Enter Your Choice");
            System.out.println("1- Priority Scheduling with context switching");
            System.out.println("2- Shortest- Job First (SJF)");
            System.out.println("3- Shortest-Remaining Time First Scheduling with context switching");
            System.out.println("4- AGAT Scheduling");
            System.out.println("5- EXIT");

            int choice = sc.nextInt();
            if (choice == 5) {
                break;
            }
            System.out.println("Enter Number of process:");
            int numOfProcesses = sc.nextInt();
            if (numOfProcesses != 0) {
                ArrayList<Process> arrayOfProcesses = new ArrayList<>();
                GUI gui = null;
                ArrayList<Color> colors = new ArrayList<>();
                ArrayList<String> names = new ArrayList<>();
                ArrayList<Integer> priority = new ArrayList<>();
                if (choice == 1) {
                    for (int i = 0; i < numOfProcesses; i++) {
                        System.out.println("Enter process " + (i + 1) + " Name:");
                        String nameOfProcess = sc.next();
                        names.add(nameOfProcess);
                        System.out.println("Enter process " + (i + 1) + " arrival time:");
                        int arrivalTime = sc.nextInt();
                        System.out.println("Enter process " + (i + 1) + " burst time:");
                        int burstTime = sc.nextInt();
                        if (burstTime == 0) {
                            System.out.println("Error in Burst Time, please Enter burst time greater than Zero");
                            burstTime = sc.nextInt();
                        }
                        System.out.println("Enter process " + (i + 1) + " priority time:");
                        int priorityNumber = sc.nextInt();
                        priority.add(priorityNumber);
                        System.out.println("Enter process " + (i + 1) + " Color:");
                        String color = sc.next();
                        colors.add(getColorByName(color));
                        Process process1 = new Process(nameOfProcess, arrivalTime, burstTime, priorityNumber, 0, color);
                        arrayOfProcesses.add(process1);
                    }
                    System.out.println("Enter context switching time:");
                    int contextSwitchTime = sc.nextInt();
                    Priority p1 = new Priority(arrayOfProcesses, contextSwitchTime);
                    p1.getHighProcess(numOfProcesses);
                    gui=new GUI(numOfProcesses);
                    for (int i=0;i<numOfProcesses;i++)
                    {
                        ArrayList<Double> execTime = new ArrayList<>();
                        for (int j=0;j<p1.Gantt_chart.size();j++)
                        {
                            if(p1.Gantt_chart.get(j).getProcessName()==arrayOfProcesses.get(i).getProcessName())
                            {
                                execTime.add((double) p1.Gantt_chart.get(j).getExecutedAt());
                            }
                        }
                         gui.setSeries(i,execTime);
                    }
                    gui.createChart(gui.dataset,colors);
                    //int tableSize=p1.Gantt_chart.size()-1;


                    gui.setName(names);
                    gui.setColor(colors);
                    gui.setPriority(priority);
                    /*
                    for (int i = 0; i <p1.Gantt_chart.size() ; i++) {
                        System.out.println("process name: " + p1.Gantt_chart.get(i).getProcessName() + "  executedAt Time: " + p1.Gantt_chart.get(i).getExecutedAt());
                    }

                     */
                } else if (choice == 2) {

                    for (int i = 0; i < numOfProcesses; i++) {
                        System.out.println("Enter process " + (i + 1) + " Name:");
                        String nameOfProcess = sc.next();
                        System.out.println("Enter process " + (i + 1) + " arrival time:");
                        int arrivalTime = sc.nextInt();
                        System.out.println("Enter process " + (i + 1) + " burst time:");
                        int burstTime = sc.nextInt();
                        if (burstTime == 0) {
                            System.out.println("Error in Burst Time, please Enter burst time greater than Zero");
                            burstTime = sc.nextInt();
                        }
                        System.out.println("Enter process " + (i + 1) + " Color:");
                        String color = sc.next();
                        Process process1 = new Process(nameOfProcess, arrivalTime, burstTime, 0, 0, color);
                        arrayOfProcesses.add(process1);
                        colors.add(getColorByName(color));
                        names.add(nameOfProcess);
                        priority.add(0);
                    }
                    SJF SJF1 = new SJF(arrayOfProcesses);
                    SJF1.getShortest(numOfProcesses);
                    gui=new GUI(numOfProcesses);
                    for (int i=0;i<numOfProcesses;i++)
                    {
                        ArrayList<Double> execTime = new ArrayList<>();
                        for (int j=0;j<SJF1.GanttChart.size();j++)
                        {
                            if( SJF1.GanttChart.get(j).getProcessName()==arrayOfProcesses.get(i).getProcessName())
                            {
                                execTime.add((double) SJF1.GanttChart.get(j).getExecutedAt());
                            }
                        }
                        gui.setSeries(i,execTime);
                    }
                    gui.createChart(gui.dataset,colors);
                    gui.setName(names);
                    gui.setColor(colors);
                    gui.setPriority(priority);
                    /*
                    for (int i = 0; i <SJF1.Gantt_chart.size() ; i++) {
                        System.out.println("process name: " + SJF1.Gantt_chart.get(i).getProcessName() + "  executedAt Time: " + SJF1.Gantt_chart.get(i).getExecutedAt());
                    }

                     */
                } else if (choice == 3) {
                    for (int i = 0; i < numOfProcesses; i++) {
                        System.out.println("Enter process " + (i + 1) + " Name:");
                        String nameOfProcess = sc.next();
                        System.out.println("Enter process " + (i + 1) + " arrival time:");
                        int arrivalTime = sc.nextInt();
                        System.out.println("Enter process " + (i + 1) + " burst time:");
                        int burstTime = sc.nextInt();
                        if (burstTime == 0) {
                            System.out.println("Error in Burst Time, please Enter burst time greater than Zero");
                            burstTime = sc.nextInt();
                        }
                        System.out.println("Enter process " + (i + 1) + " Color:");
                        String color = sc.next();
                        Process process1 = new Process(nameOfProcess, arrivalTime, burstTime, 0, 0, color);
                        arrayOfProcesses.add(process1);
                        colors.add(getColorByName(color));
                        names.add(nameOfProcess);
                        priority.add(0);
                    }
                    System.out.println("Enter context switching time:");
                    int contextSwitchTime = sc.nextInt();
                    Shortest_Remaining_Time SRT = new Shortest_Remaining_Time(arrayOfProcesses, contextSwitchTime);
                    SRT.getShortestRemainingTime();
                    gui=new GUI(numOfProcesses);
                    for (int i=0;i<numOfProcesses;i++)
                    {
                        ArrayList<Double> execTime = new ArrayList<>();
                        for (int j=0;j<SRT.Gantt_chart.size();j++)
                        {
                            if( SRT.Gantt_chart.get(j).getProcessName()==arrayOfProcesses.get(i).getProcessName())
                            {
                                execTime.add((double) SRT.Gantt_chart.get(j).getExecutedAt());
                            }
                        }
                        gui.setSeries(i,execTime);
                    }
                    gui.createChart(gui.dataset,colors);
                    gui.setName(names);
                    gui.setColor(colors);
                    gui.setPriority(priority);
                    /*
                    for (int i = 0; i <Shortest_Remaining_Time.Gantt_chart.size() ; i++) {
                        System.out.println("process name: "+Shortest_Remaining_Time.Gantt_chart.get(i).getProcessName()+"  executedAt Time: "+Shortest_Remaining_Time.Gantt_chart.get(i).getExecutedAt());
                    }

                     */

                } else if (choice == 4) {
                    Process[] processes;
                    processes = new Process[numOfProcesses];  //array to hold all the process

                    //for the user to add the process

                    for (int i = 0; i < numOfProcesses; i++) {
                        System.out.println("Enter process number " + (i + 1) + " name");
                        String name = sc.next();
                        System.out.println("Enter process number " + (i + 1) + "  burst time");
                        int b_t = sc.nextInt();
                        System.out.println("Enter process number " + (i + 1) + "  arrival time");
                        int a_t = sc.nextInt();
                        System.out.println("Enter process number " + (i + 1) + " priority");
                        int pri = sc.nextInt();
                        System.out.println("Enter process number " + (i + 1) + " quantum");
                        int quan = sc.nextInt();
                        System.out.println("Enter process " + (i + 1) + " Color:");
                        String color = sc.next();
                        //calling the constructor setting the process attributes with the user input
                        processes[i] = new Process(name, a_t, b_t, pri, quan, color);
                        colors.add(getColorByName(color));
                        names.add(name);
                        priority.add(pri);
                    }

                    //printing the process attributes for test purpose
                    for (int j = 0; j < numOfProcesses; j++) {

                        System.out.println("process number " + (j + 1) + ": ");
                        System.out.println("Name: " + processes[j].getProcessName());
                        System.out.println(processes[j].getProcessName() + " burst time " + processes[j].getBurstTime());
                        System.out.println(processes[j].getProcessName() + " arrival time " + processes[j].getArrivalTime());
                        System.out.println(processes[j].getProcessName() + " priority " + processes[j].getPriority());
                        System.out.println(processes[j].getProcessName() + " quantum " + processes[j].getQuantum());
                        System.out.println("****************************");
                    }
                    double maxArrivalTime;
                    AGAT_Scheduling AGAT=new AGAT_Scheduling();
                    maxArrivalTime = AGAT_Scheduling.getMaxArrivalTime(processes);
                    //Rule of calculating V1
                    if (maxArrivalTime > 10) {
                        Process.V1 = (maxArrivalTime / 10);
                    } else {
                        Process.V1 = 1.0;

                    }
                    AGAT.AGAT(processes);
                    System.out.println();
                    gui=new GUI(numOfProcesses);
                    for (int i=0;i<numOfProcesses;i++)
                    {
                        ArrayList<Double> execTime = new ArrayList<>();
                        for (int j=0;j<AGAT.Gantt_chart.size();j++)
                        {
                            if(Objects.equals(AGAT.Gantt_chart.get(j).getProcessName(), processes[i].getProcessName()))
                            {
                                execTime.add((double) AGAT.Gantt_chart.get(j).getExecutedAt());
                            }
                        }
                        gui.setSeries(i,execTime);
                    }
                    gui.createChart(gui.dataset,colors);
                    gui.setName(names);
                    gui.setColor(colors);
                    gui.setPriority(priority);
                    /*
                    for (int i = 0; i <AGAT_Scheduling.Gantt_chart.size() ; i++) {
                        System.out.println("process name: "+AGAT_Scheduling.Gantt_chart.get(i).getProcessName()+"  executedAt Time: "+AGAT_Scheduling.Gantt_chart.get(i).getExecutedAt());
                    }

                     */
                }
            }
        }
    }
}


















