package dev.ubaid.labs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

@Slf4j
public class SecondTest {
    
    
    /*
    * Sort the given list of name. The sorting should be based on the name alone and not the initial. The result should not truncate the initials.
Input: ["T Danny", "K Peter", "B Anna"]

Output: ["B Anna", "T Danny", "K Peter"]
    * 
    * */
    @Test
    void test1() {
        List<String> input = new ArrayList<>(Arrays.asList("T Danny", "K Peter", "B Anna"));

        Comparator<String> comparator = (i1, i2) -> {
            String name1 = i1.split(" ")[1];
            String name2 = i2.split(" ")[1];
            //here we check name in alphabaticall order
            return stringCompare(name1, name2);
        };
        
        input.sort(comparator);
        
        
        log.debug("{}", input);
    }

    public static int stringCompare(String str1, String str2)
    {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }

        // Edge case for strings like
        // String 1="Geeks" and String 2="Geeksforgeeks"
        if (l1 != l2) {
            return l1 - l2;
        }

        // If none of the above conditions is true,
        // it implies both the strings are equal
        else {
            return 0;
        }
    }
}
