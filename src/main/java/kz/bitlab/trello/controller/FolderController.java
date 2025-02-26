package kz.bitlab.trello.controller;

import kz.bitlab.trello.model.Folders;
import kz.bitlab.trello.repository.FolderRepository;
import kz.bitlab.trello.service.FolderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FolderController {
    private final FolderRepository folderRepository;
    private final FolderService folderService;

    public FolderController(FolderRepository folderRepository, FolderService folderService) {
        this.folderRepository = folderRepository;
        this.folderService = folderService;
    }

    @PostMapping("/addFolder")
    public String addFolder(@RequestParam("folderName") String folderName){
        Folders folders = new Folders();
        folders.setName(folderName);
        folderRepository.save(folders);
        return "redirect:/";
    }

    @GetMapping("detailsFolder/{id}")
    public String details(@PathVariable("id") Long id, Model model){
        Folders folder = folderRepository.findById(id).orElse(null);
        if (folder != null){
            model.addAttribute("folder", folder);
        }
        return "detailsFolder";
    }

    @GetMapping("/folders")
    public String folders(Model model){
        List<Folders> folders = folderRepository.findAll();
        if (folders != null){
            model.addAttribute("folders", folders);
        }
        return "allFolders";
    }

    @PostMapping("/renameFolder")
    public String renameFolder(@RequestParam("id") Long id, @RequestParam("name") String name){
        Folders folder = folderRepository.findById(id).orElse(null);
        if (folder != null){
            folder.setName(name);
            folderRepository.save(folder);
        }
        return "redirect:/folders";
    }

    @GetMapping("/deleteFolder/{id}")
    public String deleteFolder(@PathVariable("id") Long id){
       folderService.deleteFolder(id);
        return "redirect:/folders";
    }
}
