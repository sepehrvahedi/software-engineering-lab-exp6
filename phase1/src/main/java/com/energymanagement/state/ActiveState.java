package com.energymanagement.state;

import com.energymanagement.context.EnergyManagementSystem;

public class ActiveState extends SystemState {
    
    public ActiveState(EnergyManagementSystem context) {
        super(context);
    }
    
    @Override
    public void enter() {
        System.out.println("🟢 سیستم در حالت فعال قرار گرفت - همه سیستم‌ها روشن هستند");
    }
    
    @Override
    public String getStateName() {
        return "فعال (Active)";
    }
    
    @Override
    public double getEnergyMultiplier() {
        return 1.0;
    }
    
    @Override
    public void toActiveState() {
        System.out.println("سیستم در حال حاضر در حالت فعال است");
    }
    
    @Override
    public void toEcoModeState() {
        context.setState(new EcoModeState(context));
        context.getCurrentState().enter();
    }
    
    @Override
    public void toShutdownState() {
        context.setState(new ShutdownState(context));
        context.getCurrentState().enter();
    }
}
