public class Process {
    private int pid, totalProcessingTime;
    private int executedProcessingTime;
    private State estado;
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getTotalProcessingTime() {
        return totalProcessingTime;
    }

    public void setTotalProcessingTime(int totalProcessingTime) {
        this.totalProcessingTime = totalProcessingTime;
    }

    public int getExecutedProcessingTime() {
        return executedProcessingTime;
    }

    public void setExecutedProcessingTime(int executedProcessingTime) {
        this.executedProcessingTime = executedProcessingTime;
    }

    public State getEstado() {
        return estado;
    }

    public void setEstado(State estado) {
        this.estado = estado;
    }

    public Process(int pid, int totalProcessingTime) {
        this.pid = pid;
        this.totalProcessingTime = totalProcessingTime;
        this.estado = State.READY;
    }
}