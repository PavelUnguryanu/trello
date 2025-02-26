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
public class TaskCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @ManyToMany(mappedBy = "categories") // Обратная связь
    private List<Folders> folders;

    public String getTextFolder(){
        List<Folders> folders = this.getFolders();
        String text = "";
        if (folders != null){
            for (Folders folder: folders){
                if (text.length() > 0){
                    text = text + ", " + folder.getName();
                } else {
                    text = folder.getName();
                }


            }
        }
        return text;
    }
}
