import Cifrado.Cesar;
import Cifrado.Vigenere;
import Interfaz.VentanaCifrado;

import javax.swing.*;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            mostrarAyuda();
            return;
        }

        String comando = args[0];
        comando = comando.toLowerCase();

        if (comando.equals("interfazgrafica")){
            javax.swing.SwingUtilities.invokeLater(() -> new VentanaCifrado().setVisible(true));
            return;
        }

        if (args.length < 2) {
            mostrarAyuda();
            return;
        }

        String mensaje = args[1];

        Cesar maquinaCesar = new Cesar();
        Vigenere maquinaVigenere = new Vigenere();

        switch (comando) {

            case "cifradocesar":
                if (validarArgs(args, 3)) {
                    int llave = parseIntSeguro(args[2]);
                    System.out.println("Resultado: " + maquinaCesar.cifrar(mensaje, llave));
                }
                break;

            case "descifradocesar":
                if (validarArgs(args, 3)) {
                    int llave = parseIntSeguro(args[2]);
                    System.out.println("Resultado: " + maquinaCesar.descifrar(mensaje, llave));
                }
                break;

            case "fuerzacesar":
                System.out.println("=== Probando todas las combinaciones ===");
                List<String> resultados = maquinaCesar.descifrarFuerzaBruta(mensaje);
                resultados.forEach(System.out::println);
                break;

            case "cifradovigenere":
                if (validarArgs(args, 3)) {
                    System.out.println("Resultado: " + maquinaVigenere.cifrar(mensaje, args[2]));
                }
                break;

            case "descifradovigenere":
                if (validarArgs(args, 3)) {
                    System.out.println("Resultado: " + maquinaVigenere.descifrar(mensaje, args[2]));
                }
                break;

            case "fuerzavigenere":
                System.out.println("=== Probando las 100 primeras claves del rockyou ===");
                List<String> resultados2 = maquinaVigenere.descifrarFuerzaBruta(mensaje, Path.of("Data/rockyou.txt"), 100);
                resultados2.forEach(System.out::println);
                break;

            default:
                System.err.println("Error: Comando '" + comando + "' no reconocido.");
                mostrarAyuda();
        }
    }

    private static boolean validarArgs(String[] args, int requeridos) {
        if (args.length < requeridos) {
            // Mensaje de error ajustado para ser más claro
            System.err.println("Error: Este comando requiere argumentos adicionales (llave o clave).");
            return false;
        }
        return true;
    }

    private static int parseIntSeguro(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            System.err.println("Error: La llave '" + valor + "' debe ser un número entero. Usando 0 por defecto.");
            return 0;
        }
    }

    public static void mostrarAyuda() {

        // Códigos ANSI
        final String RESET = "\u001B[0m";
        final String BOLD = "\u001B[1m";
        final String UNDERLINE = "\u001B[4m";

        final String CYAN = "\u001B[36m";
        final String YELLOW = "\u001B[33m";
        final String GREEN = "\u001B[32m";
        final String RED = "\u001B[31m";

        System.out.println(CYAN + BOLD + "\n=== MANUAL DE USO ===" + RESET);

        System.out.println(YELLOW + "Uso:" + RESET + " java -jar AlitasCifradas.jar <comando> <mensaje> <[llave/clave]>\n");

        System.out.println(CYAN + BOLD + "Comandos disponibles:" + RESET);

        System.out.println(GREEN + "  java -jar AlitasCifradas.jar cifradoCesar" + RESET + "       \"mensaje\" <numero>");
        System.out.println(GREEN + "  java -jar AlitasCifradas.jar descifradoCesar" + RESET + "    \"mensaje\" <numero>");
        System.out.println(GREEN + "  java -jar AlitasCifradas.jar fuerzaCesar" + RESET + "        \"mensaje\"");

        System.out.println(GREEN + "  java -jar AlitasCifradas.jar cifradoVigenere" + RESET + "    \"mensaje\" <palabra>");
        System.out.println(GREEN + "  java -jar AlitasCifradas.jar descifradoVigenere" + RESET + " \"mensaje\" <palabra>");
        System.out.println(GREEN + "  java -jar AlitasCifradas.jar fuerzaVigenere" + RESET + "     \"mensaje\"");

        System.out.println(
                YELLOW + BOLD + "  " +
                        UNDERLINE + "java -jar AlitasCifradas.jar interfazGrafica" + RESET + "\n"
        );
    }

}