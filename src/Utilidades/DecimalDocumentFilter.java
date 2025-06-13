/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author info
 */
public class DecimalDocumentFilter extends DocumentFilter {
    private final int maxEnteros;
    private final int maxDecimales;

    public DecimalDocumentFilter(int maxEnteros, int maxDecimales) {
        this.maxEnteros = maxEnteros;
        this.maxDecimales = maxDecimales;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
        throws BadLocationException {
        if (isValidInput(fb.getDocument().getText(0, fb.getDocument().getLength()), string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
        throws BadLocationException {
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        StringBuilder builder = new StringBuilder(currentText);
        builder.replace(offset, offset + length, text);
        if (isValidInput(currentText, builder.toString())) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean isValidInput(String oldText, String newText) {
        if (newText.isEmpty()) return true;
        if (!newText.matches("\\d*(\\.\\d*)?")) return false;
        String[] parts = newText.split("\\.");
        return parts[0].length() <= maxEnteros &&
               (parts.length == 1 || parts[1].length() <= maxDecimales);
    }
}
