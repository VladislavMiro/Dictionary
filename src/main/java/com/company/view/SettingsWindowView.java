package com.company.view;

import com.company.controller.SettingsWindowViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SettingsWindowView extends JFrame {

    private JPanel settingWindowView;
    private JButton saveButton;
    private JTextField userNameField;
    private JTextField urlField;
    private JPasswordField passwordField;
    private final SettingsWindowViewController controller = new SettingsWindowViewController();

    public SettingsWindowView() {
        JFrame frame = new JFrame("Настройки подключения");
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 200));
        frame.add(settingWindowView);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                setConfig();
            }

            @Override
            public void windowClosing(WindowEvent e) {

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
        saveButton.addActionListener(e -> {
            String password = String.copyValueOf(passwordField.getPassword());
            if(!userNameField.getText().isEmpty() && !urlField.getText().isEmpty() && !password.isEmpty()) {
                if(controller.saveSettings(urlField.getText(), userNameField.getText(), password)){
                    JOptionPane.showMessageDialog(frame,
                            "Настройки изменены успешно!",
                            "Изменено!",
                            JOptionPane.INFORMATION_MESSAGE);
                    controller.disconnectFromDataBase();
                    if(controller.connectToDataBase()){
                        JOptionPane.showMessageDialog(frame,
                                "Подключение к базе данных успешно!",
                                "База данных подключена!",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "Не удалось подключиться к базе данных!",
                                "Ошибка!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Не удалось сохранить изменения!",
                            "Ошибка!",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Одно из полей пустое!",
                        "Ошибка!",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void setConfig() {
        if (controller.getSettings()) {
            userNameField.setText(controller.getUsername());
            passwordField.setText(controller.getPassword());
            urlField.setText(controller.getUrl());
        }
    }
}
