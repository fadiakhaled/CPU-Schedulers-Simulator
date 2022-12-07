package pack1;

import java.util.Objects;
import java.util.Vector;

public class SJFscheduling {

    private Vector<String> executionOrder = new Vector<String>();
    public Vector<myProcess> calculateWaiting(Vector<myProcess> processes, int sz){
        int[] burstCopy = new int[sz];
        for (int i = 0; i < sz; i++) burstCopy[i] = processes.get(i).getBurstTime();


        int completed = 0, time = 0, mini = Integer.MAX_VALUE;
        int currentProcess = 0;
        boolean check = false;

        while (completed != sz) {
            // find process with minimum remaining burst time
            for (int i = 0; i < sz; i++) {
                /*
                * check if the process has arrived
                * check if it has burst time less than current process's burst time
                * check if the process hasn't finished yet (burst time > 0)
                 */
                if ((processes.get(i).getArrivalTime() <= time)
                        && (burstCopy[i] < mini) && burstCopy[i]  > 0 ) {
                    mini = burstCopy[i];
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
            if (executionOrder.size() == 0 )  executionOrder.add(processes.get(currentProcess).getName());
            else if (!Objects.equals(processes.get(currentProcess).getName(), executionOrder.get(executionOrder.size() - 1))) {
                executionOrder.add(processes.get(currentProcess).getName());
            }


            burstCopy[currentProcess]--; // reduce the burst time of the current running process
            mini = burstCopy[currentProcess]; // update the current minimum burst time

            // if a process is executed completely
            if (burstCopy[currentProcess] == 0) {

                mini = Integer.MAX_VALUE; // reset to continue comparing
                completed++; // increase number of completed processes
                check = false;

                int currentFinish = time+1;

                int waiting = currentFinish - processes.get(currentProcess).getBurstTime() - processes.get(currentProcess).getArrivalTime();
                if (waiting < 0) waiting =0;
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

    float findAverageWaiting (Vector<myProcess> processes, int sz) {
        float avgWaiting = 0.0F;
        for (myProcess process : processes) {
            avgWaiting += process.getWaitingTime();
        }
        return avgWaiting/sz;
    }

    float findAverageTurnAround (Vector<myProcess> processes, int sz) {
        float avgTurnAround = 0.0F;

        for (myProcess process : processes) {
            avgTurnAround += process.getTurnAroundTime();
        }
        return avgTurnAround/sz;
    }

    public void schedule(Vector <myProcess> processes, int sz) {

        System.out.println("\n");
        Vector<myProcess> results;
        results = calculateWaiting(processes, sz);
        results = calculateTurnAroundTime(results);
        float avgWaiting = findAverageWaiting(results, sz);
        float avgTurnAround = findAverageTurnAround(results, sz);

        System.out.println("\t Processes execution order");
        System.out.print("\t[ ");
        for (String s : executionOrder) {
            System.out.print(s + " ");
        }
        System.out.println("]");

        System.out.println("\nProcesses   Waiting time   Turn around time" );

        for (myProcess process : results) {
            System.out.print("    "+ process.getName() + "\t\t\t");
            System.out.print(process.getWaitingTime() + "\t\t\t   ");
            System.out.println(process.getTurnAroundTime());
        }
        System.out.println("\nAverage waiting time = " + avgWaiting);
        System.out.println("Average turn around time = " + avgTurnAround);
    }

}
