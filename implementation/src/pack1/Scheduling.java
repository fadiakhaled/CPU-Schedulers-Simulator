package pack1;

import java.util.Vector;

public class Scheduling {
    void calculateTurnAroundTime (Vector<myProcess> processes){

        for (myProcess process : processes) {
            int turnTime = process.getBurstTime() + process.getWaitingTime();
            process.setTurnAroundTime(turnTime);
        }

    }
    float calculateAverageWaiting(Vector<myProcess> processes, int size) {
        float avgWaiting = 0.0F;
        for (myProcess process : processes) {
            avgWaiting += process.getWaitingTime();
        }
        return avgWaiting/size;
    }
    float calculateAverageTurnAround(Vector<myProcess> processes, int size) {
        float avgTurnAround = 0.0F;

        for (myProcess process : processes) {
            avgTurnAround += process.getTurnAroundTime();
        }
        return avgTurnAround/size;
    }
    public void printOrder (Vector<String> executionOrder) {
        System.out.println("\nProcesses execution order");
        System.out.print(" [ ");
        for (String s : executionOrder) {
            System.out.print(s + " ");
        }
        System.out.println("]");
    }

    public void printResults (Vector<myProcess> processes) {
        calculateTurnAroundTime(processes);
        float avgWaiting = calculateAverageWaiting(processes, processes.size());
        float avgTurnAround = calculateAverageTurnAround(processes, processes.size());

        // printing each process waiting time and turn around time
        System.out.println("\nProcesses |  Arrival Time |  Burst Time |  Waiting Time  | Turn Around Time");
        System.out.println("----------|---------------|-------------|----------------|-----------------");

        for (myProcess process : processes) {
            System.out.print("    " + process.getName() + "\t  |\t\t ");
            System.out.print(process.getArrivalTime()+"\t\t  |\t\t ");
            System.out.print(process.getBurstTime() + "\t\t|\t\t");
            System.out.print(process.getWaitingTime() + "\t\t |\t\t");
            System.out.println(process.getTurnAroundTime());
        }
        System.out.println("\nAverage Waiting Time = " + avgWaiting + " ms");
        System.out.println("Average Turn Around Time = " + avgTurnAround + " ms");

    }
}
