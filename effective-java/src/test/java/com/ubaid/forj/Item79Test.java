package com.ubaid.forj;

import com.ubaid.forj.misc.ForwardingSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

public class Item79Test {
    
    @Test
    void test1() {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());
//        set.addObserver((s, e) -> System.out.println(e));

/*
        set.addObserver(new SetObserver<>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);
                if (element == 23) {
                    set.removeObserver(this);
                }
            }
        });
*/
        
        set.addObserver(new SetObserver<>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);
                if (element == 23) {
                    ExecutorService exec =
                        Executors.newSingleThreadExecutor();
                    try {
                        exec.submit(() -> set.removeObserver(this)).get();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new AssertionError(e);
                    } finally {
                        exec.shutdown();
                    }
                }
            }
        });
        
        IntStream
            .range(0, 100)
            .forEach(set::add);
    }

}

class ObservableSet<E> extends ForwardingSet<E> {

    public ObservableSet(Set<E> s) {
        super(s);
    }
    
    private final List<SetObserver<E>> observers
        = new ArrayList<>();
    
    public void addObserver(SetObserver<E> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }
    
    public boolean removeObserver(SetObserver<E> observer) {
        synchronized (observers) {
            return observers.remove(observer);
        }
    }
    
/*
    private void notifyElementAdded(E element) {
        synchronized (observers) {
            for (SetObserver<E> observer : observers) {
                observer.added(this, element);
            }
        }
    }
*/
    private void notifyElementAdded(E element) {
        List<SetObserver<E>> snapshot = null;
        synchronized (observers) {
            snapshot = new ArrayList<>(observers);
        }
        for (SetObserver<E> observer : snapshot) {
            observer.added(this, element);
        }
    }
    
    @Override
    public boolean add(E element) {
        boolean added = super.add(element);
        if (added) {
            notifyElementAdded(element);
        }
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E element: c) {
            result |= add(element);
        }
        return result;
    }
}

@FunctionalInterface
interface SetObserver<E> {
    void added(ObservableSet<E> set, E element);
}