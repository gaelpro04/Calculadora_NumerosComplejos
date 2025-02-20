public class AlmacenNumerosComplejos {

    Pila<NumeroComplejo> numerosComplejos1;
    Pila<NumeroComplejo> numerosComplejos2;

    public AlmacenNumerosComplejos()
    {
        numerosComplejos1 = new Pila<>();
        numerosComplejos2 = new Pila<>();
    }

    public void guardar(NumeroComplejo numeroComplejo1, NumeroComplejo numeroComplejo2)
    {
        numerosComplejos1.push(numeroComplejo1);
        numerosComplejos2.push(numeroComplejo2);
    }

    public void undo()
    {
        numerosComplejos1.pop();
        numerosComplejos2.pop();
    }

    public void borrar()
    {
        while (!numerosComplejos1.pilaVacia() && !numerosComplejos2.pilaVacia()) {
            numerosComplejos1.pop();
            numerosComplejos2.pop();
        }
    }
}
