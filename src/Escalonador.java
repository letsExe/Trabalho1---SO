import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import javax.swing.*;

public class Escalonador {
    private Queue<Process> filaProcess;

    public Escalonador() {
        // Inicializa a fila de processos como uma LinkedList
        this.filaProcess = new LinkedList<Process>();
    }

    public synchronized void addProcess(Process p){
        // Adiciona um processo à fila e notifica outras threads
        filaProcess.add(p);
        notify();
    }

    public synchronized void execute(JTextArea textArea){
        // Método que executa os processos presentes na fila
        Random r = new Random();

        // Loop enquanto houver processos na fila
        while(!filaProcess.isEmpty()){
            // Retira o processo do início da fila
            Process p1 = filaProcess.poll();

            if (p1.getEstado() == State.READY){
                // Atualiza a área de texto com informações sobre o processo
                SwingUtilities.invokeLater(() -> {
                    textArea.append("\nProcesso " + p1.getPid() + " aguardando EXECUÇÃO. Falta: " + p1.getTotalProcessingTime()
                            + ". Executou " + p1.getExecutedProcessingTime());
                });
                // Troca o estado atual para em execução e adiciona o processo novamente à fila
                p1.setEstado(State.RUNNING);
                addProcess(p1);
            }
            else if (p1.getEstado() == State.RUNNING){
                SwingUtilities.invokeLater(() -> {
                    textArea.append("\nProcesso " + p1.getPid() + " em EXECUÇÃO. Falta: " + p1.getTotalProcessingTime()
                            + ". Executou " + p1.getExecutedProcessingTime());
                });

                double block = r.nextDouble();
                double chanceBlock = 0.10;
                int quantum = 1000;

                if(block < chanceBlock){
                    // Bloqueia o processo e o adiciona novamente à fila
                    p1.setEstado(State.BLOCK);
                    addProcess(p1);
                }
                else {
                    if(p1.getTotalProcessingTime() > 0){
                        try {
                            // Simula a execução do processo
                            Thread.sleep(quantum);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        // Atualiza o tempo de processamento do processo
                        p1.setTotalProcessingTime(p1.getTotalProcessingTime() - quantum);

                        if(!filaProcess.isEmpty() || p1.getTotalProcessingTime() >= 0){
                            // Se ainda houver tempo de processamento, volta para o estado READY
                            p1.setEstado(State.READY);
                            p1.setExecutedProcessingTime(p1.getExecutedProcessingTime() + quantum);
                            addProcess(p1);
                        }
                    }
                    else{
                        // Se o tempo de processamento for zero, marca como TERMINATED
                        p1.setEstado(State.TERMINATED);
                        addProcess(p1);
                    }
                }
            }
            else if (p1.getEstado() == State.BLOCK ){
                SwingUtilities.invokeLater(() -> {
                    textArea.append("\nProcesso " + p1.getPid() + " BLOQUEADO. Falta: " + p1.getTotalProcessingTime()
                            + ". Executou " + p1.getExecutedProcessingTime());
                });
                double block = r.nextDouble();
                double chanceBlock = 0.73;

                try {
                    // Simula o bloqueio do processo
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

                if (block < chanceBlock){
                    // Se o bloqueio for bem-sucedido, volta para o estado READY
                    p1.setEstado(State.READY);
                }
                // Adiciona o processo novamente à fila
                addProcess(p1);
            }
            else if (p1.getEstado() == State.TERMINATED){
                SwingUtilities.invokeLater(() -> {
                    textArea.append("\nProcesso " + p1.getPid() + " TERMINADO. Falta: " + p1.getTotalProcessingTime()
                            + ".    Executou " + p1.getExecutedProcessingTime());
                });
            }
        }
    }
}
