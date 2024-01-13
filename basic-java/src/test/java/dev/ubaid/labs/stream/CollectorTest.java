package dev.ubaid.labs.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class CollectorTest {

    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(new JavaTimeModule());
    }


    @Test
    void toCollectionTest() {
        List<Integer> numbers =
                IntStream.range(0, 10)
                        .boxed()
                        .collect(Collectors.toList());
        
        log.debug("number: {}", numbers);

        Set<Integer> evenNumbers = 
                IntStream.range(0, 10)
                        .map(number -> number/2)
                        .boxed()
                        .collect(Collectors.toSet());
        
        log.debug("even numbers: {}", evenNumbers);

        LinkedList<Integer> numbers2 = 
                IntStream.range(0, 10)
                        .boxed()
                        .collect(Collectors.toCollection(LinkedList::new));
        
        log.debug("numbers (linked list): {}", numbers2);
    }
    
    @Test
    void countTest() {
        Collection<String> names = List.of("name1", "name2");
        long count = names.stream().count();
        long countWithCollector = names
                .stream()
                .collect(Collectors.counting());
        
        log.debug("count: {}", count);
        log.debug("count with collector: {}", countWithCollector);
    }
    
    @Test
    void joinTest() {
        String joined = 
                IntStream.range(0, 10)
                        .boxed()
                        .map(Object::toString)
                        .collect(Collectors.joining());
        
        log.debug("joined: {}", joined);
        
        joined
                = IntStream.range(0, 10)
                .boxed()
                .map(Object::toString)
                .collect(Collectors.joining(",", "{", "}"));
        
        log.debug("joined with delimiter, prefix and suffix: {}", joined);
    }
    
    @Test
    void joinTest2() {
        String result = IntStream.range(0, 2)
                .boxed()
                .map(Object::toString)
                .collect(Collectors.joining(",", "{", "}"));
        
        String expectedResult = "{0,1}";
        Assertions.assertEquals(expectedResult, result);
    }
    
    @Test
    void listToMapTest() {
        Collection<String> strings = List.of(
                "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"
        );

        Map<Boolean, List<String>> map = strings
                .stream()
                .collect(Collectors.partitioningBy(s -> s.length() > 4));
        
        log.debug("map: {}", map);
    }
    
    @Test
    void partition() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        Map<Boolean, List<Integer>> map = stream.collect(Collectors.partitioningBy(i -> (i%2) == 0));
        List<Integer> even = map.get(true);
        List<Integer> odd = map.get(false);
        Assertions.assertEquals(List.of(2, 4), even);
        Assertions.assertEquals(List.of(1, 3, 5), odd); 
    }

    @Test
    void testGroupingBy() {
        Collection<String> strings = List.of(
                "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"
        );
        print(strings);
        
        Map<Integer, List<String>> map = strings
                .stream()
                .collect(Collectors.groupingBy(String::length));
        
        print(map);
        
        Map<Integer, Long> counts = strings
                .stream()
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        print(counts);
        
        
        Map<Integer, String> newMap = strings
                .stream()
                .collect(Collectors.groupingBy(String::length, Collectors.joining(",")));
        print(newMap);
    }
    
    @Test
    void groupingBy() {
        List<String> str = List.of("a", "b", "b", "b", "c", "d", "a", "b", "c", "d", "a");

        Map<Long, List<String>> map = str.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
        print(map);
        Assertions.assertEquals(Map.of(4L, List.of("b"), 3L, List.of("a"), 2L, List.of("c", "d")), map);
    }
    
    @Test
    void groupingBy2() {
        enum Status {
            CREATED,
            LABELED,
            READY_FOR_DELIVERY
        }
        record Order (UUID uuid, Instant dateCreated, Status status) {}
     
        List<Order> orders = List.of(
                new Order(UUID.randomUUID(), Instant.now().minus(1, ChronoUnit.DAYS), Status.CREATED),
                new Order(UUID.randomUUID(), Instant.now().minus(2, ChronoUnit.DAYS), Status.READY_FOR_DELIVERY),
                new Order(UUID.randomUUID(), Instant.now(), Status.CREATED),
                new Order(UUID.randomUUID(), Instant.now(), Status.LABELED)
        );

        //date - orders count
        Map<LocalDate, Long> map = orders.stream()
                .collect(Collectors.groupingBy(order -> LocalDate.ofInstant(order.dateCreated, ZoneId.of("UTC")), Collectors.counting()));
        
        print(map);
        
        LocalDate today = Instant.now().atOffset(ZoneOffset.UTC).toLocalDate();
        long orderCounts = map.get(today);
        Assertions.assertEquals(2, orderCounts);
        
    }
    
    @Test
    void mapping() {
        List<String> str = List.of("a", "b", "b", "b", "c", "d", "a", "b", "c", "d", "a");
        Map<String, Long> map = str.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.mapping(Function.identity(), Collectors.counting())));
        
        Assertions.assertEquals(Map.of("a", 3L, "b", 4L, "c", 2L, "d", 2L), map);
        
        print(map);
    }
    
    @Test
    void testToMapPattern() {
        Collection<String> strings = List.of(
                "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"
        );
        print(strings);

        Map<Integer, String> map = strings
                .stream()
                .collect(Collectors.toMap(
                        element -> element.length(),
                        element -> element,
                        (element1, element2) -> element1 + ", " + element2));
        
        print(map);
    }
    
    
    
    @Test
    void testUserCache() {
        record User(Integer id, String username) {}
        
        List<User> users = List.of(
                new User(1, "ubaid"),
                new User(2, "attiq")
        );
        
        print(users);
        
        //User::id
        Function<User, Integer> toUserId = new Function<User, Integer>() {
            @Override
            public Integer apply(User user) {
                return user.id;
            }
        };
        
        Map<Integer, User> userMap = users
                .stream()
                .collect(Collectors.toMap(toUserId, Function.identity()));
        
        print(userMap);
    }
    
    @Test
    void toMap() {
        record User(UUID uuid, String name, Integer age) {}
        
        List<User> users = List.of(
                new User(UUID.randomUUID(), "ubaid", 26),
                new User(UUID.randomUUID(), "attiq", 23),
                new User(UUID.randomUUID(), "ali", 26)
        );

        BinaryOperator<String> mergeNames = new BinaryOperator<String>() {
            @Override
            public String apply(String s, String s2) {
                return STR."\{s},\{s2}";
            }
        };
        
        var map = users.stream()
                .collect(Collectors.toMap(u -> u.age, u -> u.name, mergeNames));
        
        print(map);
        
        
    }
    
    @Test
    void extractNonAmbiguousMax() {
        Collection<String> strings = List.of(
                "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"
        );
        print(strings);

        //histogram 
        //length of character -> number of occurrences of words
        
        Map<Integer, Long> histogram = strings
                .stream()
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        
        print(histogram);
        
        /*{
            "3" : 4,
            "4" : 3,
            "5" : 3,
            "6" : 2
        }*/
    
        //max entry of value -> 3 : 4
        
        Map.Entry<Integer, Long> maxEntry = histogram
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();
        
        print(maxEntry);

        Assertions.assertEquals(maxEntry.getValue(), 4);
        Assertions.assertEquals(maxEntry.getKey(), 3);
        
        
        NumberOfLength numberOfLength = histogram
                .entrySet()
                .stream()
                .map(NumberOfLength::fromEntry)
                .max(NumberOfLength.comparingByNumber())
                .orElseThrow();
        
        
        print(numberOfLength);
        Assertions.assertEquals(new NumberOfLength(3, 4), numberOfLength);
        
    }

    //reduce generics
    record NumberOfLength(int length, long number) {
        static NumberOfLength fromEntry(Map.Entry<Integer, Long> entry) {
            return new NumberOfLength(entry.getKey(), entry.getValue());
        }

        static Comparator<NumberOfLength> comparingByNumber() {
            return Comparator.comparing(NumberOfLength::number);
        }
    }
    
    @Test
    void extractingAmbiguousMax() {
        Collection<String> strings = List.of(
                "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"
        );
        print("raw list: ", strings);

        Map<Integer, Long> histogram = strings
                .stream()
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        
        print("histogram: ", histogram);
        /*{
            "3" : 3,
            "4" : 3,
            "5" : 3,
            "6" : 2
          }
        */
        
        Map<Long, List<NumberOfLength>> groupedByNumberOfLength = 
                histogram
                        .entrySet()
                        .stream()
                        .map(NumberOfLength::fromEntry)
                        .collect(Collectors.groupingBy(NumberOfLength::number));
        
        print("grouped by number of length: ", groupedByNumberOfLength);
        /*{
          "2" : [ {
            "length" : 6,
            "number" : 2
          } ],
          "3" : [ {
            "length" : 3,
            "number" : 3
          }, {
            "length" : 4,
            "number" : 3
          }, {
            "length" : 5,
            "number" : 3
          } ]
        }*/
        
        //we need max number of times of occurrence of length of words
        //that is 3 but there are three times 3
        //we need 3 -> [3,4,5] instead of just 3
        Map<Long, List<Integer>> groupedByNumberOfLengthEnhanced =
                histogram
                        .entrySet()
                        .stream()
                        .map(NumberOfLength::fromEntry)
                        .collect(Collectors.groupingBy(NumberOfLength::number, Collectors.mapping(NumberOfLength::length, Collectors.toList())));
        
        print("grouped by number of length (eliminating number of length object)", groupedByNumberOfLengthEnhanced);
        /*{
            "2" : [ 6 ],
            "3" : [ 3, 4, 5 ]
        }*/
        
        //we need 3 -> [3,4,5] (the max entry)
        
        Map.Entry<Long, List<Integer>> maxEntry = groupedByNumberOfLengthEnhanced.entrySet()
                .stream()
                .max(Map.Entry.comparingByKey())
                .orElseThrow();
        print("max entry: ", maxEntry);
        
        Assertions.assertEquals(3, maxEntry.getKey());
        Assertions.assertEquals(List.of(3, 4, 5), maxEntry.getValue());
    }
    
    @Test
    @DisplayName(value = "Find character occurred maximum in given list")
    void maxValueFromMap() {
        List<String> str = List.of("a", "b", "b", "b", "c", "d", "a", "b", "c", "d", "a");

        Map<Long, String> result = str.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (k1, k2) -> STR."\{k1}.\{k2}"));

        print(result);

        var maxOccurredCharacterEntry = result
                .entrySet()
                .stream().max(Map.Entry.comparingByKey())
                .orElseThrow();
        
        Assertions.assertEquals("b", maxOccurredCharacterEntry.getValue());
        
        //second max occurred element
        result.remove(maxOccurredCharacterEntry.getKey());
        
        var secondMaxOccurredEntry = result
                .entrySet()
                .stream().max(Map.Entry.comparingByKey())
                .orElseThrow();
        
        Assertions.assertEquals("a", secondMaxOccurredEntry.getValue());
    }
    
    
    void print(Object obj) {
        try {
            log.debug(mapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    void print(String message, Object obj) {
        try {
            log.debug("{}: {}", message, mapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
