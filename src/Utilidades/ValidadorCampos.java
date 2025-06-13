/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;


import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidadorCampos {

    public static boolean esEmailValido(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean esFechaValida(String fecha) {
        if (fecha == null || fecha.isEmpty()) return false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean longitudMinima(String texto, int minimo) {
        return texto != null && texto.trim().length() >= minimo;
    }

    public static boolean longitudExacta(String texto, int longitud) {
        return texto != null && texto.trim().length() == longitud;
    }

    public static boolean longitudEntre(String texto, int minimo, int maximo) {
        int len = texto != null ? texto.trim().length() : 0;
        return len >= minimo && len <= maximo;
    }
}

