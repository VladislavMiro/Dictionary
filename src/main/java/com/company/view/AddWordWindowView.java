package com.company.view;

import com.company.controller.AddWordWindowController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AddWordWindowView extends JFrame {
    private JTextField textField;
    private JButton addButton;
    private JPanel addWordWindowView;
    private final JFrame frame = new JFrame("Добавить слово");
    private final AddWordWindowController controller = new AddWordWindowController();
    private JTextArea textArea;

    public AddWordWindowView() {
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 300));
        frame.add(addWordWindowView);
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

        addButton.addActionListener(e -> {
            if (!textField.getText().isEmpty()) {
                System.out.println(textField.getText().toCharArray());
                if (!textArea.getText().isEmpty()) {
                    if (controller.addWord(textField.getText(), textArea.getText())) {
                        JOptionPane.showMessageDialog(frame, "Слово успешно добавлено.", "Слово добавлено!", JOptionPane.INFORMATION_MESSAGE);
                        textArea.setText(null);
                        textField.setText(null);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Не удалось добавить слово.", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Поле \"Значение\" пустое.", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Поле \"Слово\" пустое.", "Ошибка!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}

