import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Objects;

//Clase que modela la interfaz de una calculadora compleja
public class CalculadoraCompleja {


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
    private JScrollPane scrollPane;

    /**
     * Constructor preterminado donde se incializan las variables y se crea la GUI
     */
    public CalculadoraCompleja() {

        //Inicialización atributos lógicos
        historial = new AlmacenNumerosComplejos();
        calculadora = new ModeloCalculadora();
        ultimaOperacion = new ArrayList<>();
        ultimoIndexLabel = -1;

        //Inicialización de frame, asignamos el layout y asignamos que se hará cuando se cierra la ventana(dejará de
        // ejecutarse el programa)
        frame = new JFrame("Calculadora compleja");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Inicialización de paneles y layouts
        panelPrincipal = new JPanel(new BorderLayout());
        panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        panelCentral = new JPanel();
        panelCentral.setLayout(new GridBagLayout());
        panelCentral.setBorder(new LineBorder(Color.GRAY));

        //Inicialización de panelInferior, además de asignarle el ayout, también especificamos las dimensiones máximas
        //que irán de acuerdo al tamaño dinamico que se este agregando por el historial de operaciones
        panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setOpaque(true);
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        //Se inicializa el textField, donde especificamos color, tamaño, y un actionListener, donde utilizando un lambda
        //Redirigimos al método que hace la lectura
        lecturaNumeroComplejo1 = new JTextField(20);
        lecturaNumeroComplejo1.setText("Escribe un número complejo");
        lecturaNumeroComplejo1.setPreferredSize(new Dimension(100,100));
        lecturaNumeroComplejo1.setForeground(Color.GRAY);
        lecturaNumeroComplejo1.addActionListener(lectura -> lecturaNumeroComplejo1());

        //Para no utilizar labels para especificar donde se harán las operaciones, en los mismos textfields se puede
        //hacer, con el método focusListener, que al momento que el usuario diriga su mouse al campo, tendrá una reacción
        //que en este caso si tiene el focus, borrará la información y el texto se hará negro para que el usuario pueda
        //escribir. El otro es cuando pierda el focus, en este caso si no hay ningún contenido en el texto se escribe
        //la indicación de escribir un número complejo.
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

        //Es prácticamente lo mismo que el textField anterior solamente para el segundo número complejo
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

        //Creamos el componente de ventanta donde hay posibilidad de varias opciones, en este caso de las operaciones
        operaciones = new JComboBox<>(new String[]{"+","-","x","÷"});

        //Sección de botones, aquí basicamente asignamos nombres, dimensiones y actionListeners capaces de redirigir a
        //su método correspondiente
        botonResultado = new JButton("=");
        botonResultado.setPreferredSize(new Dimension(50,50));
        botonResultado.addActionListener(lectura -> botonResultado());
        botonUNDO = new JButton("undo");
        botonUNDO.setPreferredSize(new Dimension(70,50));
        botonUNDO.addActionListener(lectura -> botonUNDO());
        botonReiniciar = new JButton("Reiniciar");
        botonReiniciar.setPreferredSize(new Dimension(90,50));
        botonReiniciar.addActionListener(lectura -> reiniciar());

        //Agregamos los componentes de lectura al panel superior centrados
        panelSuperior.add(lecturaNumeroComplejo2, SwingConstants.CENTER);
        panelSuperior.add(operaciones, SwingConstants.CENTER);
        panelSuperior.add(lecturaNumeroComplejo1, SwingConstants.CENTER);

        //Para un acomodo más personalizaod, se utiliza el layout GridBagLayout, junto a la clase GridBagConstraints
        //para especificar la posición
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(5,5,5,5);

        //Se agregan los botones. Además se especifica este layout a los botones ya que estos suelen desordenar todo el
        //frame
        panelCentral.add(botonResultado, gbc);
        gbc.gridx = 2;
        panelCentral.add(botonUNDO,gbc);
        gbc.gridx = 3;
        panelCentral.add(botonReiniciar, gbc);

        //El ultimo panel metemos a los paneles anteriores
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);


        //Y por ultimo agregamos el panel principal al frame, además de crear un scrollpanel para el panel inferior, esto
        //ya que se ncesita estar constantemente viendo todo el historial de operaciones. Ahora si por ultimo especificamos
        //tamaño, visibilidad y acomodo del frame.
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
                if (nuevoNumComplejo.length == 2 && nuevoNumComplejo[1].equals("i")) {
                    nuevoNumComplejo[1] = "-" + nuevoNumComplejo[1];
                    nuevoNumComplejo[0] = "0";
                } else if (nuevoNumComplejo.length == 2 && nuevoNumComplejo[1].contains("i")) {
                    nuevoNumComplejo[1] = nuevoNumComplejo[1].replace("i","");
                    nuevoNumComplejo[1] = "-" + nuevoNumComplejo[1];
                    nuevoNumComplejo[0] = "0";
                } else {
                    if (nuevoNumComplejo.length == 2) {
                        nuevoNumComplejo[0] = "-" + nuevoNumComplejo[1];
                        nuevoNumComplejo[1] = "0";
                    } else {
                        nuevoNumComplejo[0] = "-" + nuevoNumComplejo[1];
                        nuevoNumComplejo[1] = "-" + nuevoNumComplejo[2];
                    }
                }

                //La misma situación donde tenemos solamente la i y remplazamos o quitamos
                if (nuevoNumComplejo[1].equals("-i")) {
                    nuevoNumComplejo[1] = nuevoNumComplejo[1].replace("-i","-1");
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
            if (numeroComplejo.equals("i")) {
                numeroComplejo = numeroComplejo.replace("i","1");
                parteImaginaria = Double.parseDouble(numeroComplejo);
                parteReal = 0;
            }else if (numeroComplejo.contains("i")) {
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

    /**
     * Método capaz de regresar a la operación anterior en el historial
     */
    public void botonUNDO()
    {
        //Tomamos el ultimo resultado en el número complejo 1, ya que este será nuestro nuevo numero complejo
        numeroComplejo1 = historial.undo();

        //Reseteamos el numero complejo 2 para que no haya ningún problema a la hora de regresar y que el usuario pique
        //por error "="
        numeroComplejo2 = null;

        //Metemos en el primer textfield el número complejo sacado del historial
        lecturaNumeroComplejo1.setText(numeroComplejo1.toString());

        //Hacemos la lectura
        lecturaNumeroComplejo1();

        //Reseteamos el segundo textfield al igual como se hizo en el mismo numero complejo 2
        lecturaNumeroComplejo2.setText("");

        //Ciclo para buscar la ultima operación y eliminarla
        Component[] componentes = panelInferior.getComponents();
        for (int i = componentes.length - 1; i >= 0; --i) {

            //cada componente la instanciamos como JLabel(no debe ocasionar problemas ya que todo lo que hemos metido
            // al panelInferior son solo etiquetas)
            if (componentes[i] instanceof JLabel) {
                JLabel label = (JLabel) componentes[i];

                //Si el texto sacado del label(ultima operación) es igual a la ultima operación y es el ultimo elemento del panel
                //entonces dse elimina y el ultimo index toma el valor del indice del ciclo
                if (label.getText().equals(ultimaOperacion.getLast()) && i == componentes.length - 1) {
                    panelInferior.remove(i);
                    ultimoIndexLabel = i;
                    break;

                    //En dado caso que no se cumpla quiere decir que solo hayun número complejo y solo se elimina y la operación anterior
                } else if (i == componentes.length - 1) {
                    panelInferior.remove(i);
                    panelInferior.remove(i-1);
                    ultimoIndexLabel = i-1;
                    break;
                }
            }
        }

        //Metemos de nuevo el resultado sacado del undo de historial, para al momento que se quiera usar undo el resultado
        //que saquemos sea este mismo que metimos
        historial.devolverResultado(numeroComplejo1);

        //Metemos este valor al panelInferior para hacer ilusión de que es el dato acumulado
        panelInferior.add(new JLabel(numeroComplejo1.toString()));

        //Se borrá la ultima operación de la colección
        ultimaOperacion.remove(ultimaOperacion.getLast());

        //Se actualiza el panel inferior y frame
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

            //Si el ultimo index label, es igual a menos 1 quiere decir que no se ha usado UNDO
            //dado caso lo contrario, quiere decir que ya se uso, entoncesl ese residuo de valor acmulado que se queda se
            //debe quitar para que se pueda poner la nueva operación en el historial
            if (ultimoIndexLabel != -1) {
                panelInferior.remove(ultimoIndexLabel);
                ultimoIndexLabel = -1;
            }

            //Se agrega la ultima operación al panel, además tambien en la colección de historial de operaciones.
            panelInferior.add(new JLabel(numeroComplejo1 + " " + operaciones.getSelectedItem() + " " + numeroComplejo2 + " = " + resultado));
            ultimaOperacion.add(numeroComplejo1.toString() + " " + operaciones.getSelectedItem() + " " + numeroComplejo2.toString() + " = " + resultado);

            //Se guarda en el historial(utilizando pilas) los números complejos utilizados, además del resultado.
            historial.guardar(numeroComplejo1, numeroComplejo2, resultado);

            //Se actualiza el panel y el frame
            panelInferior.repaint();
            panelInferior.revalidate();
            scrollPane.repaint();
            scrollPane.revalidate();
            frame.pack();

            //Una vez elaborada la operación se elimina el contenido del segundo textfield y el numero complejo 2 se hace
            //nulo. Ahora el text field 1 se le introduce el resultado(esto para que pueda hacerce el acumulo)
            lecturaNumeroComplejo2.setText("");
            numeroComplejo2 = null;
            lecturaNumeroComplejo1.setText(resultado.toString());

            //Ya por ultimo se lee
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

        //Simplemente se reinicia todo.
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
