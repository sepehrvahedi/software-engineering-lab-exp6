package com.energymanagement;

import com.energymanagement.context.EnergyManagementSystem;
import com.energymanagement.ui.MenuHandler;
import com.energymanagement.ui.UserInterface;

public class Main {
    public static void main(String[] args) {
        EnergyManagementSystem energySystem = new EnergyManagementSystem();
        UserInterface ui = new UserInterface();
        MenuHandler menuHandler = new MenuHandler(energySystem, ui);

        try {
            menuHandler.handleMainMenu();
        } finally {
            ui.close();
        }
    }
}
