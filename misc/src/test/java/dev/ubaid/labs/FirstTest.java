package dev.ubaid.labs;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstTest {
    
    private static final Logger logger = LoggerFactory.getLogger(FirstTest.class);
    
    @Test
    public void test() {
        List<Integer> list = List.of(12, 4, 6, 13, 5, 10);

        List<Integer> skills = new ArrayList<>(list);
        
        List<Integer> sortedSkills = skills.stream().sorted().collect(Collectors.toList());

        int minLevel = 5;
        int maxLevel = 7;
        
        int minLevelIndex = sortedSkills.indexOf(minLevel);
        minLevelIndex = minLevelIndex == -1 ? 0 : minLevelIndex;
        int maxLevelIndex = sortedSkills.indexOf(maxLevel);
        maxLevelIndex = maxLevelIndex == -1 ? sortedSkills.size() - 1 : maxLevelIndex;        
        List<Integer> subList = sortedSkills.subList(minLevelIndex, maxLevelIndex + 1);
        
        logger.debug(subList.toString());
        
        
    }

    
    @Test
    public void test1() {
        String s = "01:01:00PM";
        String pmOrAm = s.substring(s.length() - 2);
        boolean isPM = pmOrAm.equals("PM");
        System.out.println("AM or PM: " + pmOrAm);
        String hourS = s.substring(0, 2);
        int hour = Integer.parseInt(hourS);
        System.out.println("Hour: " + hour);
        if (isPM && hour < 12) {
            hour += 12;
            s = s.replaceFirst(hourS + ":", hour + ":");
        }
        System.out.println(s);
    }
    
    @Test
    public void test3() {
        String s1 = "yes";

        String s2 = "yes";

        String s3 = new String(s1);
        
        logger.debug("s1 == s2: {}",  s1 == s2);
        logger.debug("s3 == s1: {}",  s3 == s1);
        logger.debug("s1.equals(s2): {}",  s1.equals(s2));
        logger.debug("s3.equals(s1): {}",  s3.equals(s1));
    }
    

}
