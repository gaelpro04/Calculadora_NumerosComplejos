public class Pila<T> {

    private T[] elementos;
    private int tope;

    public Pila()
    {
        elementos = (T[]) new Object[10];
        int tope = -1;
    }

    public boolean pilaVacia()
    {
        return tope == -1;
    }

    public boolean pilaLlena()
    {
        return tope == elementos.length - 1;
    }

    public T peek()
    {
        return elementos[tope];
    }

    public T pop()
    {
        T object = null;

        if (pilaVacia()) {
            System.out.println("Pila vacia");
        } else {
            object = elementos[tope];
        }
        return object;
    }

    public void push(T object)
    {
        if (pilaLlena()) {
            elementos = redimensionar(tope);
        } else {
            ++tope;
            elementos[tope] = object;
        }
    }

    private T[] redimensionar(int tope)
    {
        T[] elementosRedimensionados = (T[]) new Object[(tope+1)*2];
        for (int i = 0; i < tope; i++) {
            elementosRedimensionados[i] = elementos[i];
        }
        return elementosRedimensionados;
    }


}
