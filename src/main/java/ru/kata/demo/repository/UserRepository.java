package ru.kata.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ru.kata.demo.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select distinct u from User u join fetch u.roles where u.email=(:email)")
    UserDetails findByEmail(String email);


}
