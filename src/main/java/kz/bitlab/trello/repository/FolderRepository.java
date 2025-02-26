package kz.bitlab.trello.repository;

import kz.bitlab.trello.model.Folders;
import kz.bitlab.trello.model.TaskCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folders, Long> {

    Folders findByCategories(List<TaskCategories> categories);
}
