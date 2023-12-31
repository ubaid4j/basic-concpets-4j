package dev.ubaid.labs.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class MapFilterReduceTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    void testWithClassifier() {
        List<String> strings = List.of("one", "two", "three", "four");
        Map<Integer, List<String>> map = strings
                .stream()
                .collect(Collectors.groupingBy(String::length));
        print("Resultant Map: (with only classifier)", map);
        
        /*{
                "3" : [ "one", "two" ],
                "4" : [ "four" ],
                "5" : [ "three" ]
        }*/
        var expectedMap = Map.of(
                3, List.of("one", "two"),
                4, List.of("four"),
                5, List.of("three")
        );
        Assertions.assertEquals(expectedMap, map);
    }
    
    @Test
    void testWithClassifierAndDownStreamCollector() {
        List<String> strings = List.of("one", "two", "three", "four");
        Map<Integer, Long> map = strings
                .stream()
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        print("Resultant Map: (with classifier and down stream collector)", map);

        /*
         * {
         *      "3": "2",
         *      "4": "1",
         *      "5": "1"
         * }
         */
        
        var expectedMap = Map.of(
                3, 2L,
                4, 1L,
                5, 1L
        );
        
        Assertions.assertEquals(expectedMap, map);
    }
    
    private record Sale(String product, Instant date, Integer amount) {}
    private record City(String name, Integer population) {}
    private record State(String name, List<City> cities) {}
    
    private List<Sale> sales;
    private List<City> cities;
    private List<State> states;
    
    @BeforeEach
    void populateSales() {
        sales = List.of(
                new Sale("P-1", Instant.now().minus(300, ChronoUnit.DAYS), 20),
                new Sale("P-2", Instant.now().minus(300, ChronoUnit.DAYS), 19),
                new Sale("P-3", Instant.now().minus(300, ChronoUnit.DAYS), 18),
                new Sale("P-4", Instant.now().minus(300, ChronoUnit.DAYS), 17),
                new Sale("P-5", Instant.now().minus(300, ChronoUnit.DAYS), 16),
                new Sale("P-6", Instant.now().minus(300, ChronoUnit.DAYS), 15),
                new Sale("P-7", Instant.now().minus(300, ChronoUnit.DAYS), 14),
                new Sale("P-8", Instant.now().minus(300, ChronoUnit.DAYS), 13),
                new Sale("P-9", Instant.now().minus(1, ChronoUnit.HOURS), 12),
                new Sale("P-10", Instant.now().minus(1, ChronoUnit.HOURS), 11)
        );
        
        cities = List.of(
                new City("city-1", 99_000),
                new City("city-2", 99_000),
                new City("city-3", 99_000),
                new City("city-4", 99_000),
                new City("city-5", 99_000),
                new City("city-6", 99_000),
                new City("city-7", 99_000),
                new City("city-8", 99_000),
                new City("city-9", 99_000),
                new City("city-10", 99_000),
                new City("city-11", 101_000),
                new City("city-12", 102_000)
        );
        
        states = List.of(
                new State("Punjab", List.of(new City("Attock", 100_000), new City("Rawalpindi", 250_000))),
                new State("Sindh", List.of(new City("Karachi", 300_000), new City("Sakhar", 90_000))),
                new State("Blochistan", List.of(new City("Quetta", 150_000), new City("Qalat", 70_000)))
        );
    }
    
    @Test
    void calculateSaleTestWithBothLegacyAndModernWay() {
        //get all sales in current month
        //that will be p-9 and p-10
        //12+11=23

        Month currentMonth = LocalDate.now().getMonth();
        
        int amountSoldInCurrentMonth = 0;
        for (Sale sale: sales) {
            if (sale.date().atOffset(ZoneOffset.UTC).toLocalDate().getMonth().equals(currentMonth)) {
                amountSoldInCurrentMonth += sale.amount();
            }
        }
        
        print("amount sold in month:", amountSoldInCurrentMonth);
        
        Assertions.assertEquals(23, amountSoldInCurrentMonth);

        Integer totalSaleInCurrentMonth = sales
                .stream()
                .filter(sale -> sale.date().atOffset(ZoneOffset.UTC).toLocalDate().getMonth().equals(currentMonth))
                .map(Sale::amount)
                .reduce(0, Integer::sum);
        
        Assertions.assertEquals(23, totalSaleInCurrentMonth);        
        
    }
    
    @Test
    void cityPopulationTestWithBothLegacyAndModernWay() {
        //two cities having population 101K and 102k
        //total population of cities having population >100K
        //101K+102K=203K
        int sum = 0;
        for (City city: cities) {
            int population = city.population;
            if (population > 100_000) {
                sum += population;
            }
        }
        Assertions.assertEquals(203_000, sum);
        print("total population: ", sum);
        
        Integer totalPopulationLivingInCitiesHavingPopulationMoreThan100K = cities
                .stream()
                .map(city -> city.population)
                .filter(population -> population > 100_000)
                .reduce(0, Integer::sum);
        
        Assertions.assertEquals(203_000, totalPopulationLivingInCitiesHavingPopulationMoreThan100K);
        
    }
    
    @Test
    void testSpecializedStreams() {
        List<String> strings = List.of("one", "two", "three", "four");
        
        //get stats
        var result = strings
                .stream()
                .mapToInt(String::length)
                .summaryStatistics();
        print("stats: ", result);
    }
    
    
    @Test
    void testCount() {
        List<String> strings = List.of("one", "two", "three", "four");
        var count = strings
                .stream()
                .map(String::length)
                .filter(length -> length > 4)
                .count();
        
        Assertions.assertEquals(1, count);
    }
    
    
    @Test
    void testFlatMap() {
        //legacy way to calculate the populations
        int totalPopulation = 0;
        for (State state : states) {
            for (City city : state.cities()) {
                totalPopulation += city.population();
            }
        }
        
        print("Total population: ", totalPopulation);
        
        var totalPopulationWithFlatMap = states
                .stream().flatMap(state -> state.cities().stream())
                .peek(city -> log.info("city: {}", city))
                .mapToInt(City::population)
                .sum();
        
        Assertions.assertEquals(960000, totalPopulationWithFlatMap);
    }
    
    @Test
    void testFlatMap2() {
        String str = "18uh sd  sd22es ewe sds8"; //extract 18228
        List<Character> charList = str.chars().mapToObj(c -> (char) c).toList();
        
        
        Function<Character, Stream<Integer>> stringToIntParser = (Character string) -> {
            if (Character.isDigit(string)) {
                return Stream.of(Integer.parseInt(String.valueOf(string)));
            }
            return Stream.empty();
        };

        String number = charList.stream()
                .flatMap(stringToIntParser)
                .map(String::valueOf)
                .collect(Collectors.joining());

        Assertions.assertEquals("18228", number);
        
        String anotherNumber = charList
                .stream()
                .filter(Character::isDigit)
                .map(String::valueOf)
                .collect(Collectors.joining());

        Assertions.assertEquals("18228", anotherNumber);
    }
    
    @Test
    void testMultiMap() {
        String str = "18uh sd  sd22es ewe sds8"; //extract 18228
        List<Character> charList = str.chars()
                .mapToObj(c -> (char) c)
                .toList();
        
        String number = charList
                .stream()
                .mapMulti(((character, consumer) -> {
                    if (Character.isDigit(character)) 
                        consumer.accept(Integer.parseInt(String.valueOf(character)));
                }))
                .map(String::valueOf)
                .collect(Collectors.joining());
        
        Assertions.assertEquals("18228", number);
    }
    
    private record CustomizedAlphabet(String alphabet, Integer number) implements Comparable<CustomizedAlphabet> {
        @Override
        public int hashCode() {
            return number;
        }

        @Override
        public int compareTo(CustomizedAlphabet o) {
            return Integer.compare(this.number, o.number);
        }
    }
    
    @Test
    void testSortAndDistinct() {
        List<CustomizedAlphabet> ubaid = List.of(
                new CustomizedAlphabet("A", 3),
                new CustomizedAlphabet("B", 2),
                new CustomizedAlphabet("D", 5),
                new CustomizedAlphabet("D", 5),
                new CustomizedAlphabet("D", 5),
                new CustomizedAlphabet("I", 4),
                new CustomizedAlphabet("U", 1)
        );
        
        String nameWithoutSortAndDistinct = ubaid
                .stream()
                .map(CustomizedAlphabet::alphabet)
                .collect(Collectors.joining());
        
        Assertions.assertEquals("ABDDDIU", nameWithoutSortAndDistinct);
        
        String sortedName = ubaid
                .stream()
                .sorted()
                .distinct()
                .map(CustomizedAlphabet::alphabet)
                .collect(Collectors.joining());
        
        Assertions.assertEquals("UBAID", sortedName);
    }
    
    @Test
    void testLimitSkipAndDropTake() {
        List<Integer> ints = List.of(1, 1, 2, 9, 9, 4, 8, 7, 7, 6, 5, 4, 2, 1, 2, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        
        //sort this list
        //remove duplicates
        //I want to get numbers that are limited to first 5 and offset with 2
        //example: 3,4,5,6,7
        
        List<Integer> result = ints
                .stream()
                .sorted()
                .distinct()
                .skip(2)
                .limit(5)
                .toList();
        
        List<Integer> expectedResult = List.of(3, 4, 5, 6, 7);
        
        Assertions.assertEquals(expectedResult, result);
        
        
        List<Integer> resultWithTakeDrop = ints
                .stream()
                .sorted()
                .distinct()
                .dropWhile(number -> number < 3)
                .takeWhile(number -> number < 8)
                .toList();
        
        Assertions.assertEquals(expectedResult, resultWithTakeDrop);
    }
    
    @Test
    void concatenationTest() {
        List<Integer> list0 = List.of(1, 2, 3);
        List<Integer> list1 = List.of(4, 5, 6);
        List<Integer> list2 = List.of(7, 8, 9);
        
        //using Stream concat
        List<Integer> resultWithConcat = Stream
                .concat(Stream.concat(list0.stream(), list1.stream()), list2.stream())
                .toList();
        
        List<Integer> expectedList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        
        Assertions.assertEquals(expectedList, resultWithConcat);
        
        //using flat map
        List<Integer> resultWithFlatMap = Stream
                .of(list0.stream(), list1.stream(), list2.stream())
                .flatMap(Function.identity())
                .toList();
        
        Assertions.assertEquals(expectedList, resultWithFlatMap);
    }

    void print(String message, Object obj) {
        try {
            log.debug("{}: {}", message, mapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
