package kz.bitlab.trello.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String description; // TEXT

    int status; // 0 - todo, 1 - intest, 2 - done, 3 - failed
    @ManyToOne(fetch = FetchType.LAZY)
    Folders folder;
}
