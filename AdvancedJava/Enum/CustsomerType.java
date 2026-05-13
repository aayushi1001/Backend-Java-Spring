package Enum;


// TODO: Topic: Enum implementing Interfaces
// TODO: Topic: Polymorphism with Enums

interface Discount {
    double apply(double price);
    String label();
}

// Every enum has implemented the methods
// If implementation is same for everyone, we can have one common implementation as well
enum CustomerType implements Discount {

    REGULAR {
        @Override
        public double apply(double price) {
            return price; // no discount
        }

        @Override
        public String label() {
            return "Regular Price";
        }
    },

    SILVER {
        @Override
        public double apply(double price) {
            return price * 0.90; // 10% discount
        }

        @Override
        public String label() {
            return "Silver Member (10% off)";
        }
    },

    GOLD {
        @Override
        public double apply(double price) {
            return price * 0.75; // 25% discount
        }

        @Override
        public String label() {
            return "Gold Member (25% off)";
        }
    };
}

class CustomerTypeMain {
    public static void main(String[] args) {
        double originalPrice = 100.0;

        for (CustomerType type : CustomerType.values()) {
            System.out.printf("%-30s Final Price: $%.2f%n", type.label(), type.apply(originalPrice));
        }
    }
}



// polymorphism - calculateFinalPrice expects Discount, and we can pass any of the CustomerType constant here
class BillingService {

    public double calculateFinalPrice(double price, Discount discount) {
        return discount.apply(price);
    }

    public static void main(String[] args) {
        BillingService service = new BillingService();

        Discount customerDiscount = CustomerType.GOLD;
        double finalPrice = service.calculateFinalPrice(200.0, customerDiscount);

        System.out.printf("Final price after discount: $%.2f%n", finalPrice);
    }
}
