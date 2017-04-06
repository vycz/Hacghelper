package moe.ggg.hacghelper;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by orange on 2017/2/21.
 */

public class Items {
    private List<Item> Items;
    private String key = "";
    public List<Item> getItems(int type,int page,String key){
        if(key != "")
            this.key = key;
        return this.getItems(type,page);
    }
    public List<Item> getItems(int type,int page){
        String Url;
        if(key == ""){
            Url = DoUrl.getUrl(type,page);
        }else {
            Url = DoUrl.getUrl(type,page,key);
        }
        Document document = null;
        try {
            document = Jsoup.connect(Url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0")
                    .ignoreContentType(true)
                    .timeout(100000)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Elements article = document.getElementsByTag("article");
        Items = new ArrayList<>();
        for (Element element:article) {
            Item item = new Item();
            String title = element.select("a").first().text();
            String href = element.select("a").first().attr("href");
            String img = "";
            if(element.select("img").first() != null)
                img = element.select("img").first().attr("src");
            String content = element.select("div.entry-content").text();
            item.setTitle(title);
            item.setUrl(href);
            item.setImage(img);
            item.setContent(content);
            item.setType(type);
            Items.add(item);
        }
        return Items;
    }
}
