package Enum;

//TODO: Topic: Enums with field and methods

public enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS  (4.869e+24, 6.0518e6),
    EARTH  (5.976e+24, 6.37814e6);

    private final double mass;
    private final double radius;
    static final double G = 6.67300E-11;

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    double surfaceGravity() {
        return G * mass / (radius * radius);
    }

    double surfaceWeight(double otherMass) {
        return otherMass * surfaceGravity();
    }
}

class Main {
    public static void main(String[] args) {

        // static fields of enums can be accessed directly
        double g = Planet.G;

        // methods are called via instances
        double gra = Planet.EARTH.surfaceGravity();
    }
}
