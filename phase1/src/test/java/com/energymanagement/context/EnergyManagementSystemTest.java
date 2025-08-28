package com.energymanagement.context;

import com.energymanagement.strategy.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EnergyManagementSystemTest {
    private EnergyManagementSystem system;
    
    @Before
    public void setUp() {
        system = new EnergyManagementSystem();
    }
    
    @Test
    public void testInitialState() {
        assertTrue("Initial strategy should be Standard", 
                   system.getCostCalculationStrategy() instanceof StandardTariffStrategy);
        assertEquals("Initial state should be Active", 
                     "فعال (Active)", system.getCurrentState().getStateName());
    }
    
    @Test
    public void testStrategyChange() {
        PeakHoursTariffStrategy peakStrategy = new PeakHoursTariffStrategy();
        system.setCostCalculationStrategy(peakStrategy);
        
        assertEquals("Strategy should be changed to Peak Hours", 
                     peakStrategy, system.getCostCalculationStrategy());
    }
    
    @Test
    public void testEnergyConsumptionAndCostCalculation() {
        system.consumeEnergy(10.0);
        assertEquals("Energy consumption should be tracked", 
                     10.0, system.getTotalEnergyConsumed(), 0.01);
        assertEquals("Cost should be calculated correctly", 
                     5000.0, system.getTotalCost(), 0.01);

        system.changeToEcoModeState();
        system.consumeEnergy(10.0);
        assertEquals("Energy consumption should account for multiplier", 
                     13.0, system.getTotalEnergyConsumed(), 0.01);
    }
    
    @Test
    public void testSimulateEnergyConsumption() {
        double simulatedCost = system.simulateEnergyConsumption(5.0);
        assertEquals("Simulated cost should be correct", 
                     2500.0, simulatedCost, 0.01);
        assertEquals("Simulation should not affect total consumption", 
                     0.0, system.getTotalEnergyConsumed(), 0.01);
    }
}
