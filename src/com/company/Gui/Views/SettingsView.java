package com.company.Gui.Views;

import com.company.Gui.ViewsManager;
import com.company.Model.DifficultyLevel;

import javax.swing.*;

public class SettingsView extends JFrame {
    ViewsManager manager;
    private JButton currentLevel, currentX, currentY;

    public SettingsView(ViewsManager manager) {
        super("Akari");
        this.manager = manager;

        putLabels();
        setActionButtons();
        putCurrentInfoButtons();

        JLabel background = new JLabel(new ImageIcon("src/com/company/Gui/Static/settings_background.jpg"));
        this.add(background);
        this.pack();
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void putLabels() {
        JButton size = new JButton(new ImageIcon("src/com/company/Gui/Static/size.jpg"));
        size.setBounds(20, 325, 120, 50);
        this.add(size);

        JButton level = new JButton(new ImageIcon("src/com/company/Gui/Static/level.jpg"));
        level.setBounds(20, 475, 150, 50);
        this.add(level);

        JButton x_lbl = new JButton(new ImageIcon("src/com/company/Gui/Static/XX.jpg"));
        x_lbl.setBounds(170, 305, 50, 40);
        this.add(x_lbl);

        JButton y_lbl = new JButton(new ImageIcon("src/com/company/Gui/Static/YY.jpg"));
        y_lbl.setBounds(170, 355, 50, 40);
        this.add(y_lbl);

        JButton current_size = new JButton(new ImageIcon("src/com/company/Gui/Static/cur_s.jpg"));
        current_size.setBounds(20, 620, 250, 30);
        this.add(current_size);

        JButton current_level = new JButton(new ImageIcon("src/com/company/Gui/Static/cur_l.jpg"));
        current_level.setBounds(20, 650, 250, 30);
        this.add(current_level);

        currentLevel = new JButton(manager.GetLevel().name());
        currentLevel.setBounds(290, 650, 150, 30);
    }

    private void setActionButtons() {
        for (int i = 6; i < 13; i++) {
            JButton x_setter = new JButton("" + i);
            x_setter.setBounds(220 + (i - 6) * 50, 300, 50, 50);
            int finalI = i;
            x_setter.addActionListener(actionEvent -> {
                manager.SetX(finalI);
                currentX.setText("" + finalI);
            });
            this.add(x_setter);

            JButton y_setter = new JButton("" + i);
            y_setter.setBounds(220 + (i - 6) * 50, 350, 50, 50);
            y_setter.addActionListener(actionEvent -> {
                manager.SetY(finalI);
                currentY.setText("" + finalI);
            });
            this.add(y_setter);
        }

        JButton easy = new JButton("EASY");
        easy.setBounds(205, 450, 100, 100);
        easy.addActionListener(actionEvent -> {
            currentLevel.setText("EASY");
            manager.SetLevel(DifficultyLevel.EASY);
        });
        this.add(easy);

        JButton normal = new JButton("NORMAL");
        normal.setBounds(330, 450, 100, 100);
        normal.addActionListener(actionEvent -> {
            currentLevel.setText("NORMAL");
            manager.SetLevel(DifficultyLevel.NORMAL);
        });
        this.add(normal);

        JButton hard = new JButton("HARD");
        hard.setBounds(455, 450, 100, 100);
        hard.addActionListener(actionEvent -> {
            currentLevel.setText("HARD");
            manager.SetLevel(DifficultyLevel.HARD);
        });
        this.add(hard);

        JButton back = new JButton(new ImageIcon("src/com/company/Gui/Static/back.jpg"));
        back.setBounds(5, 700, 200, 60);
        back.addActionListener(actionEvent -> {
            manager.ChangeView(this, manager.GetMenuView());
        });
        this.add(back);

    }

    private void putCurrentInfoButtons() {
        currentLevel = new JButton("NORMAL");
        currentLevel.setBounds(330, 650, 150, 30);
        this.add(currentLevel);

        currentX = new JButton("10");
        currentX.setBounds(330, 620, 50, 30);
        this.add(currentX);

        JButton xi = new JButton("x");
        xi.setBounds(385, 624, 40, 20);
        this.add(xi);

        currentY = new JButton("10");
        currentY.setBounds(430, 620, 50, 30);
        this.add(currentY);


    }
}
