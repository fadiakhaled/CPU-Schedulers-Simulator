package pack1;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class RRScheduling extends Scheduling {
    private final Vector<String> executionOrder = new Vector<>();

    public void calculateWaitingTime(Vector<myProcess> processes, int size, int quantum) {

        int[] burstCopy = new int[size];
        for (int i = 0; i < size; i++) {
            burstCopy[i] = processes.get(i).getBurstTime();
        }

        int time = 0, completed = 0;
        boolean check = false;

        while (completed != size) {

            for (int i = 0; i < size; i++) {

                int arrivalTime = processes.get(i).getArrivalTime();
                if (arrivalTime <= time) {
                    check = true;
                    if (burstCopy[i] > 0) {
                        String processName = processes.get(i).getName();
                        executionOrder.add(processName);
                        if (burstCopy[i] > quantum) {
                            // time will be updated by a quantum because the process still has burst time
                            time += quantum;
                            burstCopy[i] = burstCopy[i] - quantum;
                        } else {
                            time += burstCopy[i]; // the process won't take the whole quantum

                            int waiting = time - processes.get(i).getBurstTime() - arrivalTime;
                            processes.get(i).setWaitingTime(waiting);
                            burstCopy[i] = 0;
                            completed++;
                        }

                    }
                }
            }
            if (!check) {
                time += quantum;
            }
        }
    }

    public void schedule(Vector<myProcess> processes, int size, int quantum) {

        calculateWaitingTime(processes, size, quantum);

        TreeMap<String, myProcess> sortedProcess = new TreeMap<>();
        for (myProcess process : processes) {
            sortedProcess.put(process.getName(), process);
        }

        processes.removeAllElements();

        for (Map.Entry<String, myProcess> entry : sortedProcess.entrySet()) {
            processes.add(entry.getValue());
        }

        printOrder(executionOrder);
        printResults(processes);
    }


}

