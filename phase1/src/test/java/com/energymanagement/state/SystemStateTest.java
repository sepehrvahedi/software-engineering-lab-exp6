package com.energymanagement.state;

import com.energymanagement.context.EnergyManagementSystem;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SystemStateTest {
    private EnergyManagementSystem system;
    
    @Before
    public void setUp() {
        system = new EnergyManagementSystem();
    }
    
    @Test
    public void testActiveState() {
        ActiveState activeState = new ActiveState(system);
        
        assertEquals("Active state name", "فعال (Active)", activeState.getStateName());
        assertEquals("Active state energy multiplier", 1.0, activeState.getEnergyMultiplier(), 0.01);
    }
    
    @Test
    public void testEcoModeState() {
        EcoModeState ecoState = new EcoModeState(system);
        
        assertEquals("Eco mode state name", "اقتصادی (Eco Mode)", ecoState.getStateName());
        assertEquals("Eco mode state energy multiplier", 0.3, ecoState.getEnergyMultiplier(), 0.01);
    }
    
    @Test
    public void testShutdownState() {
        ShutdownState shutdownState = new ShutdownState(system);
        
        assertEquals("Shutdown state name", "خاموش (Shutdown)", shutdownState.getStateName());
        assertEquals("Shutdown state energy multiplier", 0.0, shutdownState.getEnergyMultiplier(), 0.01);
    }
    
    @Test
    public void testStateTransitions() {
        system.changeToEcoModeState();
        assertTrue("Should be in Eco Mode", system.getCurrentState() instanceof EcoModeState);

        system.changeToShutdownState();
        assertTrue("Should be in Shutdown", system.getCurrentState() instanceof ShutdownState);

        system.changeToActiveState();
        assertTrue("Should be in Active", system.getCurrentState() instanceof ActiveState);
    }
}
