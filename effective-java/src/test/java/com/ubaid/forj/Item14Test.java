package com.ubaid.forj;

import static java.util.Comparator.comparingInt;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//ITEM-14: CONSIDER IMPLEMENTING COMPARABLE
public class Item14Test {

    @Test
    void testTreeSet() {
        Set<String> strings = new TreeSet<>();
        strings.add("z");
        strings.add("a");
        strings.add("b");

        String expectedStr = strings.stream().reduce("", (a, b) -> a + b);
        
        Assertions.assertEquals(expectedStr, "abz");
        
    }
    
    @Test
    void testBigDecimalCompareToInconsistencyWithEquals() {
        Set<BigDecimal> set = new HashSet<>();
        BigDecimal oneP0 = new BigDecimal("1.0");
        BigDecimal oneP00 = new BigDecimal("1.00");
        set.add(oneP0);
        set.add(oneP00);
        
        Assertions.assertNotEquals(oneP0, oneP00);
        //added both decimals added in set as they are equals
        Assertions.assertEquals(2, set.size());
        
        Set<BigDecimal> treeSet = new TreeSet<>();
        treeSet.add(oneP0);
        treeSet.add(oneP00);
        
        final int EQUAL_INT = 0;
        Assertions.assertEquals(EQUAL_INT, oneP0.compareTo(oneP00));
        //added as one decimal as they return 0 (equal) when compared by compare to method
        Assertions.assertEquals(1, treeSet.size());

    }
    
    @Test
    void testCompareToMethodForPhoneNumberV1() {
        short countryCode = 1;
        short areaCode = 1;
        short number1 = 1;
        short number2 = 2;
        PhoneNumberV1 phone1 = new PhoneNumberV1(countryCode, areaCode, number1);
        PhoneNumberV1 phone2 = new PhoneNumberV1(countryCode, areaCode, number2);
        
        Assertions.assertEquals(1, phone2.compareTo(phone1), "phone 2 is greater than phone 1");
    }

    @Test
    void testCompareToMethodForPhoneNumberV2() {
        short countryCode = 1;
        short areaCode = 1;
        short number1 = 1;
        short number2 = 2;
        PhoneNumberV2 phone1 = new PhoneNumberV2(countryCode, areaCode, number1);
        PhoneNumberV2 phone2 = new PhoneNumberV2(countryCode, areaCode, number2);

        Assertions.assertEquals(1, phone2.compareTo(phone1), "phone 2 is greater than phone 1");
    }

}

class PhoneNumberV1 implements Comparable<PhoneNumberV1> {
    private final Short countryCode;
    private final Short areaCode;
    private final Short number;

    public PhoneNumberV1(Short countryCode, Short areaCode, Short number) {
        this.countryCode = countryCode;
        this.areaCode = areaCode;
        this.number = number;
    }

    @Override
    public int compareTo(PhoneNumberV1 o) {
        int result = Short.compare(countryCode, o.countryCode);
        if (result == 0) {
            result = Short.compare(areaCode, o.areaCode);
            if (result == 0) {
                result = Short.compare(number, o.number);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneNumberV1 that = (PhoneNumberV1) o;
        return countryCode.equals(that.countryCode) && areaCode.equals(that.areaCode)
            && number.equals(
            that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, areaCode, number);
    }

    @Override
    public String toString() {
        return "PhoneNumberV1{" +
            "countryCode=" + countryCode +
            ", areaCode=" + areaCode +
            ", number=" + number +
            '}';
    }
}

class PhoneNumberV2 implements Comparable<PhoneNumberV2> {

    private static final Comparator<PhoneNumberV2> COMPARATOR = 
         comparingInt((PhoneNumberV2 ph) -> ph.countryCode)
        .thenComparingInt(ph -> ph.areaCode)
        .thenComparingInt(ph -> ph.number);
    private final Short countryCode;
    private final Short areaCode;
    private final Short number;

    public PhoneNumberV2(Short countryCode, Short areaCode, Short number) {
        this.countryCode = countryCode;
        this.areaCode = areaCode;
        this.number = number;
    }

    @Override
    public int compareTo(PhoneNumberV2 o) {
        return COMPARATOR.compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneNumberV2 that = (PhoneNumberV2) o;
        return countryCode.equals(that.countryCode) && areaCode.equals(that.areaCode)
            && number.equals(
            that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, areaCode, number);
    }

    @Override
    public String toString() {
        return "PhoneNumberV2{" +
            "countryCode=" + countryCode +
            ", areaCode=" + areaCode +
            ", number=" + number +
            '}';
    }
}