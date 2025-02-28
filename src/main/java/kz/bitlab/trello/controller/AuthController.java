package kz.bitlab.trello.controller;

import jakarta.servlet.http.HttpSession;
import kz.bitlab.trello.model.Person;
import kz.bitlab.trello.repository.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final PersonRepository personRepository;

    public AuthController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpSession session, @RequestParam("email") String email, @RequestParam("password") String password){
        Person person =  personRepository.findByEmail(email);
        if (person != null){
            if (person.getPassword().equals(password)){
                session.setAttribute("person", person);
                return "redirect:/";
            } else {
                return "redirect:/login?errorPass";
            }
        } else {
            return "redirect:/login?errorLog";
        }
    }

    @GetMapping("/register")
    public String registerPage(){
        return "registration";
    }
    @PostMapping("/register")
    public String register(HttpSession session,
                           @RequestParam("fullName") String fullName,
                           @RequestParam("confirmpassword") String confirmpassword,
                           @RequestParam("email") String email, @RequestParam("password") String password, Model model){

        if (confirmpassword.equals(password)){
            Person oldPerson = personRepository.findByEmail(email);
            if (oldPerson != null){
                return "redirect:/register?errorUserExist";
            } else {
                Person person = new Person();
                person.setEmail(email);
                person.setFullName(fullName);
                person.setPassword(password);
                session.setAttribute("person", person);
                personRepository.save(person);
                model.addAttribute("person", person);
                return "redirect:/";
            }
        }else {
            return "redirect:/register?errorPass";
        }

    }
    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate(); // Удаляет сессию
        return "redirect:/login";
    }

}
