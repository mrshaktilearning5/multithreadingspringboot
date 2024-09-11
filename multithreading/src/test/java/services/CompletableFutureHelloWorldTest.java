package services;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService helloWorldService=new HelloWorldService();
    CompletableFutureHelloWorld completableFutureHelloWorld=new CompletableFutureHelloWorld(helloWorldService);


    @Test
    void helloWorldMultipleAsyncCall() {
        String helloWorldMultipleAsyncCall=completableFutureHelloWorld.helloWorldMultipleAsyncCall();
        assertEquals("HELLOWORLD",helloWorldMultipleAsyncCall);
    }

    @Test
    void helloWorldThenComposeCall() {
        CompletableFuture<String> helloWorldMultipleAsyncCall=completableFutureHelloWorld.helloWorldThenCompose();
        helloWorldMultipleAsyncCall.thenAccept((result)->{assertEquals("HELLO WORLD!",result);}
                ).join();
    }
}