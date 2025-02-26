package kz.bitlab.trello.repository;

import kz.bitlab.trello.model.Comments;
import kz.bitlab.trello.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByTask(Tasks task);
}
