package ui;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * https://stackoverflow.com/questions/5107629/how-to-redirect-console-content-to-a-textarea-in-java
 * Output stream that writes to a JTextArea
 */
public class CustomOutputStream extends OutputStream {
    private JTextArea textArea;

    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }


    //MODIFIES: textArea
    //EFFECTS: textArea displays what is written to it
    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
        textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
        // keeps the textArea up to date
        textArea.update(textArea.getGraphics());
    }
}
