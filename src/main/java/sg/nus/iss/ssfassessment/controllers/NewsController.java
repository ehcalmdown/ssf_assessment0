package sg.nus.iss.ssfassessment.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;

import sg.nus.iss.ssfassessment.models.Article;
import sg.nus.iss.ssfassessment.services.NewsService;

@Controller
 @RequestMapping(path = "/feeds")
public class NewsController {
    @Autowired
    private NewsService newsSvc;

    @GetMapping(produces  = "text/html")
    public String getArticle(Model model, @RequestParam String id, String title){
        List<Article> article = newsSvc.getArticle(id,title);
        model.addAttribute("title", title.toLowerCase());
        model.addAttribute("id", id);
        model.addAttribute("article", article);
        if (isNull(id))
			return "redirect:index.html";
        
        return "article";
    
    }
    
    
    //attempt to integrate postmapping for task 3
 @PostMapping
public List<String> saveArticle(@RequestBody MultiValueMap<String, String> form, Model model, HttpSession sess ){
    List<String> articles = null;

    String id = form.getFirst("id");
    if(!isNull(id)){
        System.out.println("id not found");
        sess.setAttribute("id", id);
        articles= new LinkedList<String>();
        sess.setAttribute("articles", articles);
    }
    id = (String)sess.getAttribute("id");
    // articles = (List<String>)sess.getAttribute("articles");
    String title = form.getFirst("field");
    if(!isNull(title))
        articles.add(title);

    model.addAttribute("articles", articles);
    model.addAttribute("title", title);

    return articles;
}
private boolean isNull(String s){
    return((null == s) || (s.trim().length() <=0)); //honestly not too sure what is happening here
    }
    
}

