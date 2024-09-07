package ParallelStreams;

import lombok.extern.slf4j.Slf4j;
import util.Utils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ParallelStreamsExample {

    public static void main(String[] args) throws InterruptedException {
        Long startTime=System.currentTimeMillis();
        List<String> namesList= Utils.namesList();

        ParallelStreamsExample parallelStreamsExample=new ParallelStreamsExample();
        List<String> resultList=parallelStreamsExample.stringTransform(namesList);
        Long endTime=System.currentTimeMillis();
        Long totalTimeTakenInMilliseconds=endTime-startTime;
        log.info(" Final result : {} ",resultList);
        log.info("Total time take : {}" ,totalTimeTakenInMilliseconds);


    }

    public List<String> stringTransform(List<String> namesList) throws InterruptedException{
       return namesList
               //.stream()
               .parallelStream()
               .map(name->
               {
                   try {
                          return addNameLengthTransform(name);
                       } catch (InterruptedException e) {
           // Thread.currentThread().interrupt(); // Restore the interrupted status
            return "Error processing " + name; // Return an appropriate default value
        }
    })
                .collect(Collectors.toList());
    }

    private  String addNameLengthTransform(String name) throws InterruptedException {
        Thread.sleep(500);
        return name.length()+"-"+name;
    }
}
