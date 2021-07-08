package com.company.Gui;

import com.company.Gui.Views.GameView;
import com.company.Gui.Views.MenuView;
import com.company.Gui.Views.SettingsView;
import com.company.Model.DifficultyLevel;

import javax.swing.*;

public class ViewsManager {
    private int x = 10;
    private int y = 10;
    private DifficultyLevel level = DifficultyLevel.NORMAL;
    private GameView currentGame;
    private SettingsView settingsView;
    private MenuView menuView;

    public ViewsManager() {
        menuView = new MenuView(this);
        menuView.setSize(600, 800);
        menuView.setVisible(true);
        menuView.setResizable(false);

        settingsView = new SettingsView(this);
        settingsView.setSize(600, 800);
        settingsView.setResizable(false);
    }

    public int GetX() {
        return x;
    }

    public int GetY() {
        return y;
    }

    public DifficultyLevel GetLevel() {
        return level;
    }

    public void SetX(int x) {
        this.x = x;
    }

    public void SetY(int y) {
        this.y = y;
    }

    public void SetLevel(DifficultyLevel level) {
        this.level = level;
    }

    public GameView GetCurrentGame() {
        return currentGame;
    }

    public SettingsView GetSettingsView() {
        return settingsView;
    }

    public MenuView GetMenuView() {
        return menuView;
    }

    public void ChangeView(JFrame sender, JFrame init) {
        sender.setVisible(false);
        init.setVisible(true);
    }

    public void NewGame(JFrame frame) {
        frame.setVisible(false);
        this.currentGame = new GameView(this);
        currentGame.setSize(64 * x, 64 * (y + 1));
        currentGame.setVisible(true);
        menuView.GetResumeButton().setVisible(true);

    }

}
