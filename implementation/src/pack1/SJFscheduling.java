package pack1;

import java.util.Objects;
import java.util.Vector;

public class SJFscheduling {

    private Vector<String> executionOrder = new Vector<String>();
    public Vector<myProcess> calculateWaiting(Vector<myProcess> processes, int size){
        int[] burstCopy = new int[size];
        for (int i = 0; i < size; i++) burstCopy[i] = processes.get(i).getBurstTime();


        int completed = 0, time = 0, minimumBurst = Integer.MAX_VALUE;
        int currentProcess = 0;
        boolean check = false;

        while (completed != size) {
            // find process with minimum remaining burst time
            for (int i = 0; i < size; i++) {
                /*
                * check if the process has arrived according to the current time elapsed
                * check if it has burst time less than current process's burst time
                * check if the process hasn't finished yet (burst time > 0)
                 */
                int arrivalTime = processes.get(i).getArrivalTime();
                if ((arrivalTime <= time) && (burstCopy[i] < minimumBurst) && (burstCopy[i]  > 0)) {
                    minimumBurst = burstCopy[i];
                    currentProcess = i;
                    check = true;
                }
            }


            // if no process has arrived at a specific time
            if (!check) {
                time++;
                continue;
            }

            //filling the processes execution order vector
            String currentProcessName = processes.get(currentProcess).getName();
            int orderSize = executionOrder.size();
            if (orderSize == 0 )
                executionOrder.add(currentProcessName);
            else if (!Objects.equals(currentProcessName, executionOrder.get(orderSize-1))) {
               executionOrder.add(currentProcessName);
            }


            burstCopy[currentProcess]--; // reduce the burst time of the current running process
            minimumBurst = burstCopy[currentProcess]; // update the current minimum burst time

            // if a process is executed completely
            if (burstCopy[currentProcess] == 0) {

                minimumBurst = Integer.MAX_VALUE; // reset to continue comparing
                completed++; // increase number of completed processes
                check = false;

                int currentFinishTime = time+1;
                int processBrustTime = processes.get(currentProcess).getBurstTime();
                int processArrivalTime = processes.get(currentProcess).getArrivalTime();

                int waiting = currentFinishTime - processBrustTime - processArrivalTime;

                if (waiting < 0)
                    waiting = 0;

                processes.get(currentProcess).setWaitingTime(waiting);
            }
            time++;
        }
        return processes;
    }

    public Vector<myProcess> calculateTurnAroundTime (Vector<myProcess> processes){

        for (myProcess process : processes) {
            int turnTime = process.getBurstTime() + process.getWaitingTime();
            process.setTurnAroundTime(turnTime);
        }

        return processes;
    }

    float findAverageWaiting (Vector<myProcess> processes, int size) {
        float avgWaiting = 0.0F;
        for (myProcess process : processes) {
            avgWaiting += process.getWaitingTime();
        }
        return avgWaiting/size;
    }

    float findAverageTurnAround (Vector<myProcess> processes, int size) {
        float avgTurnAround = 0.0F;

        for (myProcess process : processes) {
            avgTurnAround += process.getTurnAroundTime();
        }
        return avgTurnAround/size;
    }

    public void schedule(Vector <myProcess> processes, int size) {

        Vector<myProcess> results;
        results = calculateWaiting(processes, size);
        results = calculateTurnAroundTime(results);
        float avgWaiting = findAverageWaiting(results, size);
        float avgTurnAround = findAverageTurnAround(results, size);

        System.out.print("\nProcesses execution order");
        System.out.print(" [ ");
        for (String s : executionOrder) {
            System.out.print(s + " ");
        }
        System.out.println("]");

        System.out.println("\nProcesses |  Waiting time  | Turn around time" );
        System.out.println("----------|----------------|------------------");

        for (myProcess process : results) {
            System.out.print("    "+ process.getName() + "\t  |\t\t ");
            System.out.print(process.getWaitingTime() + "\t\t   |\t\t");
            System.out.println(process.getTurnAroundTime());
        }
        System.out.println("\nAverage waiting time = " + avgWaiting);
        System.out.println("Average turn around time = " + avgTurnAround);
    }

}
