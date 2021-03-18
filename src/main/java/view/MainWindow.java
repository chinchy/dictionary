package view;

import controller.DictController;
import utils.XmlConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;

public class MainWindow extends JFrame {
    DictController dictController = new DictController();
    JMenuBar menuBar;
    JTextField searchField;
    JButton searchButton;
    JList<String> listPane;
    JTextArea textArea;
    JPanel contentPanel;
    JPanel leftPanel;
    JPanel rightPanel;
    JPanel searchPanel;
    JPanel listPanel;
    JPanel meanPanel;

    public MainWindow () {
        super("Толковый словарь");

        menuBar = new JMenuBar();
        searchButton = new JButton();
        searchField = new JTextField(25);
        listPane = new JList<>();
        textArea = new JTextArea("", 10, 40);

        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createAboutMenu());
        setJMenuBar(menuBar);

        searchField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    listPane.setListData(dictController.getWordsList(searchField.getText()));
            }
        });
        searchButton.addActionListener(e -> listPane.setListData(dictController.getWordsList(searchField.getText())));
        listPane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if ( e.getClickCount() == 1 ) {
                    ArrayList<String> list = (ArrayList<String>) listPane.getSelectedValuesList();
                    String mean = list.get(list.size() - 1);
                    textArea.setText(mean + dictController.getMean(mean));
                }
            }
        });


        try {
            Image img = ImageIO.read(new File(new XmlConfig().getByKey("media_folder") + "magnifier.png"));
            Image new_img = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
            searchButton.setIcon(new ImageIcon(new_img));
            searchButton.setPreferredSize(new Dimension(20,20));
        } catch (IOException e) {
            e.printStackTrace();
            searchButton.setText("Поиск");
            searchButton.setPreferredSize(new Dimension(80,20));
        }
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        searchButton.setContentAreaFilled(false);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        contentPanel = new JPanel(new BorderLayout());
        leftPanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel(new BorderLayout());
        searchPanel = new JPanel(new BorderLayout());
        listPanel = new JPanel(new BorderLayout());
        meanPanel = new JPanel(new BorderLayout());

        searchPanel.add(searchField, BorderLayout.WEST);
        searchPanel.add(searchButton, BorderLayout.EAST);
        leftPanel.add(searchPanel, BorderLayout.NORTH);

        listPanel.add(new JScrollPane(listPane), BorderLayout.CENTER);
        leftPanel.add(listPanel, BorderLayout.SOUTH);

        meanPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        rightPanel.add(meanPanel, BorderLayout.CENTER);

        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(rightPanel, BorderLayout.EAST);
        setContentPane(contentPanel);

        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setMinimumSize(new Dimension(740, 200));
        setVisible(true);
    }

    private JMenu createFileMenu()
    {
        JMenu file = new JMenu("Файл");

        JMenuItem clear = new JMenuItem(new ClearAction());
        JMenuItem search = new JMenuItem("Найти");
        search.addActionListener(e -> listPane.setListData(dictController.getWordsList(searchField.getText())));
        JMenuItem exit = new JMenuItem(new ExitAction());

        file.add(clear);
        file.add(search);
        file.addSeparator();
        file.add(exit);

        return file;
    }

    private JMenu createEditMenu()
    {
        JMenu edit = new JMenu("Изменить слово");

        JMenuItem new_word = new JMenuItem(new NewWordAction());
        JMenuItem edit_word = new JMenuItem(new EditWordAction());
        JMenuItem delete_word = new JMenuItem(new DeleteWordAction());

        edit.add(new_word);
        edit.addSeparator();
        edit.add(edit_word);
        edit.add(delete_word);

        return edit;
    }

    private JMenu createAboutMenu()
    {
        JMenu about = new JMenu("Справка");
        JMenuItem help = new JMenuItem(new AboutAction());
        about.add(help);
        return about;
    }

    static class AboutAction extends AbstractAction
    {
        @Serial
        private static final long serialVersionUID = 1L;
        AboutAction() {
            putValue(NAME, "О программе");
        }
        public void actionPerformed(ActionEvent e) {
            System.out.println("Нажатие на кнопку О программе");
        }
    }

    static class NewWordAction extends AbstractAction
    {
        @Serial
        private static final long serialVersionUID = 1L;
        NewWordAction() {
            putValue(NAME, "Добавить новое");
        }
        public void actionPerformed(ActionEvent e) {
            System.out.println("Нажатие на кнопку Добавить слово");
        }
    }

    static class EditWordAction extends AbstractAction
    {
        @Serial
        private static final long serialVersionUID = 1L;
        EditWordAction() {
            putValue(NAME, "Изменить");
        }
        public void actionPerformed(ActionEvent e) {
            System.out.println("Нажатие на кнопку Изменить слово");
        }
    }

    static class DeleteWordAction extends AbstractAction
    {
        @Serial
        private static final long serialVersionUID = 1L;
        DeleteWordAction() {
            putValue(NAME, "Удалить");
        }
        public void actionPerformed(ActionEvent e) {
            System.out.println("Нажатие на кнопку Удалить слово");
        }
    }

    static class ClearAction extends AbstractAction
    {
        @Serial
        private static final long serialVersionUID = 1L;
        ClearAction() {
            putValue(NAME, "Новый поиск");
        }
        public void actionPerformed(ActionEvent e) {
            System.out.println("Нажатие на кнопку Новый поиск");
        }
    }

    static class ExitAction extends AbstractAction
    {
        @Serial
        private static final long serialVersionUID = 1L;
        ExitAction() {
            putValue(NAME, "Выход");
        }
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
