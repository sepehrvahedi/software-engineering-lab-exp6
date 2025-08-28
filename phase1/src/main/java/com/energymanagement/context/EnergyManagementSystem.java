package com.energymanagement.context;

import com.energymanagement.strategy.*;
import com.energymanagement.state.*;

public class EnergyManagementSystem {
    private CostCalculationStrategy costStrategy;
    private SystemState currentState;
    private double totalEnergyConsumed;
    
    public EnergyManagementSystem() {
        this.costStrategy = new StandardTariffStrategy();
        this.currentState = new ActiveState(this);
        this.totalEnergyConsumed = 0.0;

        this.currentState.enter();
    }
    
    public void setCostCalculationStrategy(CostCalculationStrategy strategy) {
        this.costStrategy = strategy;
        System.out.println("✅ سیاست محاسبه هزینه تغییر کرد به: " + strategy.getStrategyName());
    }
    
    public CostCalculationStrategy getCostCalculationStrategy() {
        return costStrategy;
    }
    
    public double calculateCost(double energyUnits) {
        double adjustedUnits = energyUnits * currentState.getEnergyMultiplier();
        return costStrategy.calculateCost(adjustedUnits);
    }
    
    public double simulateEnergyConsumption(double energyUnits) {
        return calculateCost(energyUnits);
    }
    
    public void setState(SystemState state) {
        this.currentState = state;
    }
    
    public SystemState getCurrentState() {
        return currentState;
    }
    
    public void changeToActiveState() {
        currentState.toActiveState();
    }
    
    public void changeToEcoModeState() {
        currentState.toEcoModeState();
    }
    
    public void changeToShutdownState() {
        currentState.toShutdownState();
    }
    
    public void consumeEnergy(double units) {
        double adjustedUnits = units * currentState.getEnergyMultiplier();
        totalEnergyConsumed += adjustedUnits;
    }
    
    public double getTotalEnergyConsumed() {
        return totalEnergyConsumed;
    }
    
    public double getTotalCost() {
        return costStrategy.calculateCost(totalEnergyConsumed);
    }
    
    public void displayCurrentStatus() {
        System.out.println("\n📊 وضعیت فعلی سیستم:");
        System.out.println("   • وضعیت سیستم: " + currentState.getStateName());
        System.out.println("   • سیاست محاسبه: " + costStrategy.getStrategyName());
        System.out.println("   • کل انرژی مصرفی: " + String.format("%.2f", totalEnergyConsumed) + " واحد");
        System.out.println("   • کل هزینه: " + String.format("%.0f", getTotalCost()) + " تومان");
        System.out.println("   • ضریب مصرف فعلی: " + String.format("%.0f", currentState.getEnergyMultiplier() * 100) + "%");
    }
}
