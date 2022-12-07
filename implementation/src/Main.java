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
            System.out.print("Process [" + i + "] name:  ");
            String name = sc.nextLine();
            System.out.print("Process [" + i + "] arrival time:  ");
            int arrival = validInput(input);
            if (arrival == -1) break;
            System.out.print("Process [" + i + "] burst time:  ");
            int burst = validInput(input);
            if (burst == -1) break;
            System.out.print("Process [" + i + "] priority:  ");
            int priority = validInput(input);
            if (priority == -1) break;

            myProcess p = new myProcess();
            p.setName(name);
            p.setArrivalTime(arrival);
            p.setBurstTime(burst);
            p.setPriority(priority);
            processes.add(p);
        }

    }
}