import  javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Clase que modela la interfaz de una calculadora compleja
public class CalculadoraCompleja {

    //Atributos lógicos(que hacen posible la resolución de los números)
    private AlmacenNumerosComplejos historial;
    private ModeloCalculadora calculadora;
    private NumeroComplejo numeroComplejo1;
    private NumeroComplejo numeroComplejo2;
    private String operacionElaborada;

    //Atributos de la GUI
    private JFrame frame;
    private JPanel panelPrincipal, panelSuperior, panelInferior, panelOperaciones, panelNumeros, panelResultados, panelHistorial;
    private JTextField lecturaNumeroComplejo1, lecturaNumeroComplejo2;
    private JLabel labelNumeroComplejo1, labelNumeroComplejo2, labelResultadoEnunciado, labelResultado, labelOperaciones;
    private JButton botonResultado;
    private ArrayList<JButton> botonesOperaciones;

    /**
     * Constructor preterminado donde se incializan las variables y se crea la GUI
     */
    public CalculadoraCompleja() {

        operacionElaborada = "";
        calculadora = new ModeloCalculadora();
        historial = new AlmacenNumerosComplejos();
        frame = new JFrame("Calculadora Compleja");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Panel superior(número complejo1, número complejo2 y resultado)
        panelSuperior = new JPanel(new BorderLayout());
        panelNumeros = new JPanel(new GridBagLayout());
        panelNumeros.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelResultados = new JPanel(new GridBagLayout());
        panelHistorial = new JPanel();
        panelHistorial.setLayout(new GridLayout(100, 1));
        panelHistorial.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelHistorial.add(new JLabel("Historial"));

        //Como Intellij no nos da mucha flexibilidad en cuestión de guis, se utiliza de gridBagConstraints, que nos deja
        //manipular un poco más los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 5);

        //Label y TextField del número complejo 1
        labelNumeroComplejo1 = new JLabel("Número complejo 1", SwingConstants.CENTER);
        labelNumeroComplejo1.setPreferredSize(new Dimension(250, 15));
        lecturaNumeroComplejo1 = new JTextField();
        lecturaNumeroComplejo1.setPreferredSize(new Dimension(250, 70));
        //Dirijimos la lectura del textField al método de lectura
        lecturaNumeroComplejo1.addActionListener(lectura -> lecturaNumeroComplejo1());

        //MOdificación de localización de los componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelNumeros.add(labelNumeroComplejo1, gbc);
        gbc.gridy = 1;
        panelNumeros.add(lecturaNumeroComplejo1, gbc);

        //Label y textField de la lectura del segundo nombre
        labelNumeroComplejo2 = new JLabel("Número complejo 2", SwingConstants.CENTER);
        labelNumeroComplejo2.setPreferredSize(new Dimension(250, 10));
        lecturaNumeroComplejo2 = new JTextField();
        lecturaNumeroComplejo2.setPreferredSize(new Dimension(250, 70));
        lecturaNumeroComplejo2.addActionListener(lectura -> lecturaNumeroComplejo2());

        //Acomodo de componentes
        gbc.gridy = 2;
        panelNumeros.add(labelNumeroComplejo2, gbc);
        gbc.gridy = 3;
        panelNumeros.add(lecturaNumeroComplejo2, gbc);

        //Label(enunciado) y el labelResultado(donde se mostrará el resultado)
        labelResultadoEnunciado = new JLabel("Resultado", SwingConstants.CENTER);
        labelResultadoEnunciado.setPreferredSize(new Dimension(220, 10));
        labelResultado = new JLabel("", SwingConstants.CENTER);
        labelResultado.setOpaque(true);
        labelResultado.setBackground(Color.WHITE);
        labelResultado.setPreferredSize(new Dimension(220, 80));

        //Botón que mostrará en el labelResultado el resultado
        botonResultado = new JButton("=");
        botonResultado.setPreferredSize(new Dimension(50, 30));
        botonResultado.setFocusPainted(false);
        botonResultado.addActionListener(lectura -> botonResultado());

        //Acomodo de componentes de lado derecho
        gbc.gridy = 0;
        panelResultados.add(labelResultadoEnunciado, gbc);
        gbc.gridy = 1;
        panelResultados.add(labelResultado, gbc);
        gbc.gridy = 2;
        panelResultados.add(botonResultado, gbc);
        panelSuperior.add(panelResultados, BorderLayout.EAST);
        panelSuperior.add(panelNumeros, BorderLayout.WEST);

        //Panel inferior(operaciones)
        panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        //Botones de operaciones, donde se utiliza de un arreglo para asignar el contenido de los botones mas fácil
        String[] operaciones = {"+", "-", "x", "÷"};
        botonesOperaciones = new ArrayList<>();
        panelOperaciones = new JPanel(new FlowLayout(FlowLayout.CENTER));
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

        //Botón reinicio, donde con el addActionListener, se va dirigido al método reiniciar
        JButton botonReiniciar = new JButton("Reiniciar");
        botonReiniciar.addActionListener(lectura -> reiniciar());
        panelOperaciones.add(botonReiniciar);

        labelOperaciones = new JLabel("OPERACIONES", SwingConstants.CENTER);
        panelInferior.add(labelOperaciones, BorderLayout.NORTH);
        panelInferior.add(panelOperaciones, BorderLayout.SOUTH);

        //Agregar los paneles al frame
        frame.add(panelHistorial, BorderLayout.EAST);
        frame.add(panelSuperior, BorderLayout.NORTH);
        frame.add(panelInferior, BorderLayout.SOUTH);
        frame.setSize(700, 300);
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
                //en dado caso que no este vacio el primer elemento, entonces la parte real es positiva
            } else {
                //Verificamos lo mismo, si solamente se encuentra la i
                if (nuevoNumComplejo[1].equals("i")) {
                    nuevoNumComplejo[1] = nuevoNumComplejo[1].replace("i","1");
                } else {
                    nuevoNumComplejo[1] = nuevoNumComplejo[1].replace("i","");
                }
                nuevoNumComplejo[1] = "-" + nuevoNumComplejo[1];
            }
            //Si no tiene ninguna operación simplemente no es valido y se entrea null
        } else {
            return null;
        }

        //Ya por ultimo convertimos las String solamente con números a double
        parteReal = Double.parseDouble(nuevoNumComplejo[0]);
        parteImaginaria = Double.parseDouble(nuevoNumComplejo[1]);

        return new NumeroComplejo(parteReal, parteImaginaria);
    }

    /**
     * Método que lee el contenido del textField de numeroComplejo1 una vez se de enter
     */
    private void lecturaNumeroComplejo1()
    {
        //Se desactiva la faunción del textField, para que no pueda ingresar nada más y no ocasione errores
        lecturaNumeroComplejo1.setEnabled(false);

        //Se crea una variable tipo String donde pueda almacenar el texto que escribió el usuario
        String numeroComplejoString = lecturaNumeroComplejo1.getText();

        //Se utiliza el método que convierte Strings a numeros complejos
        numeroComplejo1 = stringToNumeroComplejo(numeroComplejoString);

        //En dado caso que el número complejo sea null(Muy probablemente ingreso algo invalido) simplemente
        //se imprime un texto nuevo en el label de numerocomplejo1 que no es invalido, por ultimo se reinicia
        if (numeroComplejo1 == null) {
            labelNumeroComplejo1.setText("Debes ingresar un número valido");
            reiniciar();
        }
    }

    /**
     * Método que lee el contenido del textField de numeroComplejo2 una vez se de enter
     */
    private void lecturaNumeroComplejo2()
    {
        //Al igual que con el método pasado, es básicamente lo mismo pero simplemente para el otro número complejo
        lecturaNumeroComplejo2.setEnabled(false);
        String numeroCompleString = lecturaNumeroComplejo2.getText();
        numeroComplejo2 = stringToNumeroComplejo(numeroCompleString);

        if (numeroComplejo2 == null) {
            labelNumeroComplejo2.setText("Debes ingresar un número valido");
            reiniciar();
        }
    }

    /**
     * Método para seleccionar la operación elegida por el usuario
     * @param operacion
     */
    private void getOperacion(String operacion)
    {
        //Simplemente el atributo de operación se le asigna la entrada del usuario(que en este caso es el botón que eligió
        // por lo tanto los botones van dirigidos aquí)
        this.operacionElaborada = operacion;
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
            if (operacionElaborada.equals("+")) {
                resultado = calculadora.suma(numeroComplejo1.getParteReal(), numeroComplejo1.getParteImaginaria(), numeroComplejo2.getParteReal(), numeroComplejo2.getParteImaginaria());
            } else if (operacionElaborada.equals("-")) {
                resultado = calculadora.resta(numeroComplejo1.getParteReal(), numeroComplejo1.getParteImaginaria(), numeroComplejo2.getParteReal(), numeroComplejo2.getParteImaginaria());
            } else if (operacionElaborada.equals("x")) {
                resultado = calculadora.multiplicacion(numeroComplejo1.getParteReal(), numeroComplejo1.getParteImaginaria(), numeroComplejo2.getParteReal(), numeroComplejo2.getParteImaginaria());
            } else if (operacionElaborada.equals("÷")) {
                resultado = calculadora.division(numeroComplejo1.getParteReal(), numeroComplejo1.getParteImaginaria(), numeroComplejo2.getParteReal(), numeroComplejo2.getParteImaginaria());
            } else if (operacionElaborada.isEmpty()) {
                labelResultadoEnunciado.setText("Ingresa una operación valida");
            }
            labelResultado.setText(String.valueOf(resultado));

            panelHistorial.add(new JLabel(lecturaNumeroComplejo1.getText() + " " + operacionElaborada + " " + lecturaNumeroComplejo2.getText()), SwingConstants.CENTER);
            panelHistorial.revalidate();
            panelHistorial.repaint();
            historial.guardar(stringToNumeroComplejo(lecturaNumeroComplejo1.getText()), stringToNumeroComplejo(lecturaNumeroComplejo2.getText()));

            //En dado caso que se null alguno simplemente se imprime una leyenda en el labelResultado enunciado que no es valido o
            //que ingrese uns numeros validos
        } else {
            labelResultadoEnunciado.setText("Ingresa los números complejos");
            //Reiniciamos la operacionElaborada para que al momento que verdaderamente ingrese números validos, y no ingrese una operación
            //no imprima un valor no esperado.
            operacionElaborada = "";
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
        labelResultado.setText("");
        operacionElaborada = "";
    }
}
