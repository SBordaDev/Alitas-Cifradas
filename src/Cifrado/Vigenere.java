package Cifrado;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Vigenere {

    public String cifrar(String mensaje, String clave) {
        return procesarMensaje(mensaje, clave, true);
    }

    public String descifrar(String mensajeCifrado, String clave) {
        return procesarMensaje(mensajeCifrado, clave, false);
    }

    public List<String> descifrarFuerzaBruta(String mensajeCifrado, Path archivo, int cantidadPalabras){
        List<String> respuesta = new ArrayList<>();

        try (Stream<String> llaves = Files.lines(archivo, StandardCharsets.ISO_8859_1)) {
            Iterator<String> it = llaves.limit(cantidadPalabras).iterator();
            while (it.hasNext()){
                String palabra = it.next();
                if(!palabra.trim().isEmpty()){
                    respuesta.add("Clave [" + palabra + "]: " + procesarMensaje(mensajeCifrado, palabra, false));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return respuesta;
    }

    private String procesarMensaje(String texto, String clave, boolean encriptar) {
        if (clave == null || clave.isEmpty()) {
            throw new IllegalArgumentException("La clave no puede estar vacía.");
        }

        StringBuilder resultado = new StringBuilder();
        String claveLimpia = clave.replaceAll("[^a-zA-Z]", "").toUpperCase();
        if (claveLimpia.isEmpty()) {
            return texto;
        }

        int indiceClave = 0;

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);

            if (Character.isLetter(c)) {
                char caracterClave = claveLimpia.charAt(indiceClave % claveLimpia.length());
                int desplazamiento = caracterClave - 'A';

                if (!encriptar) {
                    desplazamiento = -desplazamiento;
                }

                resultado.append(rotarCaracter(c, desplazamiento));

                indiceClave++;
            } else {
                resultado.append(c);
            }
        }

        return resultado.toString();
    }

    private char rotarCaracter(char c, int desplazamiento) {
        char base = Character.isLowerCase(c) ? 'a' : 'A';
        int valorOriginal = c - base;

        // Fórmula del módulo que soporta números negativos (para descifrar)
        int nuevoValor = (valorOriginal + desplazamiento) % 26;
        if (nuevoValor < 0) {
            nuevoValor += 26;
        }

        return (char) (base + nuevoValor);
    }
}
