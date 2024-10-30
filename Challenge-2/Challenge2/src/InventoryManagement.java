import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// THIS IS QUESTION 2.1

class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}

class Inventory {
    private List<Product> products;

    public Inventory() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double calculateTotalInventoryValue() {
        return products.stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantity())
                .sum();
    }

    public String findMostExpensiveProduct() {
        return products.stream()
                .max(Comparator.comparingDouble(Product::getPrice))
                .map(Product::getName)
                .orElse("No products found");
    }

    public boolean isProductInStock(String productName) {
        return products.stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(productName) && p.getQuantity() > 0);
    }

    public List<Product> sortProducts(String sortBy, boolean ascending) {
        Comparator<Product> comparator;
        switch (sortBy.toLowerCase()) {
            case "price":
                comparator = Comparator.comparingDouble(Product::getPrice);
                break;
            case "quantity":
                comparator = Comparator.comparingInt(Product::getQuantity);
                break;
            default:
                comparator = Comparator.comparing(Product::getName);
        }

        if (!ascending) {
            comparator = comparator.reversed();
        }

        List<Product> sortedProducts = new ArrayList<>(products);
        sortedProducts.sort(comparator);
        return sortedProducts;
    }
}

public class InventoryManagement {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        inventory.addProduct(new Product("Laptop", 999.99, 5));
        inventory.addProduct(new Product("Smartphone", 499.99, 10));
        inventory.addProduct(new Product("Tablet", 299.99, 0));
        inventory.addProduct(new Product("Smartwatch", 199.99, 3));

        // Calculate total inventory value
        System.out.printf("Total inventory value: $%.2f%n", inventory.calculateTotalInventoryValue());

        // Find the most expensive product
        System.out.println("Most expensive product: " + inventory.findMostExpensiveProduct());

        // Check if "Headphones" is in stock
        System.out.println("Is 'Headphones' in stock? " + inventory.isProductInStock("Headphones"));

        // Sort products by price in descending order
        System.out.println("\nProducts sorted by price (descending):");
        inventory.sortProducts("price", false).forEach(p ->
                System.out.printf("%s: $%.2f, Quantity: %d%n", p.getName(), p.getPrice(), p.getQuantity()));

        // Sort products by quantity in ascending order
        System.out.println("\nProducts sorted by quantity (ascending):");
        inventory.sortProducts("quantity", true).forEach(p ->
                System.out.printf("%s: $%.2f, Quantity: %d%n", p.getName(), p.getPrice(), p.getQuantity()));
    }
}