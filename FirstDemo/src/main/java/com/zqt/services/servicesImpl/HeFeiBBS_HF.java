package com.zqt.services.servicesImpl;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.zqt.services.HeFei_BBS_HFservices;
import com.zqt.utils.Hefei_Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Level;

@Service
public class HeFeiBBS_HF implements HeFei_BBS_HFservices {
    @Autowired
    private HeFei_BBS_HFservices heFei_BBSHFservices;

    /**
     * 评论资讯
     *
     * @param url
     */
    int num = 1;

    @Override
    public boolean pinglun(String url, String PingLun) {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
//        webClient.getOptions().setJavaScriptEnabled(false); //关闭JS
        webClient.getOptions().setCssEnabled(false); //禁用Css,可避免自动二次请求CSS进行渲染
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        /**
         * 去除CSS相关控制台警告
         */
        java.util.logging.Logger.getLogger("net.sourceforge.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        String cookie = Hefei_Utils.QueryCookie();
        String[] cookieArr = cookie.split(";");
        CookieManager cookieManager = new CookieManager();
        for (int i = 0; i < cookieArr.length; i++) {
            String[] cookieStr = cookieArr[i].split("=");
            cookieManager.addCookie(new Cookie("bbs.hefei.cc", cookieStr[0], cookieStr[1]));
        }
        webClient.setCookieManager(cookieManager);
        HtmlPage htmlPage = null;
        try {
            htmlPage = webClient.getPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HtmlForm form = (HtmlForm) htmlPage.getElementById("fastpostform");
        HtmlTextArea input = form.getTextAreaByName("message");
        input.setText(PingLun);
        HtmlButton submit = form.getButtonByName("replysubmit");
        try {
            submit.click();
            htmlPage.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (num == 1) {
            String newCookies = Hefei_Utils.getCookies(webClient);
            int updateNum = Hefei_Utils.UpdateCookie(cookie, newCookies);
            if (updateNum > 0) {
                System.out.println("cookie存入成功________________________");
            } else {
                break;
            }
            num++;
        }
        String pageText = htmlPage.asXml();
        System.out.println("输入字体长度:" + PingLun.length());
        webClient.close();
        if (pageText.contains(PingLun) && PingLun.length() >= 5) {
            return true;
        } else {
            return false;
        }
    }

}
