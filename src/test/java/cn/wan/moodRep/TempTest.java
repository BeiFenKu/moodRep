package cn.wan.moodRep;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.junit.Test;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.*;
import java.util.List;

/**
 * Created by wanzhenghang on 2017/2/19.
 */
public class TempTest {

    @Test
    public void test() throws IOException {
//        List<Word> words = WordSegmenter.seg("日本人也开始拍抗日神剧了");

        char [] buf =new char[1024];
        int len;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("webmagic_s.txt"));
            String result = "";
            while ((len=bufferedReader.read(buf))!=-1){
                result +=new String(buf);
            }
            result = result.replaceAll("\\[em].*\\[/em]","");
            result = result.replaceAll("@\\{uin:\\d*,nick:.*,who:\\d*}","");
            System.out.println("result  2\t:"+result);
            try{
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream("webmagic.txt")));
                printWriter.write(result);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("写入文件的时候出错了！！");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("读取文件的时候出错了！！");
        }
    }

    @Test
    public void test2() throws Exception {
//        WordSegmenter.seg(new File("webmagic.txt"),new File("webmagic_s_seg.txt"));
        String str1 =read("webmagic.txt");
        List<Word> words = WordSegmenter.seg(str1);
        System.out.println("words.toString()\t:"+words.toString());
        write(words.toString(),"webmagic_s_seg_withoutStop.txt");
    }

    public String read(String fileName){
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

    public void write(String string,String fileName){
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
