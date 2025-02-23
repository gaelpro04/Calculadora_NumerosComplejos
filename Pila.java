
//Clase que modela un pila
public class Pila<T> {

    //Método para la pila
    private T[] elementos;
    private int tope;

    /**
     * Constructor preterminado de la pila
     */
    public Pila()
    {
        elementos = (T[]) new Object[10];
        this.tope = -1;
    }

    /**
     * Método para saber si una pila está vacia
     * @return
     */
    public boolean pilaVacia()
    {
        return tope == -1;
    }

    /**
     * Método para saber si una pila está llena
     * @return
     */
    public boolean pilaLlena()
    {
        return tope == elementos.length - 1;
    }

    /**
     * Método para saber cual será el siguiente elemento que se sacará
     * @return
     */
    public T peek()
    {
        return elementos[tope];
    }

    /**
     * Método para sacar un elemento de la pila
     * @return
     */
    public T pop()
    {
        T object = null;

        if (pilaVacia()) {
            System.out.println("Pila vacia");
        } else {
            object = elementos[tope];
            --tope;
        }
        return object;
    }

    /**
     * Método para meter un elemento a la pila
     * @param object
     */
    public void push(T object)
    {
        if (pilaLlena()) {
            elementos = redimensionar(tope);
        } else {
            ++tope;
            elementos[tope] = object;
        }
    }

    /**
     * Método para redimensionar la pila en dado caso que llegue a su valor máximo. Esto para que sea "infinito"
     * @param tope
     * @return
     */
    private T[] redimensionar(int tope)
    {
        T[] elementosRedimensionados = (T[]) new Object[(tope+1)*2];
        for (int i = 0; i <= tope; i++) {
            elementosRedimensionados[i] = elementos[i];
        }
        return elementosRedimensionados;
    }


}
