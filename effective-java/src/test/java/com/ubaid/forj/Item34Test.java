package com.ubaid.forj;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// ITEM 34: USE ENUMS INSTEAD OF INT CONSTANTS
public class Item34Test {

    @Test
    void testPlanet() {
        double weightInEarth = 76d;
        double mass = weightInEarth / Planet.EARTH.surfaceGravity;
        for (Planet p: Planet.values()) {
            System.out.printf("Weight on %s is %f%n",
                p, p.surfaceWeight(mass));
        }
    }
    
    @Test
    void testOperationC() {
        double x = 8923232.21232323;
        double y = 92354.2129923193892;
        
        for (OperationC op: OperationC.values()) {
            System.out.printf("%f%s%f = %f%n",
                x, op, y, op.apply(x, y));
        }
    }

    
    enum Apple {
        FUJI, PIPPIN, GRANNY_SMITH
    }
    
    enum Orange {
        NAVEL, TEMPLE, BLOOD
    }
    
    enum Fruit {
        FUJI;
        
        static class Hello {
            
        }
        
        interface Hi {
            
        }
    }

    enum Planet {
        MERCURY(3.302e+23, 2.439e6),
        VENUS(4.869e+24, 6.052e6),
        EARTH(5.975e+24, 6.378e6),
        MARS(6.419e+23, 3.393e6),
        JUPITER(1.899e+27, 7.149e7),
        SATURN(5.685e+26, 6.027e7),
        URANUS(8.683e+25, 2.556e7),
        NEPTUNE(1.024e+26, 2.477e7);

        private static final double G = 6.67300E-11;
        private final double mass;
        private final double radius;
        private final double surfaceGravity;


        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
            surfaceGravity = G * mass / (radius * radius);
        }

        public double mass() {
            return mass;
        }

        public double radius() {
            return radius;
        }

        public double surfaceGravity() {
            return surfaceGravity;
        }

        public double surfaceWeight(double mass) {
            return mass * surfaceGravity;
        }
    }
    
    enum OperationA {
        PLUS, MINUS, TIMES, DIVIDE;
        
        public double apply(double x, double y) {
            return switch (this) {
                case PLUS -> x + y;
                case MINUS -> x - y;
                case TIMES -> x * y;
                case DIVIDE -> x / y;
            };
        }
    }
    
    enum OperationB {
        PLUS {
            @Override
            double apply(double x, double y) {
                return  x + y;
            }
        },
        MINUS {
            @Override
            double apply(double x, double y) {
                return x - y;
            }
        },
        TIMES {
            @Override
            double apply(double x, double y) {
                return  x * y;
            }
        },
        DIVIDE {
            @Override
            double apply(double x, double y) {
                return x/y;
            }
        };
        abstract double apply(double x, double y);
    }
    
    enum OperationC {
        
        PLUS("+") {
            @Override
            double apply(double x, double y) {
                return x + y;
            }
        },
        MINUS("-") {
            @Override
            double apply(double x, double y) {
                return x - y;
            }
        },
        TIMES("*") {
            @Override
            double apply(double x, double y) {
                return x * y;
            }
        },
        DIVIDE("/") {
            @Override
            double apply(double x, double y) {
                return x/y;
            }
        };
        
        private static final Map<String, OperationC> stringToEnum =
            Stream.of(values())
                .collect(Collectors.toMap(Objects::toString, Function.identity()));
        
        private static Optional<OperationC> fromString(String symbol) {
            return Optional.ofNullable(stringToEnum.get(symbol));
        }    
        
        private static final int one = 1;
        private static final String oneS = "1";
        
        private static final List<String> list = List.of("1");
            
            
        private final String symbol;
        OperationC(String symbol) {
            this.symbol = symbol;
            System.out.println(one);
            System.out.println(oneS);
        }
        
        abstract double apply(double x, double y);
        
        @Override
        public String toString() {
            return " " + symbol + " ";
        }
    }
    
    enum PayrollDay {
        MONDAY,
        TUESDAY, 
        WEDNESDAY, 
        THURSDAY,
        FRIDAY, 
        SATURDAY,
        SUNDAY;
        
        private static final int MINS_PER_SHIFT = 8 * 60;
        
        int pay(int minutesWorked, int payRate) {
            int basePay = minutesWorked * payRate;
            int overTimePay = switch (this) {
                case SATURDAY, SUNDAY -> basePay/2;
                default -> minutesWorked <= MINS_PER_SHIFT ? 0 : (minutesWorked - MINS_PER_SHIFT) * (payRate/2);
            };
            return basePay + overTimePay;
        }
    }
    
    
    @Test
    void testPayrollDay() {
        int pay = PayrollDay.SUNDAY.pay(20, 5);
        Assertions.assertEquals(150, pay);
    }
    
    enum PayrollDay1 {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY(PayType.WEEKEND),
        SUNDAY(PayType.WEEKEND);
        
        private final PayType payType;
        
        PayrollDay1(PayType payType) {
            this.payType = payType;
        }
        
        PayrollDay1() {
            this(PayType.WEEKDAY);
        }
        
        int pay(int minutesWorked, int payRate) {
            return payType.pay(minutesWorked, payRate);
        }
        
        private enum PayType {
            
            WEEKDAY {
                @Override
                int overtimePay(int mins, int payRate) {
                    return mins < MINS_PER_SHIFT ? 0 : (mins - MINS_PER_SHIFT) * payRate/2;
                }
            },
            WEEKEND {
                @Override
                int overtimePay(int mins, int payRate) {
                    return mins * payRate/2;
                }
            };
            
            abstract int overtimePay(int mins, int payRate);
            private static final int MINS_PER_SHIFT = 8 * 60;
            int pay(int minsWorked, int payRate) {
                int basePay = minsWorked * payRate;
                return basePay + overtimePay(minsWorked, payRate);
            }
        }
    }

    @Test
    void testPayRollDay1() {
        int pay = PayrollDay1.SUNDAY.pay(20, 5);
        Assertions.assertEquals(150, pay);
    }
}