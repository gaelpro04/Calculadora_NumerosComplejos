
//Clase que modela las 4 operaciones básicas de números complejos
//como métodos
public class ModeloCalculadora {

    /**
     * Suma de dos numeros complejos
     * @return
     */
    public NumeroComplejo suma(double parteReal, double parteImaginaria, double parteReal1, double parteImaginaria1)
    {
        //Suma
        double ansReal = parteReal + parteReal1;
        double ansImaginaria  = parteImaginaria + parteImaginaria1;

        return new NumeroComplejo(ansReal, ansImaginaria);
    }

    /**
     * Resta de un número complejo
     * @return
     */
    public NumeroComplejo resta(double parteReal, double parteImaginaria, double parteReal1, double parteImaginaria1)
    {
        //Resta
        double ansReal = parteReal - parteReal1;
        double ansImaginaria = parteImaginaria - parteImaginaria1;

        return new NumeroComplejo(ansReal, ansImaginaria);
    }

    /**
     * Método de multiplicación para número complejos
     * @return
     */
    public NumeroComplejo multiplicacion(double parteReal, double parteImaginaria, double parteReal1, double parteImaginaria1)
    {
        //Multiplicación
        double ansReal = (parteReal * parteReal1 - parteImaginaria * parteImaginaria1);
        double ansImaginaria = (parteReal * parteImaginaria1 + parteImaginaria * parteReal1);

        return new NumeroComplejo(ansReal, ansImaginaria);
    }

    /**
     * Método para división de numeros complejos
     * @return
     */
    public NumeroComplejo division(double parteReal, double parteImaginaria, double parteReal1, double parteImaginaria1)
    {
        //División
        double ansReal = ((parteReal * parteReal1) + (parteImaginaria * parteImaginaria1)) / ((parteReal1 * parteReal1) + (parteImaginaria1 * parteImaginaria1));
        double ansImaginaria = ((parteImaginaria * parteReal1) - (parteReal * parteImaginaria1)) / ((parteReal1 * parteReal1) + (parteImaginaria1 * parteImaginaria1));

        return new NumeroComplejo(ansReal, ansImaginaria);
    }
}
