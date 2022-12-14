package pack1;

public class myProcess {
    private String name;
    private int burstTime;
    private int arrivalTime;
    private int priority;
    private int waitingTime;
    private int turnAroundTime;
    private int quantum;

    public myProcess() {}
    public myProcess(String n, int burst, int arrive, int prior, int quantum){
        setName(n);
        setBurstTime(burst);
        setArrivalTime(arrive);
        setPriority(prior);
        setWaitingTime(0);
        setTurnAroundTime(0);
        setQuantum(quantum);
    }
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setQuantum(int quantum) {this.quantum = quantum;}
    public int getQuantum() {return quantum;}

    public void printProcess () {
        System.out.println("Name: " + getName());
        System.out.println("Arrival time: " + getArrivalTime());
        System.out.println("Burst time: " + getBurstTime());
        System.out.println("Priority: " + getPriority());
        System.out.println("Waiting time: " + getWaitingTime());
        System.out.println("Turn around time: " + getTurnAroundTime());
    }



}

