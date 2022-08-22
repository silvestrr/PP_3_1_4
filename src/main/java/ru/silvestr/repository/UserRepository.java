package ru.silvestr.repository;

import ru.silvestr.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select u from User u join fetch u.roles where u.username = :username")
    User findByUsername(@Param("username")String username);
}