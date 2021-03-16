package view;

import utils.XmlConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serial;

public class MainWindow extends JFrame  {
    JMenuBar menuBar;
    JTextField searchField;
    JButton searchButton;
    JScrollPane listPane;
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
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createAboutMenu());
        setJMenuBar(menuBar);

        searchField = new JTextField(25);

        searchButton = new JButton(new SearchAction());
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

        listPane = new JScrollPane();

        textArea = new JTextArea("", 10, 40);

        contentPanel = new JPanel(new BorderLayout());
        leftPanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel(new BorderLayout());
        searchPanel = new JPanel(new BorderLayout());
        listPanel = new JPanel(new BorderLayout());
        meanPanel = new JPanel(new BorderLayout());

        searchPanel.add(searchField, BorderLayout.WEST);
        searchPanel.add(searchButton, BorderLayout.EAST);
        leftPanel.add(searchPanel, BorderLayout.NORTH);

        listPanel.add(listPane, BorderLayout.CENTER);
        leftPanel.add(listPanel, BorderLayout.SOUTH);

        meanPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        rightPanel.add(meanPanel, BorderLayout.CENTER);

        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(rightPanel, BorderLayout.EAST);
        setContentPane(contentPanel);

//        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private JMenu createFileMenu()
    {
        JMenu file = new JMenu("Файл");

        JMenuItem clear = new JMenuItem(new ClearAction());
        JMenuItem search = new JMenuItem("Найти");
        search.addActionListener(new SearchAction());
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

    static class SearchAction extends AbstractAction {
        @Serial
        private static final long serialVersionUID = 1L;
        SearchAction() {}
        public void actionPerformed(ActionEvent e) {
//            JButton btn = (JButton) e.getSource();
            System.out.println("Нажатие на кнопку Поиск");
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
