package dev.ubaid.labs.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamCreationTest {
    
    @Test
    void streamFromIterator() {
        List<Integer> list = List.of(1 ,2, 3, 5);
        Iterator<Integer> iterator = list.iterator();
        
        long estimatedSize = 10L;
        int characteristics = 0;

        Spliterator<Integer> spliterator = Spliterators.spliterator(iterator, estimatedSize, characteristics);
        Stream<Integer> stream = StreamSupport.stream(spliterator, true);

        List<Integer> result = stream
                .distinct()
                .toList();

        Assertions.assertEquals(list, result);
    }
    
    @Test
    void emptyStreamTest() {
        Stream<Integer> empty = Stream.empty();
        List<Integer> result = empty.toList();
        Assertions.assertEquals(List.of(), result);
    }
    
    @Test
    void createStreamFromOfAndArraysStreamMethod() {
        Stream<Integer> intStream = Stream.of(1, 2, 3);
        Assertions.assertEquals(List.of(1, 2, 3), intStream.toList());
        
        Integer[] array = new Integer[] {1, 2, 3};
        Stream<Integer> instStream2 = Arrays.stream(array);
        Assertions.assertEquals(List.of(1, 2, 3), instStream2.toList());
    }
    
    private static class NumberGenerator implements Supplier<Integer> {
        
        private final Random random = ThreadLocalRandom.current();
        
        @Override
        public Integer get() {
            return random.nextInt();
        }
    }
    
    @Test
    void createStreamFromSupplier() {
        Stream<Integer> stream = Stream.generate(new NumberGenerator());
        List<Integer> numbers = stream
                .limit(50)
                .toList();
        System.out.println(STR."Numbers: \{numbers}");
        Assertions.assertEquals(50, numbers.size());
    }
    
    @Test
    void createStreamFromUnaryOperatorAndSeed() {
        var series = Stream
                .iterate(1L, number -> number < 50_000_000L, number -> (number * 32)/2)
                .limit(50)
                .toList();
        System.out.println(series);
    }
    
    @Test
    void createStreamFromRange() {
        var sum = IntStream
                .range(20, 25)
                .sum();
        
        Assertions.assertEquals(20+21+22+23+24, sum);
    }
    
    record Percentage(Long count) {
        private static final DecimalFormat numberFormat = new DecimalFormat("#.##");
        private static final Function<Long, Integer> normalizeValue = count -> (int) ((count/1000.0) * 100);

        @Override
        public String toString() {
            String percentage = numberFormat.format((count /1000.0)*100);
            return STR."\{percentage}% & normal value \{normalValue()}";
        }
        
        public int normalValue() {
            return normalizeValue.apply(count);
        }
    }
    
    @Test
    void createStreamFromRandom() {
        Random random = new Random(314L);
        
        List<Integer> randomInts = random.ints(10, 1, 5)
                .boxed()
                .toList();

        System.out.println(randomInts);
        
        List<Boolean> booleans = random
                .doubles(1_000, 0d, 1d)
                .mapToObj(randomNumber -> randomNumber <= 0.8)
                .toList();
        
        long numberOfTrue = booleans
                .stream()
                .filter(Boolean::booleanValue)
                .count();
        
        Assertions.assertTrue(numberOfTrue > 750);
        
        List<String> letters = random.doubles(1_000, 0d, 1d)
                .mapToObj(rand -> 
                        rand < 0.5 ? "A" :
                        rand < 0.8 ? "B" : 
                        rand < 0.9 ? "C" : 
                                     "D")
                .toList();
        
        Map<String, Long> map = letters
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        map.forEach((key, value) -> {
            Percentage percentage = new Percentage(value);
            System.out.println(STR."\{key} :: \{percentage}");
            int normalValue = percentage.normalValue();
            switch (key) {
                case "A" -> Assertions.assertTrue(normalValue > 48 && normalValue < 52);
                case "B" -> Assertions.assertTrue(normalValue > 28 && normalValue < 42);
                case "C", "D" -> Assertions.assertTrue(normalValue > 8 && normalValue < 12);
            }
        });
    }
    
    @Test
    void createStreamFromString() {
        String name = "Ubaid";
        
        String result = name
                .chars()
                .mapToObj(Character::toString)
                .peek(System.out::print)
                .collect(Collectors.joining());
        
        Assertions.assertEquals(name, result);
        
    }
    
    @Test
    void createStreamFromTextFile() throws IOException {
        Path textFile2 = Path.of("src/test/resources/text2.txt");
        try (Stream<String> lines = Files.lines(textFile2)) {
            List<String> lines_  = lines.peek(System.out::println).toList();
            Assertions.assertEquals(10, lines_.size());
            Assertions.assertEquals("line10", lines_.getLast());
        }
    }
    
    @Test
    void createStreamFromRegularExpression() {
        String sentence = "My name is Ubaid";

        Pattern pattern = Pattern.compile(" ");
        
        Stream<String> stream = pattern.splitAsStream(sentence);
        
        List<String> list = stream.peek(System.out::println).toList();
        
        Assertions.assertEquals("Ubaid", list.getLast());
    }
    
    @Test
    void createStreamWithBuilderPattern() {
        Stream.Builder<String> builder = Stream.builder();
        builder.add("My")
                .add(" ")
                .add("name")
                .add(" ")
                .add("is")
                .add(" ")
                .add("Ubaid")
                .add(".");


        Stream<String> stream = builder.build();
        
        Assertions.assertThrows(IllegalStateException.class, () -> builder.add("exception"));
        
        
        List<String> list = stream
                .peek(System.out::print)
                .toList();
        
        Assertions.assertEquals(".", list.getLast());
    }
    
    @Test
    void createStreamOnHttpSource() throws IOException, InterruptedException {
        URI uri = URI.create("https://www.gutenberg.org/files/98/98-0.txt");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpResponse<Stream<String>> response = client.send(request, HttpResponse.BodyHandlers.ofLines());
        
        List<String> lines;
        
        try (Stream<String> stream = response.body()) {
            lines = stream
                    .dropWhile(line -> !line.equals("A TALE OF TWO CITIES"))
                    .takeWhile(line -> !line.equals("*** END OF THE PROJECT GUTENBERG EBOOK A TALE OF TWO CITIES ***"))
                    .toList();
        }
        
        Assertions.assertEquals(15904, lines.size());
    }
}
