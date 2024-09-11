package services;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
@Slf4j
public class CompletableFutureHelloWorld {

    HelloWorldService hws;

    public CompletableFutureHelloWorld(HelloWorldService hws) {
        this.hws = hws;
    }

    public static void main(String[] args) {
        HelloWorldService helloWorldService=new HelloWorldService();
        CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase)
                .thenAccept((result)->{
                    log.info("Result id "+ result);
                })
                .join();
        log.info("Done");
    }

    public String helloWorldMultipleAsyncCall(){

        Long startTime=System.currentTimeMillis();

        CompletableFuture<String> hello=CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> world=CompletableFuture.supplyAsync(hws::world);
        String response= hello.thenCombine(world,(h,w)->h+w)
                .thenApply(String::toUpperCase)
                .join();
        Long endTime=System.currentTimeMillis();
        Long totalTimeTakenInMilliseconds=endTime-startTime;
        log.info("Total time take : {}" ,totalTimeTakenInMilliseconds);

        return response;
    }


    public CompletableFuture<String> helloWorldThenCompose(){

        Long startTime=System.currentTimeMillis();

        CompletableFuture<String> hello=CompletableFuture.supplyAsync(hws::hello)
                .thenCompose((resutl)->hws.worldFuture(resutl))
                .thenApply(String::toUpperCase);

        Long endTime=System.currentTimeMillis();
        Long totalTimeTakenInMilliseconds=endTime-startTime;
        log.info("Total time take : {}" ,totalTimeTakenInMilliseconds);
        return hello;
    }

}
