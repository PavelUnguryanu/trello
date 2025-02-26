package kz.bitlab.trello.service;

import kz.bitlab.trello.model.Comments;
import kz.bitlab.trello.model.Folders;
import kz.bitlab.trello.model.TaskCategories;
import kz.bitlab.trello.model.Tasks;
import kz.bitlab.trello.repository.CategoryRepository;
import kz.bitlab.trello.repository.CommentsRepository;
import kz.bitlab.trello.repository.FolderRepository;
import kz.bitlab.trello.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class FolderService {
    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Transactional
    public void deleteFolder(Long folderId) {
        Folders folder = folderRepository.findById(folderId).orElse(null);
        if (folder != null) {
            List<TaskCategories> categories = folder.getCategories();
            categoryRepository.deleteAll(categories);
            folder.getCategories().clear();

           List<Tasks> tasks = taskRepository.findByFolder(folder);
           if (tasks != null){
               for (Tasks task: tasks){
                   List<Comments> comments = commentsRepository.findAllByTask(task);
                   if (comments != null){
                       commentsRepository.deleteAll(comments);
                   }
               }
               taskRepository.deleteAll(tasks);
           }

            folder.getTasks().clear();

            // Сохраняем изменения, чтобы обновить связи в базе данных
            folderRepository.save(folder);

            // Теперь можно удалить сам Folder
            folderRepository.delete(folder);
        }
    }
}
