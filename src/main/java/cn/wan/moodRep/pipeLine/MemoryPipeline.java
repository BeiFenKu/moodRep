package cn.wan.moodRep.pipeLine;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by wanzhenghang on 2017/2/19.
 */
public class MemoryPipeline implements Pipeline {

    public String memoryPlace ="";


    public void process(ResultItems resultItems, Task task) {
        List<String > results = resultItems.get("results");
        for (String s:
             results) {
            memoryPlace = memoryPlace + s + "\n";
//            System.out.println("memoryPlace\t:"+memoryPlace);
        }
    }
}
