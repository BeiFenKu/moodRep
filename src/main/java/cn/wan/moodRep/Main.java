package cn.wan.moodRep;

import cn.wan.moodRep.pageProcessor.MoodPageProcessor;
import cn.wan.moodRep.pipeLine.MemoryPipeline;
import cn.wan.moodRep.utils.Constant;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import us.codecraft.webmagic.Spider;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * Created by wanzhenghang on 2017/2/19.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        MemoryPipeline memoryPipeline = new MemoryPipeline();

        String url= Constant.getConfig("startUrl");
        Spider.create(new MoodPageProcessor())
                .addPipeline(memoryPipeline)
                .addUrl(url)
                .thread(Constant.getInt("thread"))
                .run();
        System.out.println("memoryPlace\t:"+memoryPipeline.memoryPlace);
        String noemojifile = Constant.getConfig("noemojifile");
        String finalfile = Constant.getConfig("finalfile");
        dealAndWrite1(memoryPipeline.memoryPlace,noemojifile);

        Scanner in = new Scanner(System.in);
        System.out.println("处理完了没？？");
        in.next();


        segWithoutStop(noemojifile,finalfile);

    }

    public static void dealAndWrite1(String result,String filename) throws IOException {

        result = result.replaceAll("\\[em].*\\[/em]","");
        result = result.replaceAll("@\\{uin:\\d*,nick:.*,who:\\d*}","");
        System.out.println("result  2\t:"+result);
        try{
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename)));
            printWriter.write(result);
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("写入文件的时候出错了！！");
        }
    }

    public static void segWithoutStop(String filenameR,String filenameW) throws Exception {
        List<Word> words = WordSegmenter.seg(read(filenameR));
        System.out.println("words.toString()\t:"+words.toString());
        write(words.toString(),filenameW);
    }

    public static String read(String fileName){
        char [] buf =new char[1024];
        int len;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String result = "";
            while ((len=bufferedReader.read(buf))!=-1){
                result +=new String(buf);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取文件的时候出错了！！");
            return null;
        }
    }

    public static void write(String string,String fileName){
        try{
            System.out.println("string\t:"+string);
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
            printWriter.write(string);
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("写入文件的时候出错了！！");
        }
    }
}
