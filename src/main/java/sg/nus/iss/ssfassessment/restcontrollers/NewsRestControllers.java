package sg.nus.iss.ssfassessment.restcontrollers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.iss.ssfassessment.models.Article;
import sg.nus.iss.ssfassessment.services.NewsService;


@RestController
@RequestMapping(path = "/news/<id>", produces = MediaType.APPLICATION_JSON_VALUE )

public class NewsRestControllers {

    @Autowired
    private NewsService newsSvc;

    @GetMapping(value = "{id}")
    public ResponseEntity<String> getArticleById(@PathVariable String id){
        Optional<Article> opt = newsSvc.getArticleById(id);

        if(opt.isEmpty()){
            JsonObject err = Json.createObjectBuilder()
                                .add("error", ": Cannot find news article %s\n".formatted(id))
                                .build();
                            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(err.toString());
        }

        Article article = opt.get();
        return ResponseEntity.ok(article.toJson().toString());
    }
    
}
