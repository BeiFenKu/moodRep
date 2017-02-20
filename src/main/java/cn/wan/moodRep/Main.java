package cn.wan.moodRep;

import cn.wan.moodRep.pageProcessor.MoodPageProcessor;
import cn.wan.moodRep.pipeLine.MemoryPipeline;
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

        String url=
                "https://h5.qzone.qq.com/proxy/domain/taotao.qq.com/cgi-bin/emotion_cgi_msglist_v6?uin=905852399&inCharset=utf-8&outCharset=utf-8&hostUin=905852399&notice=0&sort=0&pos=0&num=20&cgi_host=http%3A%2F%2Ftaotao.qq.com%2Fcgi-bin%2Femotion_cgi_msglist_v6&code_version=1&format=jsonp&need_private_comment=1&g_tk=842368538";
        Spider.create(new MoodPageProcessor(51,"842368538"))
                .addPipeline(memoryPipeline)
                .addUrl(url)
                .thread(1)
                .run();
        System.out.println("memoryPlace\t:"+memoryPipeline.memoryPlace);
        dealAndWrite1(memoryPipeline.memoryPlace,"test_zhangtianjing_noemoji.txt");

        Scanner in = new Scanner(System.in);
        System.out.println("处理完了没？？");
        in.next();


        segWithoutStop("test_zhangtianjing_noemoji.txt","test_zhangtianjing_finall.txt");

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
