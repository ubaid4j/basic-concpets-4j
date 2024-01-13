package dev.ubaid.labs.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Slf4j
public class CustomCollectorTest {
    
    @Test
    void createListFrom0To4FromStream() {
      
        class ArrayListSupplier implements Supplier<List<Integer>> {
            @Override
            public List<Integer> get() {
                return new ArrayList<>();
            }
        }
        
        class AddElementToArrayListConsumer implements BiConsumer<List<Integer>, Integer> {
            @Override
            public void accept(List<Integer> integers, Integer integer) {
                integers.add(integer);
            }
        }

        class AddListIntoListConsumer implements BiConsumer<List<Integer>, List<Integer>> {
            @Override
            public void accept(List<Integer> integers, List<Integer> integers2) {
                integers.addAll(integers2);
            }
        }
        
        
        List<Integer> result = IntStream.range(0, 5)
                .boxed()
                .collect(new ArrayListSupplier(), new AddElementToArrayListConsumer(), new AddListIntoListConsumer());
        
        log.info("{}", result);

        Assertions.assertEquals(List.of(0, 1, 2, 3, 4), result);
        
        Supplier<List<Integer>> arrayListSupplier = ArrayList::new;
        ObjIntConsumer<List<Integer>> addElementToList = Collection::add;
        BiConsumer<List<Integer>, List<Integer>> addLists = Collection::addAll;
        
        List<Integer> result2 = IntStream.range(0,5)
                .collect(arrayListSupplier, addElementToList, addLists);
        Assertions.assertEquals(List.of(0, 1, 2, 3, 4), result2);
    }
    
    @Test
    void joinStreamOfIntsFrom0To4() {
        Supplier<StringBuilder> supplier = StringBuilder::new;
        ObjIntConsumer<StringBuilder> accumulator = StringBuilder::append;
        BiConsumer<StringBuilder, StringBuilder> combiner = StringBuilder::append;
        
        StringBuilder stringBuilder = IntStream.range(0,5)
                .collect(supplier, accumulator, combiner);
        
        Assertions.assertEquals("01234", stringBuilder.toString());
    }
    
    @Test
    void simpleHistogram() {
        List<String> str = List.of("a", "b", "b", "b", "c", "d", "a", "b", "c", "d", "a", "c", "c", "c", "c", "c", "x");

        //b-4
        //a-3

        Map<Long, List<String>> result = str.stream().parallel()
                .collect(groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream().parallel()
                .collect(groupingBy(Map.Entry::getValue, TreeMap::new, mapping(Map.Entry::getKey, toList())));
        
        log.info("Tree map: {}", result);
        
    }
    
    @Test
    void maxOccurrenceOfCharacter() {
        List<String> str = List.of("a", "b", "b", "b", "c", "d", "a", "b", "c", "d", "a");

        //b-4
        //a-3
        TreeMap<Long, List<String>> result = str.stream()
                .collect(groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .collect(groupingBy(Map.Entry::getValue, TreeMap::new, mapping(Map.Entry::getKey, toList())));
        
        Map.Entry<Long, List<String>> maxOccurredChar = result.lastEntry();
        
        log.info("{}", result);
        Assertions.assertEquals(Map.of(2L, List.of("c", "d"), 3L, List.of("a"), 4L, List.of("b")), result);
        
        log.info("max occurred: {}", maxOccurredChar);
        Assertions.assertEquals(Map.entry(4L, List.of("b")), maxOccurredChar);
        
        Map.Entry<Long, List<String>> secondMaxOccurredChar = result.lowerEntry(maxOccurredChar.getKey());
        log.info("second max occurred: {}", secondMaxOccurredChar);
        Assertions.assertEquals(Map.entry(3L, List.of("a")), secondMaxOccurredChar);
    }
    
    @Test
    void maxOccurrenceOfCharacterByFinisher() {
        List<String> str = List.of("a", "b", "b", "b", "c", "d", "a", "b", "c", "d", "a");
        
        Function<Map<Long, List<String>>, String> joinString = map -> {
            List<String> chars = map.entrySet().stream()
                    .max(Map.Entry.comparingByKey())
                    .orElseThrow()
                    .getValue();
            return String.join(",", chars);
        };
        
        
        String result = str.stream()
                .collect(groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .collect(Collectors.collectingAndThen(groupingBy(Map.Entry::getValue, mapping(Map.Entry::getKey, toList())), joinString));
        
        Assertions.assertEquals("b", result);
    }
 
    enum Color {
        RED, BLUE, WHITE, YELLOW
    }
    
    enum Engine {
        ELECTRIC, HYBRID, GAS
    }
    
    enum Drive {
        WD2, WD4
    }
    
    interface Vehicle {
        
    }
    
    record Car(Color color, Engine engine, Drive drive, int passengers) implements Vehicle {}
    
    record Truck(Engine engine, Drive drive, int weight) implements Vehicle {}
    
    @Test
    void allVehiclesHavingElectricEngine() {
        List<Vehicle> vehicles = List.of(
                new Car(Color.RED, Engine.ELECTRIC, Drive.WD4, 10),
                new Car(Color.RED, Engine.HYBRID, Drive.WD2, 8),
                new Car(Color.RED, Engine.GAS, Drive.WD2, 12),
                new Car(Color.RED, Engine.GAS, Drive.WD2, 12),
                new Truck(Engine.HYBRID, Drive.WD4, 500),
                new Truck(Engine.ELECTRIC, Drive.WD2, 800),
                new Truck(Engine.HYBRID, Drive.WD4, 500),
                new Truck(Engine.GAS, Drive.WD4, 500)
        );
        
        List<Vehicle> electricEngineVehicles = vehicles
                .stream()
                .collect(Collectors.teeing(
                        Collectors.filtering(vehicle -> vehicle instanceof Car car && Objects.equals(car.engine, Engine.ELECTRIC), Collectors.toList()),
                        Collectors.filtering(vehicle ->  vehicle instanceof Truck truck && Objects.equals(truck.engine, Engine.ELECTRIC), Collectors.toList()),
                        (cars, trucks) -> {
                            cars.addAll(trucks);
                            return cars;
                        }));
        
        Assertions.assertEquals(List.of(new Car(Color.RED, Engine.ELECTRIC, Drive.WD4, 10), new Truck(Engine.ELECTRIC, Drive.WD2, 800)), electricEngineVehicles);
    }
}
