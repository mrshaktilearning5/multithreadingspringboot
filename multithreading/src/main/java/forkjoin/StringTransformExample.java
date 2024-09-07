package forkjoin;

import lombok.extern.slf4j.Slf4j;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StringTransformExample {

    public static void main(String[] args) {
         Long startTime=System.currentTimeMillis();

        List<String> resultList=new ArrayList<>();
        List<String> names= Utils.namesList();

        names.forEach((name)-> {

            String newValue= null;
            try {
                newValue = addNameLengthTransform(name);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            resultList.add(newValue);

        });

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
