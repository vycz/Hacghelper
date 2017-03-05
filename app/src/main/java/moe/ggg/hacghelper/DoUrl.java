package moe.ggg.hacghelper;

import android.util.Log;

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
        }
        if(currentPage > 1)
            urlStr += "/page/"+currentPage;
        return urlStr;
    }
}
