package com.energymanagement.state;

import com.energymanagement.context.EnergyManagementSystem;

public class ShutdownState extends SystemState {
    
    public ShutdownState(EnergyManagementSystem context) {
        super(context);
    }
    
    @Override
    public void enter() {
        System.out.println("🔴 سیستم خاموش شد - همه سیستم‌ها غیرفعال هستند");
    }
    
    @Override
    public String getStateName() {
        return "خاموش (Shutdown)";
    }
    
    @Override
    public double getEnergyMultiplier() {
        return 0.0;
    }
    
    @Override
    public void toActiveState() {
        context.setState(new ActiveState(context));
        context.getCurrentState().enter();
    }
    
    @Override
    public void toEcoModeState() {
        context.setState(new EcoModeState(context));
        context.getCurrentState().enter();
    }
    
    @Override
    public void toShutdownState() {
        System.out.println("سیستم در حال حاضر خاموش است");
    }
}
