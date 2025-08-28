package com.energymanagement.strategy;

public class StandardTariffStrategy implements CostCalculationStrategy {
    private static final double COST_PER_UNIT = 500.0;
    
    @Override
    public double calculateCost(double energyUnits) {
        return energyUnits * COST_PER_UNIT;
    }
    
    @Override
    public String getStrategyName() {
        return "تعرفه معمولی (Standard)";
    }
    
    @Override
    public double getCostPerUnit() {
        return COST_PER_UNIT;
    }
}
