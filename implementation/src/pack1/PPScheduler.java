package pack1;


import java.util.Objects;
import java.util.Vector;

//Preemptive Priority Scheduling
public class PPScheduler {
    Scheduling scheduling = new Scheduling();

    private final Vector<String> executionOrder = new Vector<>();

    void calculateWaitingTime(Vector<myProcess> processes, int size, int aging_factor){

        int n = aging_factor;
        int completed = 0, time = 0;
        boolean check = false;
        int currentProcess = 0, currentPriority = Integer.MAX_VALUE;

        int [] priorities = new int [size];
        int [] burstCopy = new int [size];
        for (int i = 0; i < size; i++){
            priorities[i] = processes.get(i).getPriority();
            burstCopy[i] = processes.get(i).getBurstTime();
        }

        while (completed != size) {
            
            if (n == 0) {
                for (int i = 0; i < size; i++) {
                    int arrival = processes.get(i).getArrivalTime();
                    if (priorities[i] > 0 && burstCopy[i] > 0 && arrival <= time)
                    {
                        priorities[i]--;
                    }
                }
                n = aging_factor;
            } else {

                for (int i = 0; i < size; i++) {

                    int arrived = processes.get(i).getArrivalTime();
                    if ((arrived <= time) && (priorities[i] < currentPriority) && (burstCopy[i] > 0)) {
                        currentPriority = priorities[i];
                        currentProcess = i;
                        check = true;
                    }
                }
                if (!check) {
                    time++;
                    continue;
                }

                String currentProcessName = processes.get(currentProcess).getName();
                int orderSize = executionOrder.size();
                if (orderSize == 0) {
                    executionOrder.add(currentProcessName);
                } else if (!Objects.equals(currentProcessName, executionOrder.get(orderSize - 1))) {
                    executionOrder.add(currentProcessName);
                }

                burstCopy[currentProcess]--;

                if (burstCopy[currentProcess] == 0) {
                    currentPriority = Integer.MAX_VALUE;
                    completed++;
                    check = false;

                    int completionTime = time + 1;

                    int processBurstTime = processes.get(currentProcess).getBurstTime();
                    int processArrivalTime = processes.get(currentProcess).getArrivalTime();

                    int waiting = completionTime - processBurstTime - processArrivalTime;

                    if (waiting < 0)
                        waiting = 0;

                    processes.get(currentProcess).setWaitingTime(waiting);
                }
                time++;
                n--;
            }
        }
    }

    public void schedule(Vector <myProcess> processes, int size, int aging_factor) {
        calculateWaitingTime(processes, size, aging_factor);
        scheduling.printOrder(executionOrder);
        scheduling.printResults(processes);
    }

}
