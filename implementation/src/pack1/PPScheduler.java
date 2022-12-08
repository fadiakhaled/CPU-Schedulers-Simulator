package pack1;


import java.util.Objects;
import java.util.Vector;

//Preemptive Priority Scheduling
public class PPScheduler {
    Scheduling scheduling = new Scheduling();

    private final Vector<String> executionOrder = new Vector<>();

    void calculateWaitingTime(Vector<myProcess> processes, int size){

        int completed = 0, time = 0;
        boolean check = false;
        int currentProcess = 0, currentPriority = Integer.MAX_VALUE;

        /*processes.sort(new Comparator<myProcess>() {
            @Override
            public int compare(myProcess o1, myProcess o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });*/

        int [] priorities = new int [size];
        int [] burstCopy = new int [size];
        for (int i = 0; i < size; i++){
            priorities[i] = processes.get(i).getPriority();
            burstCopy[i] = processes.get(i).getBurstTime();
        }

        while (completed != size) {

            for (int i = 0; i < size; i++) {

                int arrived = processes.get(i).getArrivalTime();
                if ((arrived <= time) && (priorities[i] < currentPriority) && (burstCopy[i] > 0)){
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
            if (orderSize == 0 ) {
                executionOrder.add(currentProcessName);
            }
            else if (!Objects.equals(currentProcessName, executionOrder.get(orderSize-1))) {
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
        }
    }

    public void schedule(Vector <myProcess> processes, int size) {
        calculateWaitingTime(processes, size);
        scheduling.printOrder(executionOrder);
        scheduling.printResults(processes);
    }

}
