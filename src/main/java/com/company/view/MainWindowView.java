package com.company.view;

import com.company.controller.MainWindowController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainWindowView extends JFrame {

    private JPanel main;
    private JTextArea textArea;
    private JTextField textField;
    private JButton addButton;
    private JButton changeButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JList<String> resultList;
    private final MainWindowController controller = new MainWindowController();
    private static final JFrame frame = new JFrame("Словарь");

    public MainWindowView() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        frame.setJMenuBar(menuBar);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 480));
        frame.add(main);
        frame.pack();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        frame.setVisible(true);

        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                if(controller.configureSettings()) {
                    if (!controller.connectToDataBase()) {
                        JOptionPane.showMessageDialog(frame,
                                "Программа не смогла подключитьсэя к базе данных!",
                                "Ошибка!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Данные о подключении отсутствуют!",
                            "Ошибка!",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                controller.disconnectFromDataBase();
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
                find();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        searchButton.addActionListener(e -> find());

        addButton.addActionListener(e -> {
            new AddWordWindowView();
            frame.setEnabled(false);
        });
        changeButton.addActionListener(e -> {
            if (!resultList.isSelectionEmpty()) {
                EditWordWindowView window = new EditWordWindowView();
                int index = resultList.getSelectedIndex();
                window.setData(controller.getArrayOfWords().get(index).getId(),
                        controller.getArrayOfWords().get(index).getWord(),
                        controller.getArrayOfWords().get(index).getDescription());
                frame.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Выбирите запись для редактирования.",
                        "Ошибка!",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        deleteButton.addActionListener(e -> {
            if (!resultList.isSelectionEmpty()) {
                Object[] options = {"Да", "Нет"};
                int answer = JOptionPane.showOptionDialog(frame,
                        "Вы уверены что хотите удалить запись?",
                        "Удалить запись?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]);

                if (answer == 0) {
                    if (controller.deleteWord(resultList.getSelectedIndex())) {
                        JOptionPane.showMessageDialog(frame,
                                "Запись была удалена успешно.",
                                "Запись удалена!",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "Запись не была удалена.",
                                "Ошибка!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Выберите запись для удаления!.",
                        "Ошибка!",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        resultList.addListSelectionListener(e -> {
            if (!resultList.isSelectionEmpty()) {
                if (!controller.getArrayOfWords().isEmpty()) {
                    textArea.setText(null);
                    textArea.setText(controller.getArrayOfWords().get(resultList.getSelectedIndex()).getWord() + "\n" +
                            controller.getArrayOfWords().get(resultList.getSelectedIndex()).getDescription());
                }
            }
        });
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                find();
            }
        });
    }

    private void find() {
        if (!textField.getText().isEmpty()) {
            resultList.clearSelection();
            if (controller.searchWord(textField.getText())) {
                resultList.setListData(controller.getNamesArray());
                textArea.setText(null);
            } else {
                resultList.setListData(new String[] {});
                textArea.setText(null);
                textArea.setText("Ничего не найдено");
            }

        }
    }

    public static void setEnableFrame(boolean bool) {
        frame.setEnabled(bool);
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu("Файл");
        file.addSeparator();
        JMenuItem settings = new JMenuItem("Настройки");
        JMenuItem connectToDB = new JMenuItem("Подключиться");
        JMenuItem disconnectToDB = new JMenuItem("Отключиться");
        file.add(connectToDB);
        file.add(disconnectToDB);
        file.add(settings);
        connectToDB.addActionListener(e -> {
            if (controller.configureSettings()) {
                if (controller.connectToDataBase()) {
                    JOptionPane.showMessageDialog(frame,
                            "База данных успешно подключена!",
                            "База данных подключена!",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Не удалось подключиться к базе данных!",
                            "Ошибка подключения!",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Данные о подключении отсутствуют!",
                        "Ошибка!",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        disconnectToDB.addActionListener(e -> {
            if(controller.disconnectFromDataBase()) {
                JOptionPane.showMessageDialog(frame,
                        "База данных успешно отключена!",
                        "База данных отключена!",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Не удалось отключиться от базы данных!",
                        "Ошибка при отключении!",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        settings.addActionListener(e -> new SettingsWindowView());
        return file;
    }

}
