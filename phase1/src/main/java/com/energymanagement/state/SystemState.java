package com.energymanagement.state;

import com.energymanagement.context.EnergyManagementSystem;

public abstract class SystemState {
    protected EnergyManagementSystem context;
    
    public SystemState(EnergyManagementSystem context) {
        this.context = context;
    }
    
    public abstract void enter();
    
    public abstract String getStateName();
    
    public abstract double getEnergyMultiplier();
    
    public abstract void toActiveState();
    
    public abstract void toEcoModeState();
    
    public abstract void toShutdownState();
}
