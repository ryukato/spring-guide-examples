package app.blur.app.executor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public class ExecutorServiceTest {
    public static void main(String[] args) throws Exception {
//        doWithExecutorService();
//        doWithJava8Stream();
        doWithForkJoinPool();

    } // end of main

    private static void doWithForkJoinPool() throws Exception {
        final Function<Integer, String> f = i -> "Starting " + Thread.currentThread().getName() + ", index=" + i + ", ended at" + Instant.now().toString();
        ForkJoinPool pool = new ForkJoinPool(5);
        pool.submit(() -> {
            IntStream.range(0, 10).parallel().forEach(i -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(f.apply(i));
                }catch (InterruptedException e) {
                    // do nothing
                }
            });
        }).get();
    }

    private static void doWithJava8Stream() {
        Function<Integer, String> f = i -> "Starting " + Thread.currentThread().getName() + ", index=" + i + ", ended at" + Instant.now().toString();
        IntStream.range(0, 10).parallel().forEach(i -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(f.apply(i));
            }catch (InterruptedException e) {
                // do nothing
            }
        });
    }

    private static void doWithExecutorService() {
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        List<Future<String>> futures = new ArrayList<>();
        IntStream.range(1, 11).forEach(i -> {
            futures.add(executorService.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                }catch (InterruptedException e){
                    //do nothing
                }
                return Thread.currentThread().getName() + ", index=" + i + ", ended at" + Instant.now().toString();
            }));
        });

        futures.stream().forEach(f -> {
            try {
                System.out.println("Thread result: "+ f.get());
            }catch (ExecutionException | InterruptedException e) {
                //do nothing
            }
        });

        executorService.shutdown();
    }
}
