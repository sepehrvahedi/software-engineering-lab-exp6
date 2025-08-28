package com.energymanagement.strategy;

public class PeakHoursTariffStrategy implements CostCalculationStrategy {
    private static final double COST_PER_UNIT = 1000.0;
    
    @Override
    public double calculateCost(double energyUnits) {
        return energyUnits * COST_PER_UNIT;
    }
    
    @Override
    public String getStrategyName() {
        return "تعرفه زمان اوج مصرف (Peak Hours)";
    }
    
    @Override
    public double getCostPerUnit() {
        return COST_PER_UNIT;
    }
}
