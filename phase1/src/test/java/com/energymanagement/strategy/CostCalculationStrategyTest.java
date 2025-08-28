package com.energymanagement.strategy;

import org.junit.Test;
import static org.junit.Assert.*;

public class CostCalculationStrategyTest {
    
    @Test
    public void testStandardTariffStrategy() {
        CostCalculationStrategy strategy = new StandardTariffStrategy();
        
        assertEquals("Standard tariff cost calculation", 
                     2500.0, strategy.calculateCost(5.0), 0.01);
        assertEquals("Standard tariff name", 
                     "تعرفه معمولی (Standard)", strategy.getStrategyName());
        assertEquals("Standard tariff cost per unit", 
                     500.0, strategy.getCostPerUnit(), 0.01);
    }
    
    @Test
    public void testPeakHoursTariffStrategy() {
        CostCalculationStrategy strategy = new PeakHoursTariffStrategy();
        
        assertEquals("Peak hours tariff cost calculation", 
                     5000.0, strategy.calculateCost(5.0), 0.01);
        assertEquals("Peak hours tariff name", 
                     "تعرفه زمان اوج مصرف (Peak Hours)", strategy.getStrategyName());
        assertEquals("Peak hours tariff cost per unit", 
                     1000.0, strategy.getCostPerUnit(), 0.01);
    }
    
    @Test
    public void testGreenModeTariffStrategy() {
        CostCalculationStrategy strategy = new GreenModeTariffStrategy();
        
        assertEquals("Green mode tariff cost calculation", 
                     1500.0, strategy.calculateCost(5.0), 0.01);
        assertEquals("Green mode tariff name", 
                     "تعرفه سبز (Green Mode)", strategy.getStrategyName());
        assertEquals("Green mode tariff cost per unit", 
                     300.0, strategy.getCostPerUnit(), 0.01);
    }
}
