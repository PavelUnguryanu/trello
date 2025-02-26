package kz.bitlab.trello.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Folders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    @ManyToMany
    List<TaskCategories> categories; // Many To Many
    @OneToMany
    List<Tasks> tasks;


    public String getTextTasks(){
        List<Tasks> tasks = this.getTasks();
        String text = "";
        if (tasks != null){
            for (Tasks task: tasks){
                if (text.length() == 0){
                    text = task.getTitle();
                } else {
                    text = text + ", " + task.getTitle();
                }
            }
        }
        return text;
    }
}
