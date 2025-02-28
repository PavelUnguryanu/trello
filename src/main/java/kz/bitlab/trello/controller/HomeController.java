package kz.bitlab.trello.controller;

import jakarta.servlet.http.HttpSession;
import kz.bitlab.trello.model.Folders;
import kz.bitlab.trello.model.Person;
import kz.bitlab.trello.repository.FolderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final FolderRepository folderRepository;

    public HomeController(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session){
        List<Folders> folders = folderRepository.findAll();
        if (folders != null){
            model.addAttribute("folders", folders);
        }
        Person person = (Person) session.getAttribute("person");
        if (person == null){
            return "redirect:/login";
        } else {
            return "index";
        }

    }
}
