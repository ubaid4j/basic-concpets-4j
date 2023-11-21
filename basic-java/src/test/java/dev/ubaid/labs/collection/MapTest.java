package dev.ubaid.labs.collection;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
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