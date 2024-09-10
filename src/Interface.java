import javax.swing.*;
import java.awt.*;

public class Interface {
    private final JTextArea textArea;

    // Construtor da classe Interface
    public Interface(){
        // Inicializa a área de texto
        textArea = new JTextArea();;
        textArea.setEditable(false);

        // Cria uma nova janela (frame) e configura suas propriedades
        JFrame frame = new JFrame("Trabalho 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        // Torna a janela visível
        frame.setVisible(true);
    }

    // Método para iniciar o escalonador e adicionar processos
    public void iniciar(){
        // Cria uma instância do escalonador
        Escalonador escalonador = new Escalonador();

        // Adiciona alguns processos à fila do escalonador
        escalonador.addProcess(new Process(1, 5000));
        escalonador.addProcess(new Process(2, 5000));
        escalonador.addProcess(new Process(3, 5000));
        escalonador.addProcess(new Process(4, 5000));

        // Cria uma nova thread para executar o escalonador e atualizar a interface gráfica
        Thread th = new Thread(() -> escalonador.execute(textArea));
        th.start();
    }

    // Método principal que inicia a interface gráfica
    public static void main(String[] args) {
        // Utiliza o método SwingUtilities.invokeLater para garantir que a criação da interface ocorra na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            // Cria uma instância da classe Interface
            Interface i = new Interface();
            // Chama o método iniciar para iniciar o escalonador e adicionar processos
            i.iniciar();
        });
    }
}
