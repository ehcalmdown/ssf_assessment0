package sg.nus.iss.ssfassessment.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Article {
    private String id;
    private String published_on;
    private String title;
    private String news_url;
    private String imageurl;
    private String newsBody;
    private String tags;
    private String categories;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPublished_on() {
        return published_on;
    }
    public void setPublished_on(String published_on) {
        this.published_on = published_on;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getNews_url() {
        return news_url;
    }
    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }
    public String getImageurl() {
        return imageurl;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    public String getNewsBody() {
        return newsBody;
    }
    public void setNewsBody(String newsBody) {
        this.newsBody = newsBody;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }

    //convert to string and back to json object
    public static Article create(JsonObject jo){
        Article n = new Article();
        n.setId(jo.getString("id"));
        n.setPublished_on(jo.getString("published_on"));
        n.setTitle(jo.getString("title"));
        n.setNews_url(jo.getString("news_url"));
        n.setImageurl(jo.getString("image_url"));
        n.setNewsBody(jo.getString("newsBody"));
        n.setTags(jo.getString("tags"));
        n.setCategories(jo.getString("catergories"));

        return n;

    }
    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("id", id)
                .add("published_on", published_on)
                .add("title", title)
                .add("news_url", news_url)
                .add("imageurl", imageurl)
                .add("newsBody", newsBody)
                .add("tags", tags)
                .add("categories", categories)
                .build();
    }


}
