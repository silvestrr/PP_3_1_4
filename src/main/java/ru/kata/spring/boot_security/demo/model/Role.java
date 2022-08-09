package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role", unique = true)
    private String userRole;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public Role(String userRole) {
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String role) {
        this.userRole = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return userRole;
    }

    @Override
    public String toString() {
        return userRole;
    }
// При добавлении equals и hashCode приложение отказывается аутентифицировать юзера или админа. Пробрасывает: java.lang.StackOverflowError: null
//	at java.base/sun.net.www.ParseUtil.encodePath(ParseUtil.java:76) ~[na:na]
//	at java.base/jdk.internal.loader.URLClassPath$FileLoader.getResource(URLClassPath.java:1246) ~[na:na]
//	at java.base/jdk.internal.loader.URLClassPath.getResource(URLClassPath.java:322) ~[na:na]
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Role role = (Role) o;
//        return Objects.equals(id, role.id) && Objects.equals(userRole, role.userRole) && Objects.equals(users, role.users);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, userRole, users);
//    }
}
