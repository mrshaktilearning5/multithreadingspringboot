package ParallelStreams;

import org.junit.jupiter.api.Test;
import util.Utils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamsExampleTest {

    ParallelStreamsExample parallelStreamsExample=new ParallelStreamsExample();

    @Test
    void stringTransform() throws InterruptedException {

        List<String> inputList= Utils.namesList();
        List<String> resultList= parallelStreamsExample.stringTransform(inputList);
        assertEquals(4,resultList.size());
    }

}