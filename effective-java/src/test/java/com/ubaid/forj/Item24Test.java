package com.ubaid.forj;

import com.ubaid.forj.Class1.NestedClass;
import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.jupiter.api.Test;

// ITEM 24: FAVOR STATIC MEMBER CLASSES OVER NONSTATIC
public class Item24Test {

    
    @Test
    void testGetContainingClassReferenceForNotStaticMemberClass() {
        Class1 c1 = new Class1("one");
        NestedClass ns = c1.getNestedClass();
        NestedClass nsBad = c1.new NestedClass();
    }
    
    @Test
    void testClass2() {
        Class2 class2 = new Class2();
        System.out.println(class2.getKeys());
    }
    
    @Test
    void mapTest() {
        Map<String, String> maps = new HashMap<>();
        maps.put("Hello", "World");
        Set<Entry<String, String>> entry = maps.entrySet();
        System.out.println(entry);
        entry.forEach(e -> {
            System.out.println(e);
            System.out.println(e);
        });
    }
    
    @Test
    void anonymousClassTest() {
        I1 anonymouseClass = (a1, a2, a3) -> {
            System.out.println(a1 + a2 + a3);
        };
        anonymouseClass.doThat(1, 2, 3);
    }
    
    @Test
    void testLocalClass() {
        class Local1 {
            int k;
            int v;

            @Override
            public String toString() {
                return "Local1{" +
                    "k=" + k +
                    ", v=" + v +
                    '}';
            }
        }
        
        Local1 l = new Local1();
        l.k = 1;
        l.v = 250;

        System.out.println(l);
        
    }
}

class Class1 {
    
    private final String name;

    public Class1(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    NestedClass getNestedClass() {
        return new NestedClass();
    }

    @Override
    public String toString() {
        return "Class1{" +
            "name='" + name + '\'' +
            '}';
    }

    class NestedClass {

        public NestedClass() {
            System.out.println(Class1.this.name);
        }

        @Override
        public String toString() {
            return "NestedClass{}";
        }
    }

    static class StaticNestedClass {

        public StaticNestedClass() {
            System.out.println(Class1.class.getName());
        }
    }


}

class Class2 {
    
    public List<Key> getKeys() {
        return List.of(new Key(1), new Key(2));
        
    }
    
    
    private class Key {
        private final int id;

        public Key(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Key{" +
                "id=" + id +
                '}';
        }
    }
    
}

interface I1 {
    void doThat(int arg1, int arg2, int arg3);
}