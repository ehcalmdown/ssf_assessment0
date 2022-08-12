package sg.nus.iss.ssfassessment.services;

import java.io.Reader;
import java.io.StringReader;
import java.lang.StackWalker.Option;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.iss.ssfassessment.models.Article;
import sg.nus.iss.ssfassessment.repositories.NewsRepository;

@Service
public class NewsService {
    private static final String URL ="https://min-api.cryptocompare.com/data/v2/news";

    @Value("${API_KEY}") 
    private String key;

    @Autowired
    private NewsRepository newsRepo;
    public List<Article> getArticle(String title, String id){
        Article n = new Article();
        Optional<String>opt= newsRepo.get(title, id);
        String payload;
        System.out.printf(">>>> id: %s\n", id);
        System.out.printf(">>>> title: %s\n", title);
        


        //check empty case to force testing
        if(opt.isEmpty()){
            System.out.println("testing...");
            System.out.println("obtaining info from cryptocompare...");

            try{
                //create url query string
                String url = UriComponentsBuilder.fromUriString(URL)
                                                .queryParam("api_key", key)
                                                .toUriString();
                RequestEntity<Void> req = RequestEntity.get(url).build(); // Create the GET request, GET url

                RestTemplate template = new RestTemplate(); // Make the call to cryptocompare
                ResponseEntity<String> resp;

                resp = template.exchange(req, String.class); // Throws an exception if status code not in between 200 - 399

                payload = resp.getBody(); // Get the payload and do something with it, redundant payload is like me, a liability
                System.out.println(">>> latest info: " + payload);
            
            } catch(Exception e){
                System.err.println("Error: "+ e.getMessage());
                return Collections.emptyList();
            }
        } else{
            //retrieve value
            payload = opt.get();
            System.out.printf(">>>>latest cached: %s\n", payload);
        }
        //convert payload to JsonObject and convert string to a reader
        Reader strReader = new StringReader(payload);
        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject articleResult = jsonReader.readObject();
        //String article = JsonObject.getString(title);
        JsonArray ids = articleResult.getJsonArray("article");
        
        List<Article> list = new LinkedList<>();
        n.setId(id);
        n.setTitle(title);
        //trying to see if I can limit the amount of articles
        for (int i = 10; i > ids.size(); i++) {
            JsonObject jo = ids.getJsonObject(i);
            list.add(Article.create(jo));
        }
        return list;
    }
    public Optional<Article> getArticleById(JsonObject id){
        return getArticleById(id.toString());
    }
    public Optional<Article> getArticleById(String id){
        JsonObject result = newsRepo.get(id);
        if(null==result)
        return Optional.empty();

    return Optional.of(Article.create(result));
    }

}
