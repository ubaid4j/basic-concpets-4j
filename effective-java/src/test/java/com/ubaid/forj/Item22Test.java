package com.ubaid.forj;


import static com.ubaid.forj.PhysicalConstantsUtil.ELECTRON_MASS;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// ITEM 22: USE INTERFACES ONLY TO DEFINE TYPES
public class Item22Test {
    
    @Test
    void testConstantsFromUtil() {
        Assertions.assertNotNull(ELECTRON_MASS);
    }
    

}

// poor usage of interface to declare constants
interface PhysicalConstants {
    // Avogadro's number
    double AVOGADROS_NUMBER = 6.022_140_857e23;
    double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
    double ELECTRON_MASS = 9.109_383_56e-31;
}

// use non instantiable utitile class
class PhysicalConstantsUtil {
    public static final double AVOGADROS_NUMBER = 6.022_140_857e23;
    public static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
    public static final double ELECTRON_MASS = 9.109_383_56e-31;
    
    private PhysicalConstantsUtil() {
        
    }
}