package pack1;

import java.util.*;

import static java.lang.Math.ceil;
import static java.lang.Math.min;

public class AGScheduler {
    double time = 0;
    Scheduling scheduling = new Scheduling();
    private final Vector<String> executionOrder = new Vector<>();
    private boolean sFlag;


    myProcess checkPriority(Vector<myProcess> processes, myProcess currentProcess) {
        boolean flag = false;
        myProcess newProcess = new myProcess();
        Vector<myProcess> currentProcesses = new Vector<>(processes);

        currentProcesses.sort(new Comparator<myProcess>() {
            @Override
            public int compare(myProcess o1, myProcess o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });

        newProcess = currentProcess;

        for (myProcess process : currentProcesses) {
            if (process.getBurstTime() > 0) {
                if (process.getPriority() < newProcess.getPriority() && process.getArrivalTime() <= time) {
                    newProcess = process;
                    flag = true;
                }
            }
        }
        if (flag) return newProcess;
        else return currentProcess;
    }

    myProcess checkBurstTime(Vector<myProcess> processes, myProcess currentProcess, int[] burstCopy) {

        boolean flag = false;
        myProcess newProcess = new myProcess();

        newProcess = currentProcess;

        int currentIndex = processes.indexOf(currentProcess);
        for (int i =0; i < processes.size(); i++) {
            if (processes.get(i).getBurstTime() > 0) {
                if (burstCopy[i] < burstCopy[currentIndex]  && processes.get(i).getArrivalTime() <= time ) {
                    newProcess = processes.get(i);
                    flag = true;
                }
            }
        }
        if (flag) return newProcess;
        else return currentProcess;
    }


    void calculateWaitingTime(Vector<myProcess> processes, int size) {

        int completed = 0;
        myProcess currentProcess = new myProcess();
        int currentIndex = 0;

        int[] quantum = new int[size];
        int[] burstCopy = new int[size];
        int [] burstCopy2 = new int [size];
        for (int i = 0; i < size; i++) {
            quantum[i] = processes.get(i).getQuantum();
            burstCopy[i] = processes.get(i).getBurstTime();
            burstCopy2[i] = processes.get(i).getBurstTime();

        }

        boolean pFlag = false;
        boolean sFlag = false;
        boolean switched = false;

        int flag = 0;
        int oldFlag = 0;
        int amount = 0;

        myProcess newProcess = new myProcess();

        while (completed != size) {

            if (switched || executionOrder.size() == 0) {
                if (processes.get(flag).getArrivalTime() <= time) {

                    if (burstCopy[flag] > 0) {
                        if (quantum[flag] > 0) {
                            currentProcess = processes.get(flag);
                            currentIndex = flag;
                            amount = min((int) ceil(0.25 * currentProcess.getQuantum()), quantum[currentIndex]);
                            amount = min (amount, burstCopy[currentIndex]);

                            quantum[currentIndex] -= amount;
                                    time += amount;
                            burstCopy[currentIndex] -= amount;
                            switched = false;
                        }else {
                            quantum[flag] = currentProcess.getQuantum() + 2;
                            currentProcess.setQuantum(quantum[flag]);
                            switched = true;
                            flag = 0;
                            continue;
                        }
                    } else {
                        flag = (flag + 1) % size;
                        continue;
                    }
                } else {
                    time++;
                    continue;
                }
            }



            String currentProcessName = currentProcess.getName();
            int orderSize = executionOrder.size();
            if (executionOrder.size() == 0) {
                executionOrder.add(currentProcess.getName());
            }
            else if (!Objects.equals(currentProcessName, executionOrder.get(orderSize-1))) {
                executionOrder.add(currentProcessName);
            }


            if (burstCopy[currentIndex] <= 0) {
                int waiting = (int) (time - currentProcess.getArrivalTime() - currentProcess.getBurstTime());
                currentProcess.setWaitingTime(waiting);
                currentProcess.setBurstTime(0);
                quantum[currentIndex] = 0;
                flag = 0;
                completed++;
                switched = true;
                continue;
            }


            // first part , second part , third part
            //****priority check *****

            newProcess = checkPriority(processes, currentProcess);
            if (newProcess == currentProcess) {
                pFlag = true;
                if (burstCopy[currentIndex] > 0) {
                    if (quantum[currentIndex] > 0) {
                        time -= amount;
                        quantum[currentIndex] += amount;
                        burstCopy[currentIndex] += amount;
                            amount = min((int) ceil(0.5 * currentProcess.getQuantum()), quantum[currentIndex]);
                            amount = min (amount, burstCopy[currentIndex]);
                        quantum[currentIndex] -= amount;
                            time += amount;
                        burstCopy[currentIndex] -= amount;

                        //if (burstCopy[currentIndex] < 0) burstCopy[currentIndex] = 0;
                    } else {
                        quantum[currentIndex] = currentProcess.getQuantum() + 2;
                            switched = true;
                        flag = 0;
                        continue;
                    }
                } else {
                    int waiting = (int) (time - currentProcess.getArrivalTime() - currentProcess.getBurstTime());
                    currentProcess.setWaitingTime(waiting);
                    currentProcess.setBurstTime(0);
                    quantum[currentIndex] = 0;
                    flag = 0;
                    completed++;
                    switched = true;
                    continue;
                }
                // p1 : 0 2 4 , p2 : 4 7 , p3 : 7 9, p2: 9 12,
            } else {
                int remaining = currentProcess.getQuantum() - amount;
                quantum[currentIndex] = (int) (currentProcess.getQuantum() + ceil(remaining/2.0));
                currentProcess.setQuantum(quantum[currentIndex]);
                currentProcess = newProcess;
                currentIndex = processes.indexOf(newProcess);
                flag = currentIndex;
                pFlag = false;
                switched = true;
                continue;
            }


            if (quantum[currentIndex] <= 0 && burstCopy[currentIndex] > 0 ) {
                quantum[currentIndex] = currentProcess.getQuantum() + 2;
                currentProcess.setQuantum(quantum[currentIndex]);
                switched = true;
                flag = 0;
            }


            newProcess = checkBurstTime(processes, currentProcess, burstCopy);
            int count = 0;
            while (newProcess == currentProcess) {
                if (burstCopy[currentIndex] > 0) {
                    if (quantum[currentIndex] > 0) {
                        time -= amount;
                        quantum[currentIndex] += amount;
                        burstCopy[currentIndex] += amount;
                            if (count == 0) {
                            amount = (int)min( ceil(0.75 * currentProcess.getQuantum()), quantum[currentIndex]);
                            amount = min (amount, burstCopy[currentIndex]);

                                quantum[currentIndex] -= amount;
                                    time += amount;
                            burstCopy[currentIndex] -= amount;
                            count++;
                        } else {
                            amount = min(currentProcess.getQuantum(), quantum[currentIndex]);
                            amount = min (amount, burstCopy[currentIndex]);

                                quantum[currentIndex] -= amount;
                                    time += amount;
                            burstCopy[currentIndex] -= amount;
                            count++;
                        }
                    } else {
                        quantum[currentIndex] = currentProcess.getQuantum() + 2;
                        flag = 0;
                        switched = true;
                        break;
                    }
                }
                else {
                    int waiting = (int) (time - currentProcess.getArrivalTime() - currentProcess.getBurstTime());
                    currentProcess.setWaitingTime(waiting);
                    quantum[currentIndex] = 0;
                    currentProcess.setBurstTime(0);
                    flag = 0;
                    completed++;
                    switched = true;
                    break;
                }
                newProcess = checkBurstTime(processes, newProcess, burstCopy);
            }

            if (quantum[currentIndex] <= 0 && burstCopy[currentIndex] > 0) {
                quantum[currentIndex] = currentProcess.getQuantum() + 2;
                switched = true;
                flag = 0;
            }

            if (newProcess != currentProcess) {
                int remaining = currentProcess.getQuantum() - amount;
                quantum[currentIndex] = (int) (currentProcess.getQuantum() + remaining);
                currentProcess.setQuantum(quantum[currentIndex]);
                switched = true;
                currentProcess = newProcess;
                currentIndex = processes.indexOf(newProcess);
                flag = processes.indexOf(newProcess);
            }

        }
        for (int i = 0; i < size; i++) {
            processes.get(i).setBurstTime(burstCopy2[i]);
        }

    }

    public void schedule(Vector<myProcess> processes, int size) {
        calculateWaitingTime(processes, size);
        scheduling.printOrder(executionOrder);
        scheduling.printResults(processes);
    }


}
