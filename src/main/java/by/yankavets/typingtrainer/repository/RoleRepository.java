package by.yankavets.typingtrainer.repository;

import by.yankavets.typingtrainer.model.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}
