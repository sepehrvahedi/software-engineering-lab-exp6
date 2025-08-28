package com.energymanagement.strategy;

public class GreenModeTariffStrategy implements CostCalculationStrategy {
    private static final double COST_PER_UNIT = 300.0;
    
    @Override
    public double calculateCost(double energyUnits) {
        return energyUnits * COST_PER_UNIT;
    }
    
    @Override
    public String getStrategyName() {
        return "تعرفه سبز (Green Mode)";
    }
    
    @Override
    public double getCostPerUnit() {
        return COST_PER_UNIT;
    }
}
