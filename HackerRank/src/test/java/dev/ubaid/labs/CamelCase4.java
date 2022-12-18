package dev.ubaid.labs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamelCase4 {
    
    static Logger logger = LoggerFactory.getLogger(CamelCase4.class);
    
    @Test
    void test() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        logger.info("Line: {}", line);
    }
    
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            logger.info("Line: {}", line);
        }
    }
    
    @Test
    void test2() {
        String input = "S;M;plasticCup()";
        String firstChar = input.substring(0, 1);
        Assertions.assertEquals("S", firstChar);
        
        String secondChar = input.substring(2, 3);
        Assertions.assertEquals("M", secondChar);
        
        String[] arr = input.split(";");
        String name = arr[arr.length - 1];
        Assertions.assertEquals("plasticCup()", name);
        
        List<String> words = new ArrayList<>();
        String[] chars = "plasticCup".split("");
        String word = "";
        String[] CAPITAL_ALPHABET = {
          "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"  
        };
        List<String> captialAlhabets = Arrays.asList(CAPITAL_ALPHABET);
        for (String c : chars) {
            if (captialAlhabets.contains(c)) {
                words.add(word);
                word = word.toLowerCase();
                word = c;
            } else {
                word += c;
            }
        }
        words.add(word);
        
        String output = String.join(" ", words);




        System.out.println("output: "+ output);
    }
    
    @Test
    void test3() {
        String input = "mouse pad";
        List<String> words = Arrays.asList(input.split(" "));

        words = words
            .stream()
            .map(CamelCase4::capitalize)
            .collect(Collectors.toList());

        System.out.println(words);
        
    }
    
    @Test
    void test4() {
        String input = "Hello";
        input.length();
    }
    
    
    static String capitalize(String word) {
        String firstChar = word.substring(0, 1);
        String firstCharUpperCase = firstChar.toUpperCase();
        word = word.replaceFirst(firstChar, firstCharUpperCase);
        return word;
    }
}
