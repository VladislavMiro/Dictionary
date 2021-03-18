package com.company.view;

import com.company.controller.EditWordWindowController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class EditWordWindowView extends JFrame {
    private JTextField textField;
    private JTextArea textArea;
    private JButton editButton;
    private JPanel editWordWindowView;
    private final EditWordWindowController controller = new EditWordWindowController();
    private int idOfWord;

    public EditWordWindowView() {
        JFrame frame = new JFrame("Изменить");
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 300));
        frame.add(editWordWindowView);
        frame.pack();
        frame.setVisible(true);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                MainWindowView.setEnableFrame(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        editButton.addActionListener(e -> {
            if (!textField.getText().isEmpty()) {
                System.out.println(textField.getText().toCharArray());
                if (!textArea.getText().isEmpty()) {
                        if (controller.editWord(idOfWord, textField.getText(), textArea.getText())) {
                            JOptionPane.showMessageDialog(frame, "Слово было изменено", "Слово изменено", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Слово не было изменено", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                        }
                } else {
                    JOptionPane.showMessageDialog(frame, "Поле \"Значение\" пустое.", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Поле \"Слово\" пустое.", "Ошибка!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void setData(int idOfWord, String word, String description) {
        textField.setText(word);
        textArea.setText(description);
        this.idOfWord = idOfWord;
    }

}
