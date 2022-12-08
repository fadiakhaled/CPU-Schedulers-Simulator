package pack1;

import java.util.Objects;
import java.util.Vector;

public class SJFScheduling {
    Scheduling scheduling = new Scheduling();

    private final Vector<String> executionOrder = new Vector<>();

    public void calculateWaitingTime(Vector<myProcess> processes, int size){
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
                int processBurstTime = processes.get(currentProcess).getBurstTime();
                int processArrivalTime = processes.get(currentProcess).getArrivalTime();

                int waiting = currentFinishTime - processBurstTime - processArrivalTime;

                if (waiting < 0)
                    waiting = 0;

                processes.get(currentProcess).setWaitingTime(waiting);
            }
            time++;
        }
    }



    public void schedule(Vector <myProcess> processes, int size) {
        calculateWaitingTime(processes, size);
        scheduling.printOrder(executionOrder);
        scheduling.printResults(processes);
    }

}
