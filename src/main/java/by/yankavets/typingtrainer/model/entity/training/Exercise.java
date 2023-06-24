package by.yankavets.typingtrainer.model.entity.training;

import by.yankavets.typingtrainer.model.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "exercise")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    @JsonBackReference
    private Lesson lesson;

    @Column(name = "exercise")
    private String exercise;

    @Column(name = "access")
    private boolean access;

    @Column(name = "typing_speed")
    private double typingSpeed;

    @Column(name = "mistake_count")
    private double mistakeCount;


}
