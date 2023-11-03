package dev.ubaid.labs.thread;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class LiveLockTest {

    @Test
    void simulateLiveLock() throws InterruptedException {
        // Total number of left/right moves in all simulations ran so far.
        // This divided by the number of iterations is an indicator of how long two persons
        // remain in a lovelock,
        int totalMoves = 0;
        // Left/right moves in the current iteration.
        int moves = 0;

        for (int i = 1; i < 100; i++) {
            moves = run();
            totalMoves += moves;
            debug(String.format("Iteration %02d, Moves: %02d, Avg moves: %.2f",
                    i, moves, (float) totalMoves / i));
        }
    }


    final static boolean LOG_VERBOSE = true;
    final static boolean LOG_DEBUG = true;

    /**
     * A person intending to move in a gallery
     */
    static class Person {
        private static final int FWD = 0;

        public static final int LEFT = -1;
        public static final int RIGHT = 1;

        final private String name;

        /**
         * Which side of the gallery are we standing on when passing another person?
         * Could be LEFT or RIGHT or if we are able to pass the other person then FWD.
         */
        private int side;

        /**
         * The other person in gallery whom we should pass
         */
        public Person other;

        public Person(String n, int s) {
            name = n;
            side = s;
        }

        /**
         * Try to move ahead in a gallery.
         */
        public void move() {
            // If standing on the same of the gallery as the other person in gallery, move aside
            // so that both may pass...
            if (side == other.side && side != FWD) {
                // Sleep to simulate hesistation in moving aside
                try {
                    Thread.sleep(10);
                } catch (InterruptedException i) {
                    verbose(i.getMessage());
                }
                // Move aside
                side *= -1;
                verbose(name + ": " + "Moving " + ((side == RIGHT) ? "left" : "right"));
            } else {
                // Move fwd
                side = FWD;
                verbose(name + ": " + "Moving fwd!");
            }
        }

        /**
         * Whether this person has moved ahead on their course by passing the other
         * person in the gallery.
         *
         * @return true if this person has moved ahead. false otherwise.
         */
        public boolean hasMoved() {
            return side == FWD;
        }
    }


    public static int run() throws InterruptedException {

        // Twp persons in a narrow gallery...
        final Person alice = new Person("Alice", Person.LEFT);
        final Person bob = new Person("Bob", Person.LEFT);

        // Passing each other
        alice.other = bob;
        bob.other = alice;

        // Single length array for counting, since we need to pass a final obj in
        // an anonymous inner class
        final int[] aMoves = new int[1];
        final int[] bMoves = new int[1];

        final CountDownLatch latch = new CountDownLatch(2);

        verbose("Starting...");
        new Thread(() -> {
            while (!alice.hasMoved()) {
                alice.move();
                aMoves[0]++;
            }
            latch.countDown();
        }).start();

        new Thread(() -> {
            while (!bob.hasMoved()) {
                bob.move();
                bMoves[0]++;
            }
            latch.countDown();
        }).start();

        latch.await();
        return aMoves[0] + bMoves[0];
    }

    public static void verbose(String msg) {
        if (!LOG_VERBOSE) return;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:ss.SSS");
        System.out.println(String.format("%s: %s", sdf.format(new Date()), msg));
    }

    public static void debug(String msg) {
        if (!LOG_DEBUG) return;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:ss.SSS");
        System.out.println(String.format("%s: %s", sdf.format(new Date()), msg));
    }
}
