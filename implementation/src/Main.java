import pack1.*;

import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static int validInput() {
        try {
            return sc.nextInt();
        } catch (InputMismatchException exception) {
            System.out.println("Invalid input to an integer field. please rerun the program :)");
            return -1; 
        }
    }

    public static void main(String[] args) {

        Vector<myProcess> processes = new Vector<>();
        System.out.print("Enter number of processes: ");
        int processesNumber = validInput();
        System.out.print("Enter Robin Time Quantum: ");
        int quantum = validInput();
        System.out.print("Enter context switching cost: ");
        int contextSwitching = validInput();
        System.out.println("Enter aging factor for starvation problem: ");
        int aging_factor = validInput();

        for (int i = 1; i <= processesNumber; i++) {
            sc.nextLine();
            //System.out.print("Process [" + i + "] name:  ");
            String name = sc.nextLine();
            //System.out.print("Process [" + i + "] arrival time:  ");
            int arrival = validInput();
            if (arrival == -1) break;
            //System.out.print("Process [" + i + "] burst time:  ");
            int burst = validInput();
            if (burst == -1) break;
            //System.out.print("Process [" + i + "] priority:  ");
            int priority = validInput();
            if (priority == -1) break;

            myProcess p = new myProcess(name,  burst, arrival, priority);
            processes.add(p);
        }


        processes.sort(new Comparator<myProcess>() {
            @Override
            public int compare(myProcess o1, myProcess o2) {
                return o1.getArrivalTime() - o2.getArrivalTime();
            }
        });

        for (myProcess process: processes) {
            System.out.println(process.getName()+ " " + process.getArrivalTime());
        }


        System.out.println( "1 - Preemptive Shortest-Job First (SJF) Scheduling");
        SJFScheduler sjf = new SJFScheduler();
        sjf.schedule(processes, processes.size(), contextSwitching);

        for (myProcess process: processes) {
            process.setWaitingTime(0);
            process.setTurnAroundTime(0);
        }

        System.out.println( "\n2 - Round Robin (RR) Scheduling");
        RRScheduler rr = new RRScheduler();
        rr.schedule(processes, processes.size(), quantum, contextSwitching);

        for (myProcess process: processes) {
            process.setWaitingTime(0);
            process.setTurnAroundTime(0);
        }

        System.out.println("\n 3 - Preemptive Priority Scheduling");
        PPScheduler pp = new PPScheduler();
        pp.schedule(processes, processes.size(), aging_factor);


    }
    /*
    *
    *
p1
0
1
p2
1
7
p3
2
3
p4
3
6
p5
4
5
p6
5
15
p7
15
8
* */
    /*
p1
0
3
p2
2
5
p3
1
4
p4
4
2
p5
6
9
p6
5
4
p7
7
10
     */
}