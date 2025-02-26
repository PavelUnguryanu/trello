package kz.bitlab.trello.repository;

import kz.bitlab.trello.model.Folders;
import kz.bitlab.trello.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {
    List<Tasks> findByFolder(Folders folder);
}
