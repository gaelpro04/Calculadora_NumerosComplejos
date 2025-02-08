import  javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

//Clase que modela la interfaz de una calculadora compleja
public class CalculadoraCompleja {

    //Atributos lógicos
    private ModeloCalculadora calculadora;
    private NumeroComplejo numeroComplejo1;
    private NumeroComplejo numeroComplejo2;
    private String operacion;

    //Atributos GUI
    private JFrame frame;
    private JPanel panelNumeroComplejo1, panelNumeroComplejo2, panelOperaciones, panelOperacionesBotones, panelResultado;
    private JTextField lecturaNumeroComplejo1, lecturaNumeroComplejo2;
    private JLabel labelNumeroComplejo1, labelNumeroComplejo2, labelResultadoEnunciado, labelOperacionesEnunciado;
    private JLabel labelResultado;
    private ArrayList<JButton> botonesOperaciones;
    private JButton botonResultado;

    public CalculadoraCompleja()
    {
        calculadora = new ModeloCalculadora();

        frame = new JFrame("Calculadora compleja");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        panelNumeroComplejo1 = new JPanel(new BoxLayout(panelNumeroComplejo1, BoxLayout.Y_AXIS));
        panelNumeroComplejo2 = new JPanel(new BoxLayout(panelNumeroComplejo2, BoxLayout.Y_AXIS));
        panelOperacionesBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelOperaciones = new JPanel(new BoxLayout(panelOperaciones, BoxLayout.Y_AXIS));
        panelResultado = new JPanel(new BoxLayout(panelResultado, BoxLayout.Y_AXIS));

        lecturaNumeroComplejo1 = new JTextField("");
        lecturaNumeroComplejo1.setPreferredSize(new Dimension(150, 30));
        lecturaNumeroComplejo1.addActionListener(lectura -> lecturaNumeroComplejo1());

        lecturaNumeroComplejo2 = new JTextField("");
        lecturaNumeroComplejo2.setPreferredSize(new Dimension(150, 30));
        lecturaNumeroComplejo2.addActionListener(lectura -> lecturaNumeroComplejo2());

        labelNumeroComplejo1 = new JLabel("Numero Complejo 1");
        labelNumeroComplejo2 = new JLabel("Numero Complejo 2");
        labelResultadoEnunciado = new JLabel("Resultado");
        labelOperacionesEnunciado = new JLabel("Operaciones");
        labelResultado = new JLabel("0");

        String[] operaciones = {"+","-","/","*"};
        botonesOperaciones = new ArrayList<>(4);
        for (int i = 0; i < operaciones.length; i++) {

            JButton boton = new JButton(operaciones[i]);
            operacion = operaciones[i];
            boton.addActionListener(lectura -> getOperacion(operacion));
            botonesOperaciones.add(new JButton(operaciones[i]));
        }

        botonResultado = new JButton("=");
        botonResultado.setPreferredSize(new Dimension(150, 30));
        botonResultado.addActionListener(lectura -> botonResultado());

        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    /**
     * Método que convierte un String a un número complejo
     * @param numeroComplejo
     * @return
     */
    private NumeroComplejo stringToNumeroComplejo(String numeroComplejo)
    {
        numeroComplejo = numeroComplejo.replace(" ", "");
        numeroComplejo = numeroComplejo.replace("i","");
        double parteReal, parteImaginaria;
        String operacion = "";
        String[] nuevoNumComplejo = null;

        if (numeroComplejo.contains("+")) {
            nuevoNumComplejo = numeroComplejo.split("\\+");
            operacion = "+";
        } else if (numeroComplejo.contains("-")) {
            nuevoNumComplejo = numeroComplejo.split("-");
            operacion = "-";
            if (nuevoNumComplejo[0].isEmpty()) {
                nuevoNumComplejo[0] = "-" + nuevoNumComplejo[1];
                nuevoNumComplejo[1] = "-" + nuevoNumComplejo[2];
            } else {
                nuevoNumComplejo[1] = "-" + nuevoNumComplejo[1];
            }
        }

        parteReal = Double.parseDouble(nuevoNumComplejo[0]);
        parteImaginaria = Double.parseDouble(nuevoNumComplejo[1]);

        return new NumeroComplejo(parteReal, parteImaginaria);
    }

    private void lecturaNumeroComplejo1()
    {
        String numeroComplejoString = lecturaNumeroComplejo1.getText();
        numeroComplejo1 = stringToNumeroComplejo(numeroComplejoString);
    }

    private void lecturaNumeroComplejo2()
    {
        String numeroCompleString = lecturaNumeroComplejo2.getText();
        numeroComplejo2 = stringToNumeroComplejo(numeroCompleString);
    }

    private void getOperacion(String operacion)
    {
        this.operacion = operacion;
    }

    private void botonResultado()
    {

    }

}
