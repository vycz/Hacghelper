package moe.ggg.hacghelper;

/**
 * Created by orange on 2017/2/21.
 */

public class Item {
    public Item() {

    }

    public Item(String img,String title, String content) {
        this.image = img;
        this.title = title;
        this.Content = content;
    }

    String image;
    String url;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tittle) {
        this.title = tittle;
    }

    public String getContent() {
        return Content;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public void setContent(String content) {
        Content = content;

    }

    String title;
    String Content;
    int Type;
}
