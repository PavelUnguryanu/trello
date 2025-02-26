package kz.bitlab.trello.controller;

import kz.bitlab.trello.model.Comments;
import kz.bitlab.trello.model.Folders;
import kz.bitlab.trello.model.Tasks;
import kz.bitlab.trello.repository.CommentsRepository;
import kz.bitlab.trello.repository.FolderRepository;
import kz.bitlab.trello.repository.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaskController {
    private final FolderRepository folderRepository;
    private final TaskRepository taskRepository;
    private final CommentsRepository commentsRepository;
    public TaskController(FolderRepository folderRepository, TaskRepository taskRepository, CommentsRepository commentsRepository) {
        this.folderRepository = folderRepository;
        this.taskRepository = taskRepository;
        this.commentsRepository = commentsRepository;
    }

    @PostMapping("addTask")
    public String addTask(@RequestParam("id") Long id, @RequestParam("titleTask") String titleTask, @RequestParam("taskDesc") String taskDesc){
        Folders folder = folderRepository.findById(id).orElse(null);
        if (folder != null){
            Tasks task = new Tasks();
            task.setTitle(titleTask);
            task.setDescription(taskDesc);
            task.setFolder(folder);
            task.setStatus(0);
            folder.getTasks().add(task);
         //   folderRepository.save(folder);
            taskRepository.save(task);
        }
        return "redirect:/detailsFolder/" + id;
    }

    @GetMapping("/detailsTask/{id}")
    public String detailsTask(@PathVariable("id") Long id, Model model){
        Tasks task = taskRepository.findById(id).orElse(null);
        int status = 0;
        if (task != null){
            List<Comments> comments = commentsRepository.findAllByTask(task);
            if (comments != null){
                model.addAttribute("comments", comments);
            }
            model.addAttribute("task", task);
            status = task.getStatus();
        }
        if (status == 2 || status == 3){
            return "detailsTaskReadOnly";
        } else {
            return "detailsTask";
        }

    }

    @PostMapping("/taskUpdate")
    public String taskUpdate(@RequestParam("title") String title, @RequestParam("id") Long id,
                             @RequestParam("description") String description, @RequestParam("status") int status){
        Tasks task = taskRepository.findById(id).orElse(null);
        String path = "/";
        if (task != null){
            Folders folder = task.getFolder();
            task.setTitle(title);
            task.setDescription(description);
            task.setStatus(status);
            if (folder != null){

                Long idFolder = folder.getId();
                path = "/detailsFolder/" + idFolder;
            }
            taskRepository.save(task);

        }
        return "redirect:" + path;
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable("id") Long id){
        Tasks task = taskRepository.findById(id).orElse(null);
        String path = "/";
        if (task != null){
            List<Comments> comments = commentsRepository.findAllByTask(task);
            if (comments != null){
                commentsRepository.deleteAll(comments);
            }
            Folders folder = task.getFolder();
            if (folder != null){
                folder.getTasks().remove(task);
                Long idFolder = folder.getId();
                path = "/detailsFolder/" + idFolder;
            }
            taskRepository.delete(task);

        }
        return "redirect:" + path;
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam("id") Long id, @RequestParam("comm") String comm){
        Tasks task = taskRepository.findById(id).orElse(null);
        if (task != null){
            Comments comment = new Comments();
            comment.setComment(comm);
            comment.setTask(task);
            commentsRepository.save(comment);
        }

        return "redirect:/detailsTask/" + id;
    }
}
