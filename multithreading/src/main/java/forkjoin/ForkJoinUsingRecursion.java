package forkjoin;

import lombok.extern.slf4j.Slf4j;
import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
@Slf4j
public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {

    private List<String> inputList;

    public ForkJoinUsingRecursion(List<String> inputList) {
        this.inputList = inputList;
    }

    @Override
    protected List<String> compute() {

        if(inputList.size()<=1){
            List<String> resultList=new ArrayList<>();
            inputList.forEach(name->{
                try {
                    resultList.add(addNameLengthTransform(name));

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            return resultList;
        }
        int midpoint=inputList.size()/2;
        ForkJoinTask<List<String>> leftInputList =new ForkJoinUsingRecursion(inputList.subList(0,midpoint)).fork();
        inputList=inputList.subList(midpoint,inputList.size());
        List<String> rightResult=compute();
        List<String> leftResult=leftInputList.join();
        leftResult.addAll(rightResult);
        return leftResult;
    }


    public static void main(String[] args) {

        Long startTime=System.currentTimeMillis();

        List<String> resultList= new ArrayList<>();
        List<String> names= Utils.namesList();
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        ForkJoinUsingRecursion forkJoinUsingRecursion=new ForkJoinUsingRecursion(names);
        resultList=forkJoinPool.invoke(forkJoinUsingRecursion);

        Long endTime=System.currentTimeMillis();
        Long totalTimeTakenInMilliseconds=endTime-startTime;
        log.info(" Final result : {} ",resultList);
        log.info("Total time take : {}" ,totalTimeTakenInMilliseconds);

    }

    private static String addNameLengthTransform(String name) throws InterruptedException {
        Thread.sleep(500);
        return name.length()+"-"+name;
    }
}
