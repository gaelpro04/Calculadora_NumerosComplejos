
//Clase que modela un almacen de numeros complejos y su resultado correspondiente(se utiliza pilas)
public class AlmacenNumerosComplejos {

    //Atributos de la clase
    private Pila<NumeroComplejo> numerosComplejos1;
    private Pila<NumeroComplejo> numerosComplejos2;
    private Pila<NumeroComplejo> resultados;

    /**
     * Constructor preterminado
     */
    public AlmacenNumerosComplejos()
    {
        numerosComplejos1 = new Pila<>();
        numerosComplejos2 = new Pila<>();
        resultados = new Pila<>();
    }

    /**
     * Método para guardar en las pilas los números correspondientes en paralelo
     * @param numeroComplejo1
     * @param numeroComplejo2
     * @param resultado
     */
    public void guardar(NumeroComplejo numeroComplejo1, NumeroComplejo numeroComplejo2, NumeroComplejo resultado)
    {
        numerosComplejos1.push(numeroComplejo1);
        numerosComplejos2.push(numeroComplejo2);
        resultados.push(resultado);
    }

    /**
     * Método para meter un resultado a la pila
     * @param resultado
     */
    public void devolverResultado(NumeroComplejo resultado)
    {
        resultados.push(resultado);
    }

    /**
     * Método para sacar el ultimo resultado guardado
     * @return
     */
    public NumeroComplejo undoResultado()
    {
        return resultados.pop();
    }

    /**
     * Método para sacar el ultimo número complejo 1
     * @return
     */
    public NumeroComplejo undoNumeroComplejo1()
    {
        return numerosComplejos1.pop();
    }

    /**
     * Método para sacar el ultimo número complejo 2
     * @return
     */
    public NumeroComplejo undoNumeroComplejo2()
    {
        return numerosComplejos2.pop();
    }

    /**
     * Método para borrar todo lo acumulado en la pila
     */
    public void borrar()
    {
        while (!numerosComplejos1.pilaVacia() || !numerosComplejos2.pilaVacia() || !resultados.pilaVacia()) {
            numerosComplejos1.pop();
            numerosComplejos2.pop();
            resultados.pop();
        }
    }
}
