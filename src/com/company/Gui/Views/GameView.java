package com.company.Gui.Views;

import com.company.Gui.ViewsManager;
import com.company.Logic.Controller;
import com.company.Model.Packet;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameView extends JFrame {
    private Controller controller;
    private ViewsManager manager;
    private JButton[][] fields;
    private List<Packet> errors;
    private int fieldSize = 64;

    private ImageIcon bulb = new ImageIcon("src/com/company/Gui/Static/bulb.jpg"),
            bulb_err = new ImageIcon("src/com/company/Gui/Static/bulb_err.jpg");

    public GameView(ViewsManager manager) {
        super("Akari");
        this.manager = manager;
        this.controller = new Controller();

        initFields();
        putWalls();
        setAction();
        putNavigateBar();

        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initFields() {
        this.fields = new JButton[manager.GetX()][manager.GetY()];
        for (int i = 0; i < manager.GetX(); i++) {
            for (int j = 0; j < manager.GetY(); j++) {
                this.fields[i][j] = new JButton();
                this.add(this.fields[i][j]);
            }
        }
    }

    private void putWalls() {
        for (Packet packet : controller.CreateMap(manager.GetX(), manager.GetY(), manager.GetLevel())) {
            fields[packet.GetX()][packet.GetY()].setBackground(Color.CYAN);
            if (packet.GetData() != -1)
                fields[packet.GetX()][packet.GetY()].setText(String.valueOf(packet.GetData()));
        }
    }

    private void setAction() {
        for (int x = 0; x < manager.GetX(); x++) {
            for (int y = 0; y < manager.GetY(); y++) {
                fields[x][y].setBounds(fieldSize * x, fieldSize * y, fieldSize, fieldSize);
                if (fields[x][y].getBackground() != Color.CYAN) {
                    int finalX = x;
                    int finalY = y;
                    fields[x][y].addActionListener(actionEvent -> {
                        if (fields[finalX][finalY].getIcon() != null) {
                            fields[finalX][finalY].setIcon(null);
                            for (Packet packet : controller.PutRemoveBulb(finalX, finalY)) {
                                fields[packet.GetX()][packet.GetY()].setBackground(null);

                            }
                        } else {
                            fields[finalX][finalY].setIcon(bulb);
                            for (Packet packet : controller.PutRemoveBulb(finalX, finalY)) {
                                fields[packet.GetX()][packet.GetY()].setBackground(Color.YELLOW);
                                updateErrorsList(packet);
                            }
                        }
                        updateErrorsList(new Packet(finalX, finalY, 0));
                    });
                }
            }
        }
    }

    private void putNavigateBar() {
        JButton back = new JButton("BACK");
        back.setBounds(0, manager.GetY() * fieldSize, manager.GetX() * fieldSize / 3, fieldSize);
        back.addActionListener(actionEvent -> {
            manager.ChangeView(this, manager.GetMenuView());
        });
        this.add(back);

        JButton check = new JButton("CHECK");
        check.setBounds(manager.GetX() * fieldSize / 3, manager.GetY() * fieldSize, manager.GetX() * fieldSize / 3, fieldSize);
        check.addActionListener(actionEvent -> {
            if (errors != null) {
                for (Packet packet : errors) {
                    switch (packet.GetData()) {
                        case 0:
                            fields[packet.GetX()][packet.GetY()].setBackground(null);
                            break;
                        case 1:
                            fields[packet.GetX()][packet.GetY()].setBackground(Color.CYAN);
                            break;
                        case 2:
                            fields[packet.GetX()][packet.GetY()].setIcon(bulb);
                    }
                }
            }
            errors = controller.CheckMap();
            for (Packet packet : controller.CheckMap()) {
                if (packet.GetData() == 2) {
                    fields[packet.GetX()][packet.GetY()].setIcon(bulb_err);
                } else {
                    fields[packet.GetX()][packet.GetY()].setBackground(Color.RED);
                }
            }
            if (errors.size() == 0) {
                check.setBackground(Color.GREEN);
            } else {
                check.setBackground(Color.RED);
            }

        });
        this.add(check);

        JButton next = new JButton("NEXT");
        next.setBounds(manager.GetX() * fieldSize * 2 / 3, manager.GetY() * fieldSize, manager.GetX() * fieldSize / 3, fieldSize);
        next.addActionListener(actionEvent -> manager.NewGame(this));
        this.add(next);
    }

    private void updateErrorsList(Packet packet) {
        if (errors != null)
            errors.remove(packet);
    }
}
