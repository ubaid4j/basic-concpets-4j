package com.ubaid.forj;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

// ITEM 33: CONSIDER TYPESAFE HETEROGENEOUS CONTAINERS
public class Item33Test {
    
    @Test
    void testFavorite() {
        Favorites favorites = new Favorites();
        favorites.putFavorite(String.class, "Java");
        favorites.putFavorite(Integer.class, 0xcafebabe);
        favorites.putFavorite(Class.class, Favorites.class);
        String favoriteString = favorites.getFavorite(String.class);
        int favoriteInteger = favorites.getFavorite(Integer.class);
        Class<?> favoriteClass = favorites.getFavorite(Class.class);
        System.out.printf("%s %x %s%n", favoriteString, favoriteInteger, favoriteClass.getSimpleName());
    }
    
    static class Favorites {

        private final Map<Class<?>, Object> map = new HashMap<>();
        
        public <T> void putFavorite(Class<T> type, T instance) {
            map.put(type, type.cast(instance));
        }

        public <T> T getFavorite(Class<T> type) {
            return type.cast(map.get(type));
        }
    }
}
