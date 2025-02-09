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

    private JFrame frame;
    private JPanel panelPrincipal, panelSuperior, panelInferior, panelOperaciones, panelNumeros, panelResultados;
    private JTextField lecturaNumeroComplejo1, lecturaNumeroComplejo2;
    private JLabel labelNumeroComplejo1, labelNumeroComplejo2, labelResultadoEnunciado, labelResultado;
    private JButton botonResultado;
    private ArrayList<JButton> botonesOperaciones;

    public CalculadoraCompleja() {

        calculadora = new ModeloCalculadora();
        frame = new JFrame("Calculadora Compleja");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        // Panel superior (Número complejo 1, Número complejo 2 y Resultado)
        panelSuperior = new JPanel(new BorderLayout());
        panelNumeros = new JPanel(new GridBagLayout());
        panelNumeros.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelResultados = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 5); // Espaciado entre elementos

        // Etiqueta y campo de Número Complejo 1
        labelNumeroComplejo1 = new JLabel("Número complejo 1", SwingConstants.CENTER);
        labelNumeroComplejo1.setPreferredSize(new Dimension(250, 15)); // Tamaño más pequeño
        lecturaNumeroComplejo1 = new JTextField();
        lecturaNumeroComplejo1.setPreferredSize(new Dimension(250, 70));
        lecturaNumeroComplejo1.addActionListener(lectura -> lecturaNumeroComplejo1());

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelNumeros.add(labelNumeroComplejo1, gbc);

        gbc.gridy = 1;
        panelNumeros.add(lecturaNumeroComplejo1, gbc);

        // Etiqueta y campo de Número Complejo 2
        labelNumeroComplejo2 = new JLabel("Número complejo 2", SwingConstants.CENTER);
        labelNumeroComplejo2.setPreferredSize(new Dimension(250, 10)); // Tamaño más pequeño
        lecturaNumeroComplejo2 = new JTextField();
        lecturaNumeroComplejo2.setPreferredSize(new Dimension(250, 70));
        lecturaNumeroComplejo2.addActionListener(lectura -> lecturaNumeroComplejo2());

        gbc.gridy = 2;
        panelNumeros.add(labelNumeroComplejo2, gbc);

        gbc.gridy = 3;
        panelNumeros.add(lecturaNumeroComplejo2, gbc);

        // Resultado
        labelResultadoEnunciado = new JLabel("Resultado", SwingConstants.CENTER);
        labelResultadoEnunciado.setPreferredSize(new Dimension(220, 10));
        labelResultado = new JLabel("0", SwingConstants.CENTER);
        labelResultado.setOpaque(true);
        labelResultado.setBackground(Color.WHITE);
        labelResultado.setPreferredSize(new Dimension(220, 80));

        botonResultado = new JButton("=");
        botonResultado.setPreferredSize(new Dimension(50, 30));
        botonResultado.setFocusPainted(false);
        botonResultado.addActionListener(lectura -> botonResultado());

        gbc.gridy = 0;
        panelResultados.add(labelResultadoEnunciado, gbc);

        gbc.gridy = 1;
        panelResultados.add(labelResultado, gbc);

        gbc.gridy = 2;
        panelResultados.add(botonResultado, gbc);

        panelSuperior.add(panelResultados, BorderLayout.EAST);
        panelSuperior.add(panelNumeros, BorderLayout.WEST);

        // Panel inferior (Operaciones y botón de resultado)
        panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelOperaciones = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Botones de operaciones
        String[] operaciones = {"+", "-", "x", "÷"};
        botonesOperaciones = new ArrayList<>();
        int i = 0;
        for (String operacion : operaciones) {
            JButton boton = new JButton(operacion);
            operacion = operaciones[i];
            String finalOperacion = operacion;
            boton.addActionListener(lectura -> getOperacion(finalOperacion));
            panelOperaciones.add(boton);
            botonesOperaciones.add(boton);
            ++i;
        }

        JButton botonReiniciar = new JButton("Reiniciar");
        botonReiniciar.addActionListener(lectura -> reiniciar());
        panelOperaciones.add(botonReiniciar);

        panelInferior.add(new JLabel("OPERACIONES", SwingConstants.CENTER), BorderLayout.NORTH);
        panelInferior.add(panelOperaciones, BorderLayout.CENTER);

        // Agregar paneles al frame
        frame.add(panelSuperior, BorderLayout.NORTH);
        frame.add(panelInferior, BorderLayout.SOUTH);

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
        lecturaNumeroComplejo1.setEnabled(false);
        String numeroComplejoString = lecturaNumeroComplejo1.getText();
        String numeroTest = numeroComplejoString;

        numeroComplejo1 = stringToNumeroComplejo(numeroComplejoString);
    }

    private void lecturaNumeroComplejo2()
    {
        lecturaNumeroComplejo2.setEnabled(false);
        String numeroCompleString = lecturaNumeroComplejo2.getText();
        numeroComplejo2 = stringToNumeroComplejo(numeroCompleString);
    }

    private void getOperacion(String operacion)
    {
        this.operacion = operacion;
    }

    private void botonResultado()
    {
        NumeroComplejo resultado = null;
        if (operacion.equals("+")) {
            resultado = calculadora.suma(numeroComplejo1.getParteReal(), numeroComplejo1.getParteImaginaria(), numeroComplejo2.getParteReal(), numeroComplejo2.getParteImaginaria());
        } else if (operacion.equals("-")) {
            resultado = calculadora.resta(numeroComplejo1.getParteReal(), numeroComplejo1.getParteImaginaria(), numeroComplejo2.getParteReal(), numeroComplejo2.getParteImaginaria());
        } else if (operacion.equals("x")) {
            resultado = calculadora.multiplicacion(numeroComplejo1.getParteReal(), numeroComplejo1.getParteImaginaria(), numeroComplejo2.getParteReal(), numeroComplejo2.getParteImaginaria());
        } else if (operacion.equals("÷")) {
            resultado = calculadora.division(numeroComplejo1.getParteReal(), numeroComplejo1.getParteImaginaria(), numeroComplejo2.getParteReal(), numeroComplejo2.getParteImaginaria());
        }
        labelResultado.setText(String.valueOf(resultado));
    }

    private void reiniciar()
    {
        lecturaNumeroComplejo1.setEnabled(true);
        lecturaNumeroComplejo1.setText("");
        lecturaNumeroComplejo2.setEnabled(true);
        lecturaNumeroComplejo2.setText("");
        labelResultado.setText("");
        operacion = "";
    }

}
