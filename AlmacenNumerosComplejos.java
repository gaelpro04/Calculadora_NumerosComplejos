public class AlmacenNumerosComplejos {

    Pila<NumeroComplejo> numerosComplejos1;
    Pila<NumeroComplejo> numerosComplejos2;
    Pila<NumeroComplejo> resultados;

    public AlmacenNumerosComplejos()
    {
        numerosComplejos1 = new Pila<>();
        numerosComplejos2 = new Pila<>();
        resultados = new Pila<>();
    }

    public void guardar(NumeroComplejo numeroComplejo1, NumeroComplejo numeroComplejo2, NumeroComplejo resultado)
    {
        numerosComplejos1.push(numeroComplejo1);
        numerosComplejos2.push(numeroComplejo2);
        resultados.push(resultado);
    }

    public NumeroComplejo undo()
    {
        numerosComplejos2.pop();
        resultados.pop();
        return numerosComplejos1.pop();
    }

    public void borrar()
    {
        while (!numerosComplejos1.pilaVacia() || !numerosComplejos2.pilaVacia() || !resultados.pilaVacia()) {
            numerosComplejos1.pop();
            numerosComplejos2.pop();
            resultados.pop();
        }
    }
}
