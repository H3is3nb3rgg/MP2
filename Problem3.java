import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numProducts = 0;
        while (true) {
            System.out.print("Enter number of products (1 to 8): ");
            if (scanner.hasNextInt()) {
                numProducts = scanner.nextInt();
                if (numProducts >= 1 && numProducts <= 8) {
                    break;
                }
            } else {
                scanner.next();
            }
            System.out.println("Invalid input. Please enter a number between 1 and 8.");
        }
        scanner.nextLine();

        Product[] products = new Product[numProducts];

        for (int i = 0; i < numProducts; i++) {
            System.out.println("\n--- Product " + (i + 1) + " ---");
            System.out.print("Enter Product Code: ");
            String code = scanner.next();

            scanner.nextLine();
            System.out.print("Enter Product Name: ");
            String name = scanner.nextLine().trim();

            double price = -1;
            while (price < 0) {
                System.out.print("Enter Unit Price: ");
                if (scanner.hasNextDouble()) {
                    price = scanner.nextDouble();
                    if (price < 0) System.out.println("Price cannot be negative.");
                } else {
                    scanner.next();
                }
            }

            int stock = -1;
            while (stock < 0) {
                System.out.print("Enter Initial Stock Quantity: ");
                if (scanner.hasNextInt()) {
                    stock = scanner.nextInt();
                    if (stock < 0) System.out.println("Stock cannot be negative.");
                } else {
                    scanner.next();
                }
            }
            scanner.nextLine();

            products[i] = new Product(code, name, price, stock);
        }

        System.out.print("\nEnter number of stock transactions: ");
        int numTx = scanner.nextInt();

        for (int t = 1; t <= numTx; t++) {
            System.out.println("\n--- Transaction " + t + " ---");
            System.out.print("Enter Product Code: ");
            String targetCode = scanner.next();
            System.out.print("Enter Transaction Type (R for Restock / S for Sell): ");
            char type = scanner.next().toUpperCase().charAt(0);
            System.out.print("Enter Quantity: ");
            int qty = scanner.nextInt();

            Product target = null;
            for (Product p : products) {
                if (p.getCode().equalsIgnoreCase(targetCode)) {
                    target = p;
                    break;
                }
            }

            if (target == null) {
                System.out.println("Transaction REJECTED: Product code " + targetCode + " not found.");
            } else if (type == 'R') {
                if (target.restock(qty)) {
                    System.out.printf("Transaction SUCCESS: Restocked %d unit(s) of %s.%n", qty, target.getName());
                } else {
                    System.out.println("Transaction REJECTED: Restock quantity must be positive.");
                }
            } else if (type == 'S') {
                if (target.sell(qty)) {
                    System.out.printf("Transaction SUCCESS: Sold %d unit(s) of %s.%n", qty, target.getName());
                } else {
                    System.out.println("Transaction REJECTED: Insufficient stock or invalid sell quantity.");
                }
            } else {
                System.out.println("Transaction REJECTED: Invalid transaction type.");
            }
        }

        System.out.println("\n================ INVENTORY SUMMARY TABLE ================");
        System.out.printf("%-10s %-20s %-10s %-10s %-15s %-10s%n", "Code", "Name", "Price", "Stock", "Value", "Status");
        System.out.println("-------------------------------------------------------------------------");

        double totalInventoryValue = 0;
        for (Product p : products) {
            double value = p.getInventoryValue();
            totalInventoryValue += value;
            String status = p.isLowStock() ? "LOW STOCK" : "OK";
            System.out.printf("%-10s %-20s %-10.2f %-10d PHP %-10.2f %-10s%n",
                    p.getCode(), p.getName(), p.getPrice(), p.getStock(), value, status);
        }

        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("Total Inventory Value: PHP %.2f%n", totalInventoryValue);
        System.out.println("Total Product Objects Created: " + Product.getProductCount());

        scanner.close();
    }
}

class Product {
    private String code;
    private String name;
    private double price;
    private int stock;
    private static int productCount = 0;

    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price >= 0 ? price : 0;
        this.stock = stock >= 0 ? stock : 0;
        productCount++;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getStock() {
        return this.stock;
    }

    public static int getProductCount() {
        return productCount;
    }

    public boolean restock(int quantity) {
        if (quantity <= 0) {
            return false;
        }
        this.stock += quantity;
        return true;
    }

    public boolean sell(int quantity) {
        if (quantity <= 0 || quantity > this.stock) {
            return false;
        }
        this.stock -= quantity;
        return true;
    }

    public double getInventoryValue() {
        return this.price * this.stock;
    }

    public boolean isLowStock() {
        return this.stock <= 5;
    }
}
