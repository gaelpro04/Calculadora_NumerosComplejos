import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ModeloCalculadora simon = new ModeloCalculadora();
        String numeroCom1, numeroComp2;
        String operación;
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingresa el número complejo 1 de tipo (a+bi)");
        numeroCom1 = sc.nextLine();

        System.out.println(leerNumero(numeroCom1));

    }

    //a+bi
    public static NumeroComplejo leerNumero(String numeroComplejo)
    {
        numeroComplejo = numeroComplejo.replace(" ", "");
        numeroComplejo = numeroComplejo.replace("i","");
        double parteReal, parteImaginaria;
        String operacion = "";
        String[] nuevoNumComplejo = null;

        if (numeroComplejo.contains("+")) {
            nuevoNumComplejo = numeroComplejo.split("\\+");
            operacion = "+";
        } else if (numeroComplejo.contains("-")) {
            nuevoNumComplejo = numeroComplejo.split("-");
            operacion = "-";
            if (nuevoNumComplejo[0].isEmpty()) {
                nuevoNumComplejo[0] = "-" + nuevoNumComplejo[1];
                nuevoNumComplejo[1] = "-" + nuevoNumComplejo[2];
            } else {
                nuevoNumComplejo[1] = "-" + nuevoNumComplejo[1];
            }
        }

        parteReal = Double.parseDouble(nuevoNumComplejo[0]);
        parteImaginaria = Double.parseDouble(nuevoNumComplejo[1]);

        return new NumeroComplejo(parteReal, parteImaginaria);
    }
}
