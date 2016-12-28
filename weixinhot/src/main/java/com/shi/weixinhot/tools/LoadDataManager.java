package com.shi.weixinhot.tools;

import com.shi.weixinhot.common.Config;
import com.shi.weixinhot.beans.BannerBean;
import com.shi.weixinhot.beans.CategoryBean;
import com.shi.weixinhot.beans.ItemBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shimanqiang on 16-12-25.
 */

public class LoadDataManager {

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static LoadDataManager getInstance() {
        return Holder.instance;
    }

    private static final class Holder {
        private final static LoadDataManager instance = new LoadDataManager();
    }

    public List<CategoryBean> generateCategory() {
        List<CategoryBean> beans = new ArrayList<>();
        beans.add(new CategoryBean("pc_0", "热门"));
        beans.add(new CategoryBean("pc_1", "推荐"));
        beans.add(new CategoryBean("pc_2", "段子手"));
        beans.add(new CategoryBean("pc_3", "养生堂"));
        beans.add(new CategoryBean("pc_4", "私房话"));
        beans.add(new CategoryBean("pc_5", "八卦精"));
        beans.add(new CategoryBean("pc_6", "爱生活"));
        /////////////////////////////////////////////
        beans.add(new CategoryBean("pc_7", "财经迷"));
        beans.add(new CategoryBean("pc_8", "汽车迷"));
        beans.add(new CategoryBean("pc_9", "科技咖"));
        beans.add(new CategoryBean("pc_10", "潮人帮"));
        beans.add(new CategoryBean("pc_11", "辣妈帮"));
        beans.add(new CategoryBean("pc_12", "点赞党"));
        beans.add(new CategoryBean("pc_13", "旅行家"));
        beans.add(new CategoryBean("pc_14", "职场人"));
        beans.add(new CategoryBean("pc_15", "美食家"));
        beans.add(new CategoryBean("pc_16", "古今通"));
        beans.add(new CategoryBean("pc_17", "学霸族"));
        beans.add(new CategoryBean("pc_18", "星座控"));
        beans.add(new CategoryBean("pc_19", "体育迷"));

        return beans;
    }

    public static void main(String[] args) {
        LoadDataManager.getInstance().generateBannerData(new Callback<List<BannerBean>>() {
            @Override
            public void onSuccess(List<BannerBean> obj) {
                System.out.println(obj.toArray().toString());
            }
        });
    }

    public interface Callback<T> {
        void onSuccess(T obj);
    }

    public void generateBannerData(final LoadDataManager.Callback<List<BannerBean>> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<BannerBean> list = new ArrayList<>();
                    Document doc = Jsoup.connect(Config.BASE_URL).timeout(5000).get();
                    Elements links = doc.select("div.hd-list").first().select("a.sd-slider-item");
                    for (int i = 0; i < links.size(); i++) {
                        Element link = links.get(i);

                        BannerBean bannerBean = new BannerBean();

                        bannerBean.setUrl(link.attr("href"));

                        Element img = link.select("img").first();
                        bannerBean.setImgUrl(img.attr("src"));

                        Element p = link.select("div.text>p").first();
                        bannerBean.setTitle(p.attr("title"));

                        list.add(bannerBean);
                    }

                    callback.onSuccess(list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void generateFixData(final String url, final LoadDataManager.Callback<List<ItemBean>> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<ItemBean> list = new ArrayList<>();
                    Document doc = Jsoup.connect(url).timeout(5000).get();
                    Elements liDoms = doc.select("ul.news-list").first().select("li");
                    for (Element li : liDoms) {
                        ItemBean itemBean = new ItemBean();

                        Element aDom = li.select("div.img-box>a").first();
                        itemBean.setUrl(aDom.attr("href")); //

                        Element img = aDom.select("img").first();
                        itemBean.setImgUrl(img.attr("src"));//

                        Elements txtBox = li.select("div.txt-box");
                        Element h3a = txtBox.select("h3>a").first();//
                        itemBean.setTitle(h3a.text());
                        Elements pTxt = txtBox.select("p.txt-info");
                        itemBean.setAbout(pTxt.text());//

                        Element s_p = txtBox.select("div.s-p").first();
                        itemBean.setAboutTime(DateUtil.transferStampGap(s_p.attr("t") + "000")); //TODO 时间需要转化：： s_p.attr("t")

                        Element account = s_p.select("a.account").first();
                        itemBean.setAuthor(account.text());
                        itemBean.setAuthorLink(account.attr("href"));

                        list.add(itemBean);
                    }

                    callback.onSuccess(list);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
