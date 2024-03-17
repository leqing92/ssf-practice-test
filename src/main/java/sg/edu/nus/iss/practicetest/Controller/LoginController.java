package sg.edu.nus.iss.practicetest.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {
    
    @GetMapping(path={"/","/index.html","/login"})
    public String getLoginPage(){
        return "login";
    }

    @PostMapping(path = "/login")
    public String postLogin(@RequestParam String fullname, @RequestParam int age, HttpSession httpSession) {
        if(age > 10){
            httpSession.setAttribute("fullname", fullname);
            httpSession.setAttribute("age", age);
            
            return "redirect:/todos/list";
        }
        return "underage.html";
    }
    
}
