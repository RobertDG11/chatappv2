package com.robert.chatapp.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "privilege")
public class Privilege {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(
            name = "user_type_to_privilege",
            joinColumns = @JoinColumn(name = "privilege_id"),
            inverseJoinColumns = @JoinColumn(name = "user_type_id")
    )
    private List<UserType> userTypes;

    public Privilege() {
    }

    public Privilege(String name) {
        this.name = name;
    }

    public void addUserType(UserType userType) {

        if (userTypes == null) {
            userTypes = new ArrayList<>();
        }

        userTypes.add(userType);
    }

    public void removeUserType(UserType userType) {

        if (!userTypes.isEmpty()) {
            userTypes.remove(userType);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Privilege privilege = (Privilege) o;
        return Objects.equals(name, privilege.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "id=" + id +
                ", name='" + name  +
                '}';
    }
}
