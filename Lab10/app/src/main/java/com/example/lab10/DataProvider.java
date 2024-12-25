package com.example.lab10;

public class DataProvider {
    private static final Product[] skiEquipment = {
            new Product("Beginner Skis", "Perfect for learning, these skis offer stability and control", 299.99, R.drawable.ic_launcher_background, "Equipment"),
            new Product("Pro Boots", "High-performance ski boots with custom fit", 199.99, R.drawable.ic_launcher_background, "Equipment"),
            new Product("Safety Helmet", "Certified safety helmet with adjustable fit", 79.99, R.drawable.ic_launcher_background, "Equipment"),
            new Product("Ski Poles", "Lightweight aluminum poles with ergonomic grips", 49.99, R.drawable.ic_launcher_background, "Equipment"),
            new Product("Goggles", "Anti-fog goggles with UV protection", 89.99, R.drawable.ic_launcher_background, "Equipment"),
            new Product("Binding Set", "Adjustable bindings for all skill levels", 149.99, R.drawable.ic_launcher_background, "Equipment"),
            new Product("Ski Bag", "Padded bag for ski transport and storage", 69.99, R.drawable.ic_launcher_background, "Equipment"),
            new Product("Boot Bag", "Ventilated bag for ski boots", 39.99, R.drawable.ic_launcher_background, "Equipment")
    };

    private static final Product[] skiClothing = {
            new Product("Ski Jacket", "Waterproof and breathable winter jacket", 199.99, R.drawable.ic_launcher_background, "Clothing"),
            new Product("Ski Pants", "Insulated pants with reinforced knees", 149.99, R.drawable.ic_launcher_background, "Clothing"),
            new Product("Base Layer", "Moisture-wicking thermal underwear", 49.99, R.drawable.ic_launcher_background, "Clothing"),
            new Product("Ski Socks", "Warm compression socks for all-day comfort", 19.99, R.drawable.ic_launcher_background, "Clothing"),
            new Product("Gloves", "Waterproof gloves with thermal lining", 59.99, R.drawable.ic_launcher_background, "Clothing"),
            new Product("Neck Warmer", "Fleece neck gaiter for extra warmth", 24.99, R.drawable.ic_launcher_background, "Clothing"),
            new Product("Beanie", "Warm winter hat with moisture-wicking lining", 29.99, R.drawable.ic_launcher_background, "Clothing"),
            new Product("Ski Mask", "Full-face protection for extreme conditions", 34.99, R.drawable.ic_launcher_background, "Clothing")
    };

    private static final Store[] stores = {
            new Store("Alpine Sports", "123 Mountain View Rd, Aspen, CO", "+1-555-0123", "9:00-18:00"),
            new Store("Ski Pro Shop", "456 Snow Peak Ave, Vail, CO", "+1-555-0124", "8:00-19:00"),
            new Store("Mountain Gear", "789 Powder Lane, Park City, UT", "+1-555-0125", "10:00-20:00"),
            new Store("Snow Sports", "321 Winter St, Breckenridge, CO", "+1-555-0126", "9:00-17:00"),
            new Store("Peak Performance", "654 Summit Rd, Jackson Hole, WY", "+1-555-0127", "8:30-18:30")
    };

    public static Product[] getProductsByCategory(String category) {
        return category.equals("Equipment") ? skiEquipment : skiClothing;
    }

    public static Store[] getStores() {
        return stores;
    }
}
