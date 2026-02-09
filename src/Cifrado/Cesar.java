package Cifrado;

import java.util.ArrayList;
import java.util.List;

public class Cesar {

    public String cifrar(String mensaje, int llave) {
        return procesarMensaje(mensaje, llave);
    }

    public String descifrar(String mensajeCifrado, int llave) {
        return procesarMensaje(mensajeCifrado, -llave);
    }

    public List<String> descifrarFuerzaBruta(String mensajeCifrado) {
        List<String> resultados = new ArrayList<>();

        for (int i = 1; i < 26; i++) {
            String intento = descifrar(mensajeCifrado, i);
            resultados.add("Llave " + i + ": " + intento);
        }

        return resultados;
    }

    private String procesarMensaje(String texto, int llave) {
        StringBuilder resultado = new StringBuilder();

        if(llave % 26 == 0){
            throw new IllegalArgumentException("La llave no debe ser multiplo de 26");
        }

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            resultado.append(rotarCaracter(c, llave));
        }

        return resultado.toString();
    }

    private char rotarCaracter(char c, int desplazamiento) {
        if (!Character.isLetter(c)) {
            return c;
        }

        char base = Character.isLowerCase(c) ? 'a' : 'A';

        int valorOriginal = c - base;
        int nuevoValor = (valorOriginal + desplazamiento) % 26;

        if (nuevoValor < 0) {
            nuevoValor += 26;
        }

        return (char) (base + nuevoValor);
    }
}
