package Enum;

//TODO: Topic: Compiled version of enum
//TODO: Topic: EnumSet


import java.util.EnumSet;

/**
 *
 * The above enum gets converted roughly like this

 public final class Day extends java.lang.Enum<Day> {

     public static final Day MONDAY  = new Day("MONDAY", 0);
     public static final Day TUESDAY = new Day("TUESDAY", 1);
     public static final Day WEDNESDAY = new Day("WEDNESDAY", 2);

     private static final Day[] $VALUES = { MONDAY, TUESDAY, WEDNESDAY };

     private Day(String name, int ordinal) {
        super(name, ordinal);
     }

     public static Day[] values() {
        return $VALUES.clone();
     }

     public static Day valueOf(String name) {
        return Enum.valueOf(Day.class, name);
     }
 }


 **/

public enum Day {
    MONDAY, TUESDAY, WEDNESDAY;
}

class Days {
    public static void main(String[] args) {
        EnumSet<Day> weekdays = EnumSet.range(Day.MONDAY, Day.WEDNESDAY);
        System.out.println(weekdays);
    }
}
