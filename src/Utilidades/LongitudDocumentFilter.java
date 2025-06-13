package Utilidades;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Filtro de documento que limita la longitud máxima permitida en un JTextField.
 */
public class LongitudDocumentFilter extends DocumentFilter {

    private final int longitudMaxima;

    public LongitudDocumentFilter(int longitudMaxima) {
        this.longitudMaxima = longitudMaxima;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String texto, AttributeSet attr)
            throws BadLocationException {
        if (texto == null) return;

        int nuevaLongitud = fb.getDocument().getLength() + texto.length();
        if (nuevaLongitud <= longitudMaxima) {
            super.insertString(fb, offset, texto, attr);
        }
        // Si no, no inserta nada.
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String texto, AttributeSet attrs)
            throws BadLocationException {
        if (texto == null) return;

        int longitudActual = fb.getDocument().getLength();
        int nuevaLongitud = longitudActual - length + texto.length();
        if (nuevaLongitud <= longitudMaxima) {
            super.replace(fb, offset, length, texto, attrs);
        }
        // Si no, no reemplaza nada.
    }
}

