package managers;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

// Κλάση με helper μεθόδους για το UI
public final class UIHelper {

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Σφάλμα", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(parent, message, title.equals("") ? "MyMovies" : title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean showConfirmation(Component parent, String message, String title) {
        int confirmed = JOptionPane.showOptionDialog(parent, message, title.equals("") ? "Επιβεβαίωση" : title, 0, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Ναι", "Ακύρωση"}, null);
        return confirmed == 0;
    }

    // ChangeListener που χρησιμοποιούμε για την παρακολούθηση αλλαγών σε πλαίσιο κειμένου
    public static void addChangeListener(JTextComponent text, ChangeListener changeListener) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(changeListener);
        DocumentListener listener = new DocumentListener() {
            private int lastChange = 0, lastNotifiedChange = 0;

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lastChange++;
                SwingUtilities.invokeLater(() -> {
                    if (lastNotifiedChange != lastChange) {
                        lastNotifiedChange = lastChange;
                        changeListener.stateChanged(new ChangeEvent(text));
                    }
                });
            }
        };
        text.addPropertyChangeListener("document", (PropertyChangeEvent e) -> {
            Document oldDocument = (Document) e.getOldValue();
            Document newDocument = (Document) e.getNewValue();
            if (oldDocument != null) {
                oldDocument.removeDocumentListener(listener);
            }
            if (newDocument != null) {
                newDocument.addDocumentListener(listener);
            }
            listener.changedUpdate(null);
        });
        Document document = text.getDocument();
        if (document != null) {
            document.addDocumentListener(listener);
        }
    }
}
