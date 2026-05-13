package SealedClass;

sealed class PaymentMethod permits Cash, CreditCard, DebitCard {}
final class Cash        extends PaymentMethod {}
final class CreditCard  extends PaymentMethod {}
final class DebitCard   extends PaymentMethod {}

public class PaymentProcessor {

//    public void process(PaymentMethod method) {
//        switch (method) {
//            case Cash c        -> handleCash();
//            case CreditCard cc -> handleCredit();
//            case DebitCard dc  -> handleDebit();
//        }
//    }
//
//    private void handleCash()   { System.out.println("Handling cash"); }
//    private void handleCredit() { System.out.println("Handling credit"); }
//    private void handleDebit()  { System.out.println("Handling debit"); }
//
//    public static void main(String[] args) {
//        PaymentProcessor processor = new PaymentProcessor();
//        processor.process(new Cash());
//        processor.process(new CreditCard());
//        processor.process(new DebitCard());
//    }
}
