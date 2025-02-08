
//Clase que modela un número complejo, desde su parte real
//y su parte imaginaria
public class NumeroComplejo {

    //Atributos de la clase
    private double parteReal;
    private double parteImaginaria;

    /**
     * Constructor preterminado que le da valores al número complejo
     * @param parteReal
     * @param parteImaginaria
     */
    public NumeroComplejo(double parteReal, double parteImaginaria)
    {
        this.parteReal = parteReal;
        this.parteImaginaria = parteImaginaria;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Getters y setters de la clase
    public double getParteReal()
    {
        return parteReal;
    }

    public void setParteReal(double parteReal)
    {
        this.parteReal = parteReal;
    }

    public double getParteImaginaria()
    {
        return parteImaginaria;
    }

    public void setParteImaginaria(double parteImaginaria)
    {
        this.parteImaginaria = parteImaginaria;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Método para obtener la representación de la clase en String
     * @return
     */
    public String toString()
    {
        if (parteImaginaria < 0) {
            return parteReal + " " + parteImaginaria + "i";
        } else if (parteImaginaria == 0) {
            return parteReal + " ";

        } else if (parteReal == 0) {
            return parteImaginaria + "i";
        }
        return parteReal + " + " + parteImaginaria + "i";

    }
}
