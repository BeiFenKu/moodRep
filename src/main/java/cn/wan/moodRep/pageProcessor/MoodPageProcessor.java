package cn.wan.moodRep.pageProcessor;

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
            .addHeader("referer","http://qzs.qq.com/qzone/app/mood_v6/html/index.html")
            .addHeader("Cookie",
                    "eas_sid=R134Z8J40311m3y5J7j0U4d386; randomSeed=794209; __Q_w_s_hat_seed=1; __Q_w_s__QZN_TodoMsgCnt=1; ptui_loginuin=42330678; tvfe_boss_uuid=a9f707f0208ce4ea; pgv_pvid=2608045440; o_cookie=42330678; RK=Da/+PzvKU0; pgv_pvi=7908539392; ptisp=cm; ptcz=85f34490ec2d120d76d86926ca3491068c9cc6762ae94590cf4ed1c0751decb2; pt2gguin=o0820727003; uin=o0820727003; skey=@jOy8EK6mD; p_uin=o0820727003; p_skey=qkDp*sD04JNcTi6Zd2uluEXH9-ljifh5y-i0lCJGdi4_; pt4_token=5LVQ6io47apy7XJUNIrE*OCWK0-pvxq49P7ZshWW3YQ_; rv2=804F289F8368D6A16227AB7E0C510B3C963537DED8B4F26042; property20=05A6F2BAF4814552744D5B406ABF693B52A4BEA5B6AD8FDDF66CA92C6F8E6247AB6DB7309E6B6B2D; qzspeedup=sdch; pgv_si=s7871950848; pgv_info=ssid=s5577049360"
            )
            .setRetryTimes(3)
            .setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

    public MoodPageProcessor(int pageTotal, String g_tk) {
        this.pageTotal = pageTotal;
        this.g_tk = g_tk;
    }

    public void process(Page page) {

        System.out.println("当前URL\t:"+page.getUrl());
//        获取当前pos值
        String posNow = page.getUrl().regex("pos=\\d*").regex("\\d+").toString();


        int posNowInt=Integer.parseInt(posNow);
        System.out.println("当前的页数\t:"+((posNowInt/20)+1));

//        处理页面
        List<String> results=page.getJson().removePadding("_Callback").jsonPath("$.msglist[*].content").all();
//        System.out.println("results\t:"+results);
        page.putField("results",results);
        //        如果当前页面值小于总page数，那么继续向URL队列中放置TargetURL
        if ((posNowInt/20)+1 < pageTotal){
            String targetURL =
                    "https://h5.qzone.qq.com/proxy/domain/taotao.qq.com/cgi-bin/emotion_cgi_msglist_v6?uin=905852399&inCharset=utf-8&outCharset=utf-8&hostUin=905852399&notice=0&sort=0&pos="
                            + (posNowInt+20) +
                            "&num=20&cgi_host=http%3A%2F%2Ftaotao.qq.com%2Fcgi-bin%2Femotion_cgi_msglist_v6&code_version=1&format=jsonp&need_private_comment=1&g_tk="
                            +g_tk;
            page.addTargetRequest(targetURL);
        }
        System.out.println("--------------------------------------------------------------------------------------------------------");




    }

    public Site getSite() {
        return site;
    }
}
