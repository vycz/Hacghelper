package moe.ggg.hacghelper;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by orange on 2017/2/21.
 */

public class DoUrl {
    public static final String URL_NEWS="http://www.hacg.fi/wp";
    public static final String URL_AGE = "http://www.hacg.fi/wp/age.html";
    public static final String URL_ANIME = "http://www.hacg.fi/wp/anime.html";
    public static final String URL_COMIC = "http://www.hacg.fi/wp/comic.html";
    public static final String URL_GAME = "http://www.hacg.fi/wp/game.html";
    public static final String URL_OP = "http://www.hacg.fi/wp/op.html";
    public static final String URL_SEARCH = "http://www.hacg.fi/wp/";
    public static final String URL_SEARCH2 = "&submit=%E6%90%9C%E7%B4%A2";

    public static String key;
    public static String getUrl(int Type,int currentPage,String key){
        DoUrl.key = key;
        return DoUrl.getUrl(Type,currentPage);
    }
    public static String getUrl(int Type,int currentPage){
        String urlStr = "";
        switch (Type){
            case 1:
                urlStr = URL_NEWS;
                break;
            case 2:
                urlStr = URL_AGE;
                break;
            case  3:
                urlStr = URL_ANIME;
                break;
            case 4:
                urlStr = URL_COMIC;
                break;
            case 5:
                urlStr = URL_GAME;
                break;
            case 6:
                urlStr = URL_OP;
                break;
            case 7:
                urlStr = URL_SEARCH +"?s="+ DoUrl.key+URL_SEARCH2;
                break;
        }
        if(currentPage > 1&&key == null)
            urlStr += "/page/"+currentPage;
        if(key != null && currentPage >1){
            urlStr = URL_SEARCH +"page/"+currentPage+"?s="+ DoUrl.key+URL_SEARCH2;
        }
        return urlStr;
    }
}
