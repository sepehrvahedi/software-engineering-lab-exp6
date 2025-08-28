package com.energymanagement.strategy;

public interface CostCalculationStrategy {
    double calculateCost(double energyUnits);
    
    String getStrategyName();
    
    double getCostPerUnit();
}
