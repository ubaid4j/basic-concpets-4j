package dev.ubaid.labs.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class CollectorTest {

    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
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
    
    void print(Object obj) {
        try {
            log.debug(mapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
