import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Objects;

//Clase que modela la interfaz de una calculadora compleja
public class CalculadoraCompleja {

    private JScrollPane scrollPane;
    //Atributos lógicos(que hacen posible la resolución de los números)
    private AlmacenNumerosComplejos historial;
    private ModeloCalculadora calculadora;
    private NumeroComplejo numeroComplejo1;
    private NumeroComplejo numeroComplejo2;
    private ArrayList<String> ultimaOperacion;
    private int ultimoIndexLabel;

    //Atributos de la GUI
    private JFrame frame;
    private JPanel panelPrincipal, panelSuperior, panelCentral, panelInferior;
    private JComboBox operaciones;
    private JTextField lecturaNumeroComplejo1, lecturaNumeroComplejo2;
    private JButton botonResultado, botonUNDO, botonReiniciar;

    /**
     * Constructor preterminado donde se incializan las variables y se crea la GUI
     */
    public CalculadoraCompleja() {

        historial = new AlmacenNumerosComplejos();
        calculadora = new ModeloCalculadora();
        ultimaOperacion = new ArrayList<>();
        ultimoIndexLabel = -1;

        frame = new JFrame("Calculadora compleja");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panelPrincipal = new JPanel(new BorderLayout());
        panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        panelCentral = new JPanel();
        panelCentral.setLayout(new GridBagLayout());
        panelCentral.setBorder(new LineBorder(Color.GRAY));

        panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setOpaque(true);
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        lecturaNumeroComplejo1 = new JTextField(20);
        lecturaNumeroComplejo1.setText("Escribe un número complejo");
        lecturaNumeroComplejo1.setPreferredSize(new Dimension(100,100));
        lecturaNumeroComplejo1.setForeground(Color.GRAY);
        lecturaNumeroComplejo1.addActionListener(lectura -> lecturaNumeroComplejo1());
        lecturaNumeroComplejo1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (lecturaNumeroComplejo1.getText().equals("Escribe un número complejo")) {
                    lecturaNumeroComplejo1.setText("");
                    lecturaNumeroComplejo1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (lecturaNumeroComplejo1.getText().isEmpty()) {
                    lecturaNumeroComplejo1.setText("Escribe un número complejo");
                    lecturaNumeroComplejo1.setForeground(Color.GRAY);
                }
            }
        });

        lecturaNumeroComplejo2 = new JTextField(20);
        lecturaNumeroComplejo2.setText("Escribe un número complejo");
        lecturaNumeroComplejo2.setPreferredSize(new Dimension(100,100));
        lecturaNumeroComplejo2.setForeground(Color.GRAY);
        lecturaNumeroComplejo2.addActionListener(lectura -> lecturaNumeroComplejo2());
        lecturaNumeroComplejo2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (lecturaNumeroComplejo2.getText().equals("Escribe un número complejo")) {
                    lecturaNumeroComplejo2.setText("");
                    lecturaNumeroComplejo2.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (lecturaNumeroComplejo2.getText().isEmpty()) {
                    lecturaNumeroComplejo2.setText("Escribe un número complejo");
                    lecturaNumeroComplejo2.setForeground(Color.GRAY);
                }
            }
        });

        operaciones = new JComboBox<>(new String[]{"+","-","x","÷"});

        botonResultado = new JButton("=");
        botonResultado.setPreferredSize(new Dimension(50,50));
        botonResultado.addActionListener(lectura -> botonResultado());
        botonUNDO = new JButton("undo");
        botonUNDO.setPreferredSize(new Dimension(70,50));
        botonUNDO.addActionListener(lectura -> botonUNDO());
        botonReiniciar = new JButton("Reiniciar");
        botonReiniciar.setPreferredSize(new Dimension(90,50));
        botonReiniciar.addActionListener(lectura -> reiniciar());

        panelSuperior.add(lecturaNumeroComplejo2, SwingConstants.CENTER);
        panelSuperior.add(operaciones, SwingConstants.CENTER);
        panelSuperior.add(lecturaNumeroComplejo1, SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(5,5,5,5);

        panelCentral.add(botonResultado, gbc);
        gbc.gridx = 2;
        panelCentral.add(botonUNDO,gbc);
        gbc.gridx = 3;
        panelCentral.add(botonReiniciar, gbc);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        frame.add(panelPrincipal, BorderLayout.CENTER);
        scrollPane = new JScrollPane(panelInferior);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setSize(550, 210);
        frame.setLocationRelativeTo(null);
        frame.pack();

    }

    /**
     * Método que convierte un String a un número complejo
     * @param numeroComplejo
     * @return
     */
    private NumeroComplejo stringToNumeroComplejo(String numeroComplejo)
    {
        //Primero remplazamos algún espacio en blanco
        numeroComplejo = numeroComplejo.replace(" ", "");

        //Variables donde asignaremos las parte imaginaria y parte real.
        double parteReal, parteImaginaria;
        //Arreglo donde almacenaremos cada elemento posible a numero complejo
        String[] nuevoNumComplejo;

        //Si contiene un +, separamos los datos con eso.
        if (numeroComplejo.contains("+")) {
            //Separación
            nuevoNumComplejo = numeroComplejo.split("\\+");

            //Como ya tenemos un poco descompuesta el posible número complejo, ahora nos encargamos de quitar cualquier
            //perturbación, el primer caso donde se encuentre solamente la i(queriendo decir que es un uno como coheficiente)
            if (nuevoNumComplejo[1].equals("i")) {
                //Se convierte ese i en un 1
                nuevoNumComplejo[1] = nuevoNumComplejo[1].replace("i","1");
                //En dado caso que no lo sea, simplemente quitamos esa i para que no genere problemas al momento de quererlo
                //convertir a un número Double
            } else {
                nuevoNumComplejo[1] = nuevoNumComplejo[1].replace("i","");
            }
            //Ya por ultimo convertimos las String solamente con números a double
            parteReal = Double.parseDouble(nuevoNumComplejo[0]);
            parteImaginaria = Double.parseDouble(nuevoNumComplejo[1]);
            
            //En dado caso que contenga un menos, se dirigirá aquí
        } else if (numeroComplejo.contains("-")) {
            //Se divide el posible número complejo
            nuevoNumComplejo = numeroComplejo.split("-");
            //Si el primer elemento está vació quiere decir que la parte real era negativa(como separamos por menos, entonces
            // el valor menos de la parte real se quita y crea un elemento vacio de lado izquierdo, es decir el primer elemento)
            if (nuevoNumComplejo[0].isEmpty()) {
                //Pasamos la parte real al primer elemento y así con el segundo elemento(como separamos con menos, quiere decir
                // que la parte imaginaria también es negativo)
                nuevoNumComplejo[0] = "-" + nuevoNumComplejo[1];
                nuevoNumComplejo[1] = "-" + nuevoNumComplejo[2];

                //La misma situación donde tenemos solamente la i y remplazamos o quitamos
                if (nuevoNumComplejo[1].equals("i")) {
                    nuevoNumComplejo[1] = nuevoNumComplejo[1].replace("i","1");
                } else {
                    nuevoNumComplejo[1] = nuevoNumComplejo[1].replace("i","");
                }
                //Ya por ultimo convertimos las String solamente con números a double
                parteReal = Double.parseDouble(nuevoNumComplejo[0]);
                parteImaginaria = Double.parseDouble(nuevoNumComplejo[1]);
                
                //en dado caso que no este vacio el primer elemento, entonces la parte real es positiva
            } else {
                //Verificamos lo mismo, si solamente se encuentra la i
                if (nuevoNumComplejo[1].equals("i")) {
                    nuevoNumComplejo[1] = nuevoNumComplejo[1].replace("i","1");
                } else {
                    nuevoNumComplejo[1] = nuevoNumComplejo[1].replace("i","");
                }
                nuevoNumComplejo[1] = "-" + nuevoNumComplejo[1];

                //Ya por ultimo convertimos las String solamente con números a double
                parteReal = Double.parseDouble(nuevoNumComplejo[0]);
                parteImaginaria = Double.parseDouble(nuevoNumComplejo[1]);
            }

        } else {
            if (numeroComplejo.contains("i")) {
                numeroComplejo = numeroComplejo.replace("i","");
                parteImaginaria = Double.parseDouble(numeroComplejo);
                parteReal = 0;
            } else {
                parteReal = Double.parseDouble(numeroComplejo);
                parteImaginaria = 0;
            }
        }
        
        return new NumeroComplejo(parteReal, parteImaginaria);
    }

    /**
     * Método que lee el contenido del textField de numeroComplejo1 una vez se de enter
     */
    private void lecturaNumeroComplejo1()
    {
        //Se crea una variable tipo String donde pueda almacenar el texto que escribió el usuario
        String numeroComplejoString = lecturaNumeroComplejo1.getText();

        //Se utiliza el método que convierte Strings a numeros complejos
        numeroComplejo1 = stringToNumeroComplejo(numeroComplejoString);
    }

    /**
     * Método que lee el contenido del textField de numeroComplejo2 una vez se de enter
     */
    private void lecturaNumeroComplejo2()
    {
        String numeroCompleString = lecturaNumeroComplejo2.getText();
        numeroComplejo2 = stringToNumeroComplejo(numeroCompleString);
    }

    public void botonUNDO()
    {
        numeroComplejo1 = historial.undo();
        numeroComplejo2 = null;
        lecturaNumeroComplejo1.setText(numeroComplejo1.toString());
        lecturaNumeroComplejo1();
        lecturaNumeroComplejo2.setText("");

        Component[] componentes = panelInferior.getComponents();


        for (int i = componentes.length - 1; i >= 0; --i) {
            if (componentes[i] instanceof JLabel) {
                JLabel label = (JLabel) componentes[i];
                if (label.getText().equals(ultimaOperacion.getLast()) && i == componentes.length - 1) {
                    panelInferior.remove(i);
                    ultimoIndexLabel = i;
                    System.out.println("ajasi");
                    break;
                } else if (i == componentes.length - 1) {
                    panelInferior.remove(i);
                    panelInferior.remove(i-1);
                    ultimoIndexLabel = i-1;
                    System.out.println("buenosi");
                    break;
                }
            }
        }

        panelInferior.add(new JLabel(numeroComplejo1.toString()));
        ultimaOperacion.remove(ultimaOperacion.getLast());
        panelInferior.revalidate();
        panelInferior.repaint();
        scrollPane.repaint();
        scrollPane.revalidate();
        frame.pack();
    }

    /**
     * Método utilizado para resolver la operación
     */
    private void botonResultado()
    {
        //Al momento de darle click al botón "=" se va dirigido aquí con un lambda, que solamente resuelve la operación con
        //la clase de ModeloCalculadora y simplemente usamos sus métodos
        NumeroComplejo resultado = null;
        //Si ningún número es null, entonces es valido para hacer la operación
        if (numeroComplejo1 != null && numeroComplejo2 != null) {
            if (operaciones.getSelectedItem().equals("+")) {
                resultado = calculadora.suma(numeroComplejo1.getParteReal(), numeroComplejo1.getParteImaginaria(), numeroComplejo2.getParteReal(), numeroComplejo2.getParteImaginaria());
            } else if (operaciones.getSelectedItem().equals("-")) {
                resultado = calculadora.resta(numeroComplejo1.getParteReal(), numeroComplejo1.getParteImaginaria(), numeroComplejo2.getParteReal(), numeroComplejo2.getParteImaginaria());
            } else if (operaciones.getSelectedItem().equals("x")) {
                resultado = calculadora.multiplicacion(numeroComplejo1.getParteReal(), numeroComplejo1.getParteImaginaria(), numeroComplejo2.getParteReal(), numeroComplejo2.getParteImaginaria());
            } else if (operaciones.getSelectedItem().equals("÷")) {
                resultado = calculadora.division(numeroComplejo1.getParteReal(), numeroComplejo1.getParteImaginaria(), numeroComplejo2.getParteReal(), numeroComplejo2.getParteImaginaria());
            }
            if (ultimoIndexLabel != -1) {
                panelInferior.remove(ultimoIndexLabel);
                ultimoIndexLabel = -1;
            }

            panelInferior.add(new JLabel(numeroComplejo1 + " " + operaciones.getSelectedItem() + " " + numeroComplejo2 + " = " + resultado));
            ultimaOperacion.add(numeroComplejo1.toString() + " " + operaciones.getSelectedItem() + " " + numeroComplejo2.toString() + " = " + resultado);
            System.out.println(numeroComplejo1 + " " + operaciones.getSelectedItem() + " " + numeroComplejo2 + " = " + resultado);
            historial.guardar(numeroComplejo1, numeroComplejo2, resultado);
            panelInferior.repaint();
            panelInferior.revalidate();
            scrollPane.repaint();
            scrollPane.revalidate();
            frame.pack();
            lecturaNumeroComplejo2.setText("");
            numeroComplejo2 = null;
            lecturaNumeroComplejo1.setText(resultado.toString());
            lecturaNumeroComplejo1();
        } else {
            System.out.println("errrrrror");
        }
    }

    /**
     * Método que reinicia todos los campos para así poder hacer otra operación
     */
    private void reiniciar()
    {
        lecturaNumeroComplejo1.setEnabled(true);
        lecturaNumeroComplejo1.setText("");
        numeroComplejo1 = null;
        lecturaNumeroComplejo2.setEnabled(true);
        lecturaNumeroComplejo2.setText("");
        numeroComplejo2 = null;
        historial.borrar();
        ultimaOperacion.clear();
        ultimoIndexLabel = -1;
        panelInferior.removeAll();
        panelInferior.repaint();
        panelInferior.revalidate();
        frame.pack();
    }
}
