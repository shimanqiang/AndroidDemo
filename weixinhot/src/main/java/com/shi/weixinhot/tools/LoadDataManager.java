package com.shi.weixinhot.tools;

import com.shi.weixinhot.common.Config;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by shimanqiang on 16-12-25.
 */

public class LoadDataManager {
    public static void main(String[] args) {
        LoadDataManager.getInstance().generateBaseData();
    }

    public static LoadDataManager getInstance(){
        return Holder.instance;
    }

    private static final class Holder{
        private final static LoadDataManager instance = new LoadDataManager();
    }

    public String generateBaseData(){
        try {
            Document doc = Jsoup.connect(Config.BASE_URL).timeout(5000).get();
            //System.out.println(doc.toString());

            Elements links = doc.select("div.hd-list").first().select("a.sd-slider-item");
            for (int i = 0; i < links.size(); i++) {
                Element link = links.get(i);
                System.out.println(link.attr("href"));
                Element img = link.select("img").first();
                System.out.println(img.attr("src"));
                Element p = link.select("div.text>p").first();
                System.out.println(p.attr("title"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
