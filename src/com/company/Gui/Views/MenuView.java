package com.company.Gui.Views;

import com.company.Gui.ViewsManager;

import javax.swing.*;

public class MenuView extends JFrame {
    ViewsManager manager;
    private JButton resume;

    public MenuView(ViewsManager manager) {
        super("Akari");
        this.manager = manager;

        resume = new JButton();
        resume.setIcon(new ImageIcon("src/com/company/Gui/Static/resume.jpg"));
        resume.setBounds(200, 380, 200, 60);
        resume.addActionListener(actionEvent -> manager.ChangeView(this, manager.GetCurrentGame()));
        resume.setVisible(false);
        this.add(resume);

        JButton newGame = new JButton();
        newGame.setIcon(new ImageIcon("src/com/company/Gui/Static/new_game.jpg"));
        newGame.setBounds(200, 450, 200, 60);
        newGame.addActionListener(actionEvent -> manager.NewGame(this));
        this.add(newGame);

        JButton settings = new JButton();
        settings.setIcon(new ImageIcon("src/com/company/Gui/Static/settings.jpg"));
        settings.setBounds(200, 520, 200, 60);
        settings.addActionListener(actionEvent -> manager.ChangeView(this, manager.GetSettingsView()));
        this.add(settings);

        JButton quit = new JButton();
        quit.setIcon(new ImageIcon("src/com/company/Gui/Static/quit.jpg"));
        quit.setBounds(200, 590, 200, 60);
        quit.addActionListener(actionEvent -> System.exit(1));
        this.add(quit);

        JLabel background = new JLabel(new ImageIcon("src/com/company/Gui/Static/background.jpg"));
        this.add(background);
        this.pack();
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public JButton GetResumeButton() {
        return resume;
    }
}
