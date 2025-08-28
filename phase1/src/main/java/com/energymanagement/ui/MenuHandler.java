package com.energymanagement.ui;

import com.energymanagement.context.EnergyManagementSystem;
import com.energymanagement.strategy.*;

public class MenuHandler {
    private EnergyManagementSystem energySystem;
    private UserInterface ui;
    
    public MenuHandler(EnergyManagementSystem energySystem, UserInterface ui) {
        this.energySystem = energySystem;
        this.ui = ui;
    }
    
    public void handleMainMenu() {
        boolean running = true;
        
        ui.displayWelcome();
        
        while (running) {
            ui.displayMainMenu();
            int choice = ui.getChoice();
            
            switch (choice) {
                case 1:
                    handleViewStatus();
                    break;
                case 2:
                    handleSimulateConsumption();
                    break;
                case 3:
                    handleChangeCostPolicy();
                    break;
                case 4:
                    handleChangeSystemState();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    ui.displayError("انتخاب نامعتبر! لطفاً دوباره تلاش کنید.");
                    break;
            }
        }
        
        ui.displayGoodbye();
    }
    
    private void handleViewStatus() {
        energySystem.displayCurrentStatus();
    }
    
    private void handleSimulateConsumption() {
        double amount = ui.getEnergyAmount();
        if (amount < 0) {
            ui.displayError("مقدار نامعتبر!");
            return;
        }
        
        double cost = energySystem.simulateEnergyConsumption(amount);
        System.out.println("\n📊 نتیجه شبیه‌سازی:");
        System.out.println("   • مقدار انرژی: " + String.format("%.2f", amount) + " واحد");
        System.out.println("   • وضعیت سیستم: " + energySystem.getCurrentState().getStateName());
        System.out.println("   • انرژی مؤثر: " + String.format("%.2f", amount * energySystem.getCurrentState().getEnergyMultiplier()) + " واحد");
        System.out.println("   • هزینه محاسبه شده: " + String.format("%.0f", cost) + " تومان");

        energySystem.consumeEnergy(amount);
        ui.displaySuccess("انرژی مصرف شد و به کل مصرف اضافه شد");
    }
    
    private void handleChangeCostPolicy() {
        ui.displayCostPolicyMenu();
        int choice = ui.getChoice();
        
        CostCalculationStrategy newStrategy = null;
        
        switch (choice) {
            case 1:
                newStrategy = new StandardTariffStrategy();
                break;
            case 2:
                newStrategy = new PeakHoursTariffStrategy();
                break;
            case 3:
                newStrategy = new GreenModeTariffStrategy();
                break;
            default:
                ui.displayError("انتخاب نامعتبر!");
                return;
        }
        
        energySystem.setCostCalculationStrategy(newStrategy);
    }
    
    private void handleChangeSystemState() {
        ui.displaySystemStateMenu();
        int choice = ui.getChoice();
        
        switch (choice) {
            case 1:
                energySystem.changeToActiveState();
                break;
            case 2:
                energySystem.changeToEcoModeState();
                break;
            case 3:
                energySystem.changeToShutdownState();
                break;
            default:
                ui.displayError("انتخاب نامعتبر!");
                break;
        }
    }
}
