package com.ubaid.forj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;

// ITEM 57: MINIMIZE THE SCOPE OF LOCAL VARIABLES
public class Item57Test {
    
    @Test
    void testForLoops() {
        List<String> list = new ArrayList<>(List.of("ubaid", "attiq"));

        System.out.println("List: " + list);
        System.out.println("Removing");
        for (Iterator<String> i = list.iterator(); i.hasNext();) {
            String name = i.next();
            if (name.equals("ubaid")) {
                i.remove();
            }
        }
        System.out.println("List: " + list);
    }
}
