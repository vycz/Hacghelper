package moe.ggg.hacghelper;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by orange on 2017/2/22.
 */

public class GetContent {
    private String Url;
    public GetContent(String url) {
        Url = url;
    }
    public String get(){
        Document document = null;
        try {
            document =  Jsoup.connect(Url).timeout(30000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements ele_img = document.getElementsByTag("img");
        if(ele_img.size() != 0){
            for(Element e:ele_img){
                e.attr("style","max-width:100%;height:auto;");
            }
        }
        Elements e = document.select("div.entry-content");
        return e.html();

    }

}
