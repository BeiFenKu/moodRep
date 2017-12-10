package cn.wan.moodRep.pageProcessor;

import cn.wan.moodRep.utils.Constant;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by wanzhenghang on 2017/2/19.
 */
public class MoodPageProcessor implements PageProcessor {

    public int pageTotal;
    public String g_tk;

    private Site site =
            Site.me()
            .addHeader("referer",Constant.getConfig("referer"))
            .addHeader("Cookie", Constant.getConfig("cookies"))
            .setRetryTimes(3)
            .setSleepTime(100)
            .setUserAgent(Constant.getConfig("userAgent"));

    public MoodPageProcessor() {
        this.pageTotal = Constant.getInt("pageTotal");
        this.g_tk = Constant.getConfig("g_tk");
    }

    public void process(Page page) {

        System.out.println("当前URL\t:"+page.getUrl());
//        获取当前pos值
        String posNow = page.getUrl().regex("pos=\\d*").regex("\\d+").toString();


        int posNowInt=Integer.parseInt(posNow);
        System.out.println("当前的页数\t:"+((posNowInt/20)+1));

//        处理页面
//        List<String> results=page.getJson().removePadding("_Callback").removePadding("_preloadCallback").jsonPath("$.msglist[*].content").all();
        List<String> results=page.getJson().removePadding("_preloadCallback").jsonPath("$.msglist[*].content").all();
        System.out.println("results\t:"+results);
        page.putField("results",results);
        //        如果当前页面值小于总page数，那么继续向URL队列中放置TargetURL
        if ((posNowInt/20)+1 < pageTotal){
            String targetURL =
                    "https://user.qzone.qq.com/proxy/domain/taotao.qq.com/cgi-bin/emotion_cgi_msglist_v6?uin=2933274812&ftype=0&sort=0&pos="
                            + (posNowInt+20) +
                            "&num=20&replynum=100&callback=_preloadCallback&code_version=1&format=jsonp&need_private_comment=1&qzonetoken=e20a6634252be55ea8b0e48c273a8b3b5fd0e4409bc82d56f7cf5b2079430eeee837331fbc0479f38a3a&g_tk="
                            +g_tk;
            page.addTargetRequest(targetURL);
        }
        System.out.println("--------------------------------------------------------------------------------------------------------");




    }

    public Site getSite() {
        return site;
    }
}
