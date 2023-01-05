# CPU-Schedulers-Simulator
Java program to simulate CPU Schedulers:   
* Preemptive Shortest-Job First (SJF) Scheduling  
SJF Scheduling is one of the CPU scheduling algorithms in which the process that has the smallest execution time (burst time) is chosen for the next exectuion. Preemptive SJF is also called Shortest-Remaining-Time-First (SRTF). in SRTF If a process arrives with a shorter execution time than that of the current executing process, the current process will be removed from executuion and the CPU cycle is allocated to the process with the shorter burst time. 
    
* Round Robin (RR) Scheduling 
Round robin scheduling algorithm is one of the CPU scheduling algorithms in which every process gets a fixed amount of time quantum to execute the process. Once a process is executed for given time period. Process is pre-empted and added to the end of the ready queue and other process executes for given time period.

* Preemptive Priority Scheduling    prioriry scheduling algorithm is one of the CPU scheduling algorithms which is based on the priority of a process, preemptive priority scheduling checks if a process with higher priority than the current process being executed arrives then the CPU is switched form the current task and given to the higher priority task. 

* AG Scheduling    
  *   Each process is provided a static time to execute called quantum.
  * Once a process is executed for given time period, it’s called FCFS till the finishing of (ceil(25%)) of its Quantum time then it’s converted tonon preemptive Priority till the finishing of the next (ceil(25%)), after that it’s converted to preemptive Shortest- Job First (SJF). 
  * We have 3 scenarios of the running process:
     1. The running process used all its quantum time and it still have job to do (add this process to the end of the queue, then increases its Quantum time by Two).
     2. The running process was execute as non-preemptive Priority and didn’t use all its quantum time based on another process converted from ready to running (add this process to the end of the queue, and then increase its Quantum time by ceil(the remaining Quantum time/2)). 
     3. The running process was execute as preemptive Shortest-Job First (SJF) and didn’t use all its quantum time based on another process converted from ready to running (add this process to the end of the queue, and then increase its Quantum time by the remaining Quantum time).
     4. The running process didn’t use all of its quantum time because it’s no longer need that time and the job was completed (set it’s quantum time to zero).
