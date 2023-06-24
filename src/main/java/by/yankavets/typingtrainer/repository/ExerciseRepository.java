package by.yankavets.typingtrainer.repository;

import by.yankavets.typingtrainer.model.entity.training.Exercise;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query(
            nativeQuery = true,
            value = "UPDATE users_exercise " +
                    "SET access = true " +
                    "WHERE user_id = ?1 AND exercise_id IN (SELECT min(ex.id) " +
                    "                      FROM exercise ex " +
                    "                      JOIN lesson l ON l.id = ex.lesson_id " +
                    "                      JOIN course c ON l.course_id = c.id " +
                    "                      GROUP BY c.id)"
    )
    @Modifying
    void enableFirstExercise(Long userId);


    @Query(
            nativeQuery = true,
            value = "SELECT e.id, e.exercise, ue.access, ue.mistake_count, ue.typing_speed, e.lesson_id " +
                    "FROM exercise e " +
                    "LEFT JOIN users_exercise ue on e.id = ue.exercise_id AND ue.user_id = ?1 " +
                    "JOIN lesson l on l.id = e.lesson_id " +
                    "JOIN course c on c.id = l.course_id AND c.id = ?2"
    )
   List<Exercise> findWithAccess(long userId, long courseId);

}
