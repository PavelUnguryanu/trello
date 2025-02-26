package kz.bitlab.trello.repository;

import kz.bitlab.trello.model.Folders;
import kz.bitlab.trello.model.TaskCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<TaskCategories, Long> {
    @Query("SELECT f FROM Folders f JOIN f.categories c WHERE c.id = :categoryId")
    List<Folders> findFoldersByCategoryId(@Param("categoryId") Long categoryId);

}
