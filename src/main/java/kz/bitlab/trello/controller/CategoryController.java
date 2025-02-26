package kz.bitlab.trello.controller;

import kz.bitlab.trello.model.Folders;
import kz.bitlab.trello.model.TaskCategories;
import kz.bitlab.trello.repository.CategoryRepository;
import kz.bitlab.trello.repository.FolderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CategoryController {
    private final FolderRepository folderRepository;
    private final CategoryRepository categoryRepository;

    public CategoryController(FolderRepository folderRepository, CategoryRepository categoryRepository) {
        this.folderRepository = folderRepository;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam("id") Long id, @RequestParam("categoryName") String categoryName){
        Folders folder = folderRepository.findById(id).orElse(null);
        if (folder != null){
            TaskCategories category = new TaskCategories();
            category.setName(categoryName);
            folder.getCategories().add(category);
            categoryRepository.save(category);
            folderRepository.save(folder);
        }
        return "redirect:/detailsFolder/" + id;
    }

    @GetMapping("/deleteCategory/{idCategory}/{idFolder}")
    public String removeCategory(@PathVariable("idCategory") Long idCategory, @PathVariable("idFolder") Long idFolder){
        Folders folder = folderRepository.findById(idFolder).orElse(null);
        if (folder != null){
            List<TaskCategories> categories = folder.getCategories();
            if (categories != null){
                TaskCategories category = categoryRepository.findById(idCategory).orElse(null);
                if (category != null){
                    categories.remove(category);
                    categoryRepository.delete(category);
                    folder.setCategories(categories);
                    folderRepository.save(folder);
                }
            }
        }
        return "redirect:/detailsFolder/" + idFolder;
    }

    @GetMapping("/categories")
    public String categories(Model model){
        List<TaskCategories> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "allCategories";
    }

    @PostMapping("/renameCategory")
    public String renameCat(@RequestParam("id") Long id, @RequestParam("name") String name){
        TaskCategories category = categoryRepository.findById(id).orElse(null);
        if (category != null){
            category.setName(name);
            categoryRepository.save(category);
        }
        return "redirect:/categories";
    }

    @GetMapping("/deleteCategory/{id}")
    public String delCat(@PathVariable("id") Long id){
        TaskCategories category = categoryRepository.findById(id).orElse(null);
        if (category != null){
            List<Folders> folders = categoryRepository.findFoldersByCategoryId(id);
            if (folders != null){
                for (Folders folder: folders){
                    folder.getCategories().remove(category);
                    folderRepository.save(folder);
                }
            }
            categoryRepository.delete(category);
        }

        return "redirect:/categories";
    }
}
