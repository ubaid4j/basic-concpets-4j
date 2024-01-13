package dev.ubaid.labs.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class OptionalTest {

    private record Author(String name) implements Comparable<Author> {
        @Override
        public int compareTo(Author o) {
            return this.name.compareTo(o.name);
        }
    }

    private record Article(String title, int inceptionYear, List<Author> authors) {
    }


    private record PairOfAuthors(Author first, Author second) {
        public static Optional<PairOfAuthors> of(Author author1, Author author2) {
            if (author1.compareTo(author2) > 0) {
                return Optional.of(new PairOfAuthors(author1, author2));
            }
            return Optional.empty();
        }
    }


    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(new JavaTimeModule());
    }

    List<Article> articles;
    @BeforeEach
    void beforeEach() {
        articles = List.of(
                new Article("A", 2021, List.of(new Author("ubaid"), new Author("attiq"))),
                new Article("A", 2024, List.of(new Author("ubaid"), new Author("attiq"))),
                new Article("A", 2024, List.of(new Author("ubaid"), new Author("attiq"))),
                new Article("A", 2022, List.of(new Author("ali"), new Author("attiq"))),
                new Article("B", 2023, List.of(new Author("shahab"), new Author("ali"))),
                new Article("C", 2023, List.of(new Author("attiq"), new Author("saqib"))),
                new Article("D", 2023, List.of(new Author("ubaid"), new Author("attif"))),
                new Article("E", 2024, List.of(new Author("ubaid"), new Author("attiq"), new Author("adnan")))
        );
    }

    @Test
    void optional() {
        Optional<String> opt = Optional.of("ubaid");
        Assertions.assertEquals("ubaid", opt.orElseThrow());

        Optional<String> empty = Optional.empty();
        Assertions.assertThrowsExactly(NoSuchElementException.class, empty::get);

        String name = empty.orElse("ubaid");
        Assertions.assertEquals("ubaid", name);
    }

    @Test
    void optionalMap() {
        record Customer(UUID uuid, String name) {
        }

        UUID uuid = UUID.randomUUID();

        List<Customer> customers = List.of(new Customer(uuid, "ubaid"));

        String customerName = customers.stream().filter(customer -> Objects.equals(customer.uuid, uuid))
                .findFirst()
                .map(customer -> customer.name)
                .orElse("Unknown");

        Assertions.assertEquals("ubaid", customerName);
    }

    @Test
    void twoAuthorsThatPublishedTheMost() {
        
        BiFunction<Article, Author, Stream<PairOfAuthors>> buildPairsOfAuthor = (article, firstAuthor) -> article
                .authors()
                .stream()
                .flatMap(secondAuthor -> PairOfAuthors.of(firstAuthor, secondAuthor).stream());

        Function<Article, Stream<PairOfAuthors>> toPairOfAuthors = article -> article
                .authors()
                .stream()
                .flatMap(firstAuthor -> buildPairsOfAuthor.apply(article, firstAuthor));

        Map<PairOfAuthors, Long> pairOfAuthors = articles
                .stream()
                .flatMap(toPairOfAuthors)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        print("Pairs of authors: {}", pairOfAuthors);


        Function<Map<PairOfAuthors, Long>, Map.Entry<PairOfAuthors, Long>> maxExtractor = map -> map
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();

        Map.Entry<PairOfAuthors, Long> twoAuthorsThatPublishedMost = pairOfAuthors
                .entrySet()
                .stream()
                .collect(Collectors.collectingAndThen(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue), maxExtractor));

        Assertions.assertEquals(Map.entry(new PairOfAuthors(new Author("ubaid"), new Author("attiq")), 4L), twoAuthorsThatPublishedMost);

        Collector<PairOfAuthors, ?, Map.Entry<PairOfAuthors, Long>> mostPublishedPairOfAuthor =
                Collectors.collectingAndThen(Collectors.groupingBy(Function.identity(), Collectors.counting()), maxExtractor);


        Map.Entry<PairOfAuthors, Long> twoAuthorsThatPublishedMost2 = articles
                .stream()
                .flatMap(toPairOfAuthors)
                .collect(mostPublishedPairOfAuthor);

        Assertions.assertEquals(Map.entry(new PairOfAuthors(new Author("ubaid"), new Author("attiq")), 4L), twoAuthorsThatPublishedMost2);

        Map.Entry<PairOfAuthors, Long> twoAuthorsThatPublishedMost3 = articles
                .stream()
                .collect(Collectors.flatMapping(toPairOfAuthors, mostPublishedPairOfAuthor));

        Assertions.assertEquals(Map.entry(new PairOfAuthors(new Author("ubaid"), new Author("attiq")), 4L), twoAuthorsThatPublishedMost3);

        Collector<Article, ?, Map.Entry<PairOfAuthors, Long>> mostPublishedPairOfAuthorsFromList =
                Collectors.flatMapping(toPairOfAuthors, mostPublishedPairOfAuthor);

        Map<Integer, Map.Entry<PairOfAuthors, Long>> twoAuthorsThatPublishedTheMostTogetherPerYear = articles
                .stream()
                .collect(Collectors.groupingBy(Article::inceptionYear, mostPublishedPairOfAuthorsFromList));

        print("twoAuthorsThatPublishedTheMostTogetherPerYear: ", twoAuthorsThatPublishedTheMostTogetherPerYear);

        Assertions.assertEquals(Map.entry(new PairOfAuthors(new Author("ubaid"), new Author("attiq")), 3L), twoAuthorsThatPublishedTheMostTogetherPerYear.get(2024));
    }

    @Test
    void twoAuthorsThatPublishedTheMostPerYear() {
        
        BiFunction<Article, Author, Stream<PairOfAuthors>> buildPairsOfAuthor = (author, firstAuthor) -> author
                .authors()
                .stream()
                .flatMap(secondAuthor -> PairOfAuthors.of(firstAuthor, secondAuthor).stream());
        
        Function<Article, Stream<PairOfAuthors>> toPairOfAuthors = article -> article
                .authors()
                .stream()
                .flatMap(firstAuthor -> buildPairsOfAuthor.apply(article, firstAuthor));
        
        
        Function<Map<PairOfAuthors, Long>, Optional<Map.Entry<PairOfAuthors, Long>>> maxExtractor = map -> map
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());
        
        
        Collector<PairOfAuthors, ?, Optional<Map.Entry<PairOfAuthors, Long>>> mostPublishedPairOfAuthor = Collectors
                .collectingAndThen(Collectors.groupingBy(Function.identity(), Collectors.counting()), maxExtractor);
        
        Collector<Article, ?, Optional<Map.Entry<PairOfAuthors, Long>>> mostPublishedPairOfAuthorFormList = Collectors
                .flatMapping(toPairOfAuthors, mostPublishedPairOfAuthor);
        
        
        Map<Integer, Map.Entry<PairOfAuthors, Long>> twoAuthorThatPublishedTheMostTogetherPerYear = articles
                .stream()
                .collect(Collectors.groupingBy(Article::inceptionYear, mostPublishedPairOfAuthorFormList))
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().map(value -> Map.entry(entry.getKey(), value)).stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Assertions.assertEquals(Map.entry(new PairOfAuthors(new Author("ubaid"), new Author("attiq")), 3L), twoAuthorThatPublishedTheMostTogetherPerYear.get(2024));

    }


    void print(String message, Object obj) {
        try {
            log.debug("{}: {}", message, mapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
