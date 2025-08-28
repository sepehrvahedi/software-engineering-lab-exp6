package com.energymanagement.ui;

import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    
    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }
    
    public void displayWelcome() {
        System.out.println("=".repeat(60));
        System.out.println("🏢 سیستم مدیریت پویای مصرف انرژی هوشمند");
        System.out.println("=".repeat(60));
    }
    
    public void displayMainMenu() {
        System.out.println("\n📋 منوی اصلی:");
        System.out.println("1️⃣  مشاهده وضعیت فعلی سیستم");
        System.out.println("2️⃣  شبیه‌سازی مصرف انرژی");
        System.out.println("3️⃣  تغییر سیاست محاسبه هزینه (مدیر)");
        System.out.println("4️⃣  تغییر وضعیت سیستم (مدیر)");
        System.out.println("0️⃣  خروج");
        System.out.print("\n👆 انتخاب شما: ");
    }
    
    public void displayCostPolicyMenu() {
        System.out.println("\n💰 انتخاب سیاست محاسبه هزینه:");
        System.out.println("1️⃣  تعرفه معمولی (500 تومان/واحد)");
        System.out.println("2️⃣  تعرفه زمان اوج مصرف (1000 تومان/واحد)");
        System.out.println("3️⃣  تعرفه سبز (300 تومان/واحد)");
        System.out.print("\n👆 انتخاب شما: ");
    }
    
    public void displaySystemStateMenu() {
        System.out.println("\n⚡ انتخاب وضعیت سیستم:");
        System.out.println("1️⃣  فعال (Active) - همه سیستم‌ها روشن");
        System.out.println("2️⃣  اقتصادی (Eco Mode) - فقط سیستم‌های حیاتی");
        System.out.println("3️⃣  خاموش (Shutdown) - همه سیستم‌ها خاموش");
        System.out.print("\n👆 انتخاب شما: ");
    }
    
    public int getChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
    
    public double getEnergyAmount() {
        System.out.print("💡 مقدار انرژی مورد نظر (واحد): ");
        try {
            double amount = scanner.nextDouble();
            scanner.nextLine();
            return amount;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
    
    public void displayError(String message) {
        System.out.println("❌ خطا: " + message);
    }
    
    public void displaySuccess(String message) {
        System.out.println("✅ " + message);
    }
    
    public void displayGoodbye() {
        System.out.println("\n👋 از استفاده شما متشکریم!");
        System.out.println("=".repeat(60));
    }
    
    public void close() {
        scanner.close();
    }
}
