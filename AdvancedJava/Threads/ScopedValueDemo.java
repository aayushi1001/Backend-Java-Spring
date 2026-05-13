package Threads;

import java.lang.ScopedValue;

public class ScopedValueDemo {

    public static void main(String[] args) {
        // ScopedValue — preferred alternative in virtual thread-heavy code
        final ScopedValue<String> USER = ScopedValue.newInstance();
        final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();

        // Runnable
        ScopedValue.where(USER, "alice").run(() -> {
            System.out.println(USER.get()); // "alice"
        });

        //Callable
        String result = ScopedValue.where(USER, "bob").call(() -> {
            return "Hello, " + USER.get(); // returns a value
        });

        // multiple values
        ScopedValue.where(USER, "carol")
                .where(REQUEST_ID, "req-999")
                .run(() -> {
                    System.out.println("USER       = " + USER.get());       // carol
                    System.out.println("REQUEST_ID = " + REQUEST_ID.get()); // req-999
                });


        //orElseThrow
        try {
            USER.orElseThrow(IllegalStateException::new);
        } catch (IllegalStateException e) {
            System.out.println("USER (unbound) orElseThrow threw: " + e.getClass().getSimpleName());
        }


        // isBound method
        ScopedValue.where(USER, "eve").run(() -> {
            System.out.println("Inside scope  — isBound: " + USER.isBound()); // true
            if (USER.isBound()) {
                System.out.println("USER = " + USER.get());
            }
        });

        if (USER.isBound()) {
            System.out.println("USER = " + USER.get());
        }

        // Rebinding / Shadowing — inner scope overrides outer scope
        ScopedValue.where(USER, "frank").run(() -> {
            System.out.println("Outer scope USER = " + USER.get()); // frank

            ScopedValue.where(USER, "grace").run(() -> {
                System.out.println("Inner scope USER = " + USER.get()); // grace (shadowed)
            });

            System.out.println("Back to outer    = " + USER.get()); // frank (restored)
        });


    }
}
