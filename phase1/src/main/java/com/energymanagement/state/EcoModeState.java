package com.energymanagement.state;

import com.energymanagement.context.EnergyManagementSystem;

public class EcoModeState extends SystemState {
    
    public EcoModeState(EnergyManagementSystem context) {
        super(context);
    }
    
    @Override
    public void enter() {
        System.out.println("🟡 سیستم در حالت اقتصادی قرار گرفت - تنها سیستم‌های حیاتی فعال هستند");
        System.out.println("   • روشنایی اضطراری: فعال");
        System.out.println("   • تهویه کم مصرف: فعال");
    }
    
    @Override
    public String getStateName() {
        return "اقتصادی (Eco Mode)";
    }
    
    @Override
    public double getEnergyMultiplier() {
        return 0.3;
    }
    
    @Override
    public void toActiveState() {
        context.setState(new ActiveState(context));
        context.getCurrentState().enter();
    }
    
    @Override
    public void toEcoModeState() {
        System.out.println("سیستم در حال حاضر در حالت اقتصادی است");
    }
    
    @Override
    public void toShutdownState() {
        context.setState(new ShutdownState(context));
        context.getCurrentState().enter();
    }
}
