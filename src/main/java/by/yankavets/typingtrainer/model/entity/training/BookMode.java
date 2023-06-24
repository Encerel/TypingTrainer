package by.yankavets.typingtrainer.model.entity.training;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "book_mode")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class BookMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

}
