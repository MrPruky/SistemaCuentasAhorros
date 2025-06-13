/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;


import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class SoloLetrasDocumentFilter extends DocumentFilter {

    private final int longitudMaxima;

    public SoloLetrasDocumentFilter(int longitudMaxima) {
        this.longitudMaxima = longitudMaxima;
    }

    private boolean esSoloLetras(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*");
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;

        int nuevaLongitud = fb.getDocument().getLength() + string.length();
        if (esSoloLetras(string) && nuevaLongitud <= longitudMaxima) {
            super.insertString(fb, offset, string, attr);
        } // si no cumple, no se inserta nada
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text == null) return;

        int nuevaLongitud = fb.getDocument().getLength() - length + text.length();
        if (esSoloLetras(text) && nuevaLongitud <= longitudMaxima) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
