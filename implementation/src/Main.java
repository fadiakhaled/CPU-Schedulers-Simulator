import pack1.*;

import java.lang.Process;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static int validInput(int input) {
        try {
            input = sc.nextInt();
            return input; 
        } catch (InputMismatchException exception) {
            System.out.println("Invalid input to an integer field. please rerun the program :)");
            return -1; 
        }
    }

    public static void main(String[] args) {

        int input = 0; 
        Vector<myProcess> processes = new Vector<myProcess>();
        System.out.print("Enter number of processes: ");
        int pnum = validInput(input);

        for (int i = 1; i <= pnum; i++) {
            sc.nextLine();
            //System.out.print("Process [" + i + "] name:  ");
            String name = sc.nextLine();
            //System.out.print("Process [" + i + "] arrival time:  ");
            int arrival = validInput(input);
            if (arrival == -1) break;
            //System.out.print("Process [" + i + "] burst time:  ");
            int burst = validInput(input);
            if (burst == -1) break;
            /*System.out.print("Process [" + i + "] priority:  ");
            int priority = validInput(input);
            if (priority == -1) break;*/

            myProcess p = new myProcess(name,  burst, arrival, 0);
            processes.add(p);
        }
        //for (myProcess process: processes) process.printProcess();

        System.out.println( "1 - Preemptive Shortest-Job First (SJF) Scheduling");
        SJFscheduling sjf = new SJFscheduling();
        sjf.schedule(processes, processes.size());



    }
    /*
    * p1
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
}