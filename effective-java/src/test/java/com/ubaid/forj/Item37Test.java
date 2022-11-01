package com.ubaid.forj;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import com.ubaid.forj.Item37Test.PhaseDoNotDoThis.Transition;
import com.ubaid.forj.Item37Test.Plant.LifeCycle;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

// ITEM 37: USE ENUMMAP INSTEAD OF ORDINAL INDEXING
public class Item37Test {

    static class Plant {
        enum LifeCycle {
            ANNUAL, PERENNIAL, BIENNIAL
        }
        
        final String name;
        final LifeCycle lifeCycle;
        
        Plant(String name, LifeCycle lifeCycle) {
            this.name = name;
            this.lifeCycle = lifeCycle;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    
    static List<Plant> garden = new ArrayList<>();
    static {
        garden.add(new Plant("A1", LifeCycle.ANNUAL));
        garden.add(new Plant("B1", LifeCycle.BIENNIAL));
        garden.add(new Plant("P1", LifeCycle.PERENNIAL));
        garden.add(new Plant("A2", LifeCycle.ANNUAL));
        garden.add(new Plant("A3", LifeCycle.ANNUAL));
    }
    
    @Test
    void plantsByLifeCycle_dontDoThis() {
        Set<Plant>[] plantsByLifeCycle = (Set<Plant>[]) new Set[LifeCycle.values().length];
        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            plantsByLifeCycle[i] = new HashSet<>();
        }
        
        for (Plant p : garden) {
            plantsByLifeCycle[p.lifeCycle.ordinal()].add(p);
        }
        
        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            System.out.printf("%s: %s%n", 
                Plant.LifeCycle.values()[i], plantsByLifeCycle[i]);
        }
    }
    
    @Test
    void plantsByLifeCycleUsingEnumMaps() {
        Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle = 
            new EnumMap<>(Plant.LifeCycle.class);
        
        for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
            plantsByLifeCycle.put(lc, new HashSet<>());
        }
        
        for (Plant p: garden) {
            plantsByLifeCycle.get(p.lifeCycle).add(p);
        }

        System.out.println(plantsByLifeCycle);
    }
    
    @Test
    void plantsByLifeCycleUsingMapWithStream() {
        Map<Plant.LifeCycle, List<Plant>> plantsByLifeCycle = garden
            .stream()
            .collect(groupingBy(p -> p.lifeCycle));
        System.out.println(plantsByLifeCycle);
    }
    
    @Test
    void plantsByLifeCycleUsingEnumMapWithStream() {
        EnumMap<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle = garden
            .stream()
            .collect(Collectors.groupingBy(p -> p.lifeCycle, () -> new EnumMap<>(LifeCycle.class), toSet()));
        System.out.println(plantsByLifeCycle);
    }
    
    @Test
    void plantsByLifeCycleUsingEnumMapWithStreamVerbose() {
        Function<Plant, LifeCycle> classifier = p -> p.lifeCycle;
        Supplier<EnumMap<LifeCycle, Set<Plant>>> mapFactory = () -> new EnumMap<>(LifeCycle.class);
        var plantsByLifeCycle = garden
            .stream()
            .collect(groupingBy(classifier, mapFactory, toSet()));
        System.out.println(plantsByLifeCycle);
    }
    
    enum PhaseDoNotDoThis {
        SOLID, LIQUID, GAS;
        
        enum Transition {
            MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;
            
            private static final Transition[][] TRANSITIONS = {
                {null, MELT, SUBLIME},
                {FREEZE, null, BOIL},
                {DEPOSIT, CONDENSE, null}
            };
            
            public static Transition from(PhaseDoNotDoThis from, PhaseDoNotDoThis to) {
                return TRANSITIONS[from.ordinal()][to.ordinal()];
            }
        }
    }
    
    enum Phase {
        SOLID, LIQUID, GAS, PLASMA;
        
        enum Transition {
            MELT(SOLID, LIQUID),
            FREEZE(LIQUID, SOLID),
            BOIL(LIQUID, GAS),
            CONDENSE(GAS, LIQUID),
            SUBLIME(SOLID, GAS),
            DEPOSIT(GAS, SOLID),
            IONIZE(GAS, PLASMA),
            DEIONIZE(PLASMA, GAS);


            private final Phase from;
            private final Phase to;

            Transition(Phase from, Phase to) {
                this.from = from;
                this.to = to;
            }
            
            private static final Function<Transition, Phase> classifier = t -> t.from;
            private static final Function<Transition, Phase> nestedClassifier = t -> t.to;
            private static final BinaryOperator<Transition> mergeStrategy = (x, y) -> y;
            private static final Supplier<Map<Phase, Map<Phase, Transition>>> mapFactory = () -> new EnumMap<>(Phase.class);
            private static final Supplier<Map<Phase, Transition>> nestedMapFactory = () -> new EnumMap<>(Phase.class);
            
            private static final Map<Phase, Map<Phase, Transition>> m = Stream
                .of(values())
                .collect(groupingBy(classifier, mapFactory, 
                            toMap(nestedClassifier, identity(), mergeStrategy, nestedMapFactory)));
            
            public static Transition from(Phase from, Phase to) {
                return m.get(from).get(to);
            }
        }
    }
    
    
}
