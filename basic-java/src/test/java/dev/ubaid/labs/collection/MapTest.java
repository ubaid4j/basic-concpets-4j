package dev.ubaid.labs.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


@Slf4j
public class MapTest {

    @Test
    void synchronizedMapThrowConcurrentModificationExceptionOnModifyingMapWhileIterating() {
        Map<String, String> syncMap = Collections.synchronizedMap(new HashMap<>());
        
        syncMap.put("a", "aa");
        syncMap.put("b", "bb");
        syncMap.put("c", "cc");

        Assertions.assertThrows(ConcurrentModificationException.class, () -> {
            log.debug("Map: {}", syncMap);
            Set<String> keys = syncMap.keySet();
            log.debug("key set: {}", keys);
            Iterator<String> itr = keys.iterator();
            while (itr.hasNext()) {
                String key = itr.next();
                log.debug("Key: {}", key);
                if (key.equals("a")) {
                    itr.remove();
                    syncMap.put("a", "aaaa");
                }
            }
        });
        log.debug("Resultant Map: {}", syncMap);
    }
    
    @Test
    void explicitSynchronizationOnSynchronizeMapToPreventConcurrentModificationException() {
        Map<String, String> syncMap = Collections.synchronizedMap(new HashMap<>());

        syncMap.put("a", "aa");
        syncMap.put("b", "bb");
        syncMap.put("c", "cc");

        

        Assertions.assertDoesNotThrow(() -> {
            log.debug("Map: {}", syncMap);
            Set<Map.Entry<String, String>> entryset = syncMap.entrySet();

            synchronized (syncMap) {
                Iterator<Map.Entry<String, String>> iterator = entryset.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextLong(5) * 1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (Objects.equals("b", entry.getKey()) && Objects.equals("bb", entry.getValue())) {
                        iterator.remove();
                    }
                }
            }
            MapAdder adder = new MapAdder(syncMap);
            adder.start();
            adder.join();
        });
        log.debug("Resultant Map: {}", syncMap);
    }
    
    @Test
    void concurrentMapDoesNotThrowConcurrentModificationExceptionOnModifyingMapWhileIterating() {
        Map<String, String> concurrentMap = new ConcurrentHashMap<>();

        concurrentMap.put("a", "aa");
        concurrentMap.put("b", "bb");
        concurrentMap.put("c", "cc");

        Assertions.assertDoesNotThrow(() -> {
            log.debug("Map: {}", concurrentMap);
            Set<String> keys = concurrentMap.keySet();
            log.debug("key set: {}", keys);
            Iterator<String> itr = keys.iterator();
            while (itr.hasNext()) {
                String key = itr.next();
                log.debug("Key: {}", key);
                if (key.equals("a")) {
                    itr.remove();
                    concurrentMap.put("a", "aaaa");
                }
            }
            log.debug("Resultant Map: {}", concurrentMap);
        });
    }
    
    @Test
    @SneakyThrows
    void synchronizedMapCannotBeAccessedByTwoThreadsConcurrently() {
        Map<String, String> syncMap = Collections.synchronizedMap(new HashMap<>());

        syncMap.put("a", "aa");
        syncMap.put("b", "bb");
        syncMap.put("c", "cc");

        MapPrinter printer = new MapPrinter(syncMap);
        printer.start();
        MapAdder adder = new MapAdder(syncMap);
        adder.start();
        
        printer.join();
        adder.join();
    }
    
    @Test
    void mapTest() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("one", 1);
        map.put("two", null);
        map.put("three", 3);
        map.put("four", null);
        map.put("five", 5);
        
        Assertions.assertThrowsExactly(NullPointerException.class, () -> {
            for (int value : map.values()) {

            }
        });
        
        for (String key : map.keySet()) {
            map.putIfAbsent(key, -1);
        }
        
        for (int value : map.values()) {
            log.info("value: {}", value);
        }
    }
    
    @Test
    void mapTest2() {
        Map<Integer, String> map = new LinkedHashMap<>();
        
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");
        map.put(6, "six");
        map.put(7, "seven");
        map.put(8, "eight");
        
        Set<Integer> keys = map.keySet();
        log.info("Keys: {}", keys);

        Collection<String> values = map.values();
        log.info("values: {}", values);
        
        Set<Map.Entry<Integer, String>> entries = map.entrySet();
        log.info("entries: {}", entries);
        
    }
    
    @Test
    void computeTest() {
        Map<Integer, List<String>> ageMap = new LinkedHashMap<>();
        ageMap.put(26, new ArrayList<>(List.of("ubaid")));
        ageMap.put(23, new ArrayList<>(List.of("attiq")));
        
        log.info("age map: {}", ageMap);
        
        ageMap.computeIfAbsent(25, key -> List.of("Ali"));

        log.info("age map: {}", ageMap);

        ageMap.computeIfPresent(26, (key, value) -> List.copyOf(value));

        log.info("age map: {}", ageMap);

        ageMap.computeIfAbsent(27, key -> new ArrayList<>()).add("Shahid");
        log.info("age map: {}", ageMap);

        ageMap.computeIfAbsent(27, key -> new ArrayList<>()).add("Shahzad");
        log.info("age map: {}", ageMap);

    }
    
    @Test
    void mergeTest() {
        List<String> list = List.of("one", "two", "three", "four", "five", "six");
        
        //3 -> one,two,six
        //4 -> four,five
        //5 -> three
        
        Map<Integer, List<String>> map = new HashMap<>();
        
        for (String str : list) {
            Integer length = str.length();
            map.computeIfAbsent(length, _ -> new ArrayList<>()).add(str);
            
        }
        Map<Integer, List<String>> expectedMap = Map.ofEntries(
                Map.entry(3, List.of("one", "two", "six")),
                Map.entry(4, List.of("four", "five")),
                Map.entry(5, List.of("three"))
        );
        
        Assertions.assertEquals(expectedMap, map);
        
        
        Map<Integer, String> map2 = new HashMap<>();
        
        for (String str : list) {
            Integer length = str.length();
            map2.merge(length, str, (existingVal, newVal) -> STR."\{existingVal}, \{newVal}");
        }
        
        Map<Integer, String> expectedMap2 = Map.ofEntries(
                Map.entry(3, "one, two, six"),
                Map.entry(4, "four, five"),
                Map.entry(5, "three")
        );
        
        Assertions.assertEquals(expectedMap2, map2);
     
        
        Map<Integer, String> map3 = new HashMap<>();
        for (String str : list) {
            Integer length = str.length();
            map3.compute(length, (key, value) -> {
                if (Objects.isNull(value)) {
                    return str;
                }
               return STR."\{value}, \{str}";
            });
        }
        
        Assertions.assertEquals(expectedMap2, map3);
        
    }
}

@RequiredArgsConstructor
@Slf4j
class MapPrinter extends Thread {

    private final Map<String, String> storage;
    
    @Override
    public void run() {
        synchronized (storage) {
            Set<String> keys = storage.keySet();
            for (String key : keys) {
                log.debug("Key: {}", key);
                log.debug("Value: {}", storage.get(key));
                try {
                    Thread.sleep((long) (ThreadLocalRandom.current().nextDouble() * 1000.0));
                } catch (InterruptedException e) {
                    log.error("Interrupted exp");
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

@RequiredArgsConstructor
@Slf4j
class MapAdder extends Thread {
    private final Map<String, String> storage;

    @Override
    public void run() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(5) * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        synchronized (storage) {
            log.debug("current map: {}", storage);
            log.debug("adding more key values");
            for (int i = 0; i < 10; i++) {
                storage.put(String.valueOf(i), String.valueOf(i));
            }
            log.debug("resultant map: {}", storage);
        }
    }
}