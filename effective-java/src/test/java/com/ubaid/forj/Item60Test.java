package com.ubaid.forj;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//ITEM 60: AVOID FLOAT AND DOUBLE IF EXACT ANSWERS ARE REQUIRED
public class Item60Test {
    
    @Test
    void testWrongResultDouble() {
        double a = 1.03;
        double b = 0.42;
        double expectedResult = 0.61;
        double actualResult = a - b;
        Assertions.assertNotEquals(expectedResult, actualResult);
        System.out.println(actualResult);
    }
    
    @Test
    void testWrongResultDouble2() {
        double x = 1;
        double y = 9 * 0.10;
        double expectedResult = 0.10;
        double actualResult = x - y;
        Assertions.assertNotEquals(expectedResult, actualResult);
        System.out.println(actualResult);
    }
    
    // suppose you have a dollar in your pocket,
    // and you see a shelf with a row of delicious candies priced at 10¢, 20¢, 30¢,
    // and so forth, up to a dollar. You buy one of each candy,
    // starting with the one that costs 10¢, until you can’t afford to buy
    // the next candy on the shelf.
    // How many candies do youbuy, and howmuchchangedoyouget?
    @Test
    void testWrongAnswer() {
        double funds = 1.00;
        int itemsBought = 0;
        for (double price = 0.10; funds >= price; price += 0.10) {
            funds -= price;
            itemsBought++;
        }

        System.out.println(itemsBought + " item bought.");
        System.out.println("Change: $" + funds);
    }
    
    @Test
    void correctSolution() {
        final BigDecimal TEN_CENTS = new BigDecimal("0.10");
        int itemsBought = 0;
        BigDecimal funds = new BigDecimal("1.00");
        for (BigDecimal price  = TEN_CENTS;
            funds.compareTo(price) >= 0;
            price = price.add(TEN_CENTS)) {
            funds = funds.subtract(price);
            itemsBought++;
        }
        System.out.println(itemsBought + " items bought.");
        System.out.println("Money left over: $" + funds);
    }
    
    @Test
    void correctSolution2() {
        int itemsBought = 0;
        int funds = 100;
        for (int price = 10; funds >= price; price += 10) {
            funds -= price;
            itemsBought++;
        }
        System.out.println(itemsBought + " items bought.");
        System.out.println("Money left over: $" + funds);
    }
}
