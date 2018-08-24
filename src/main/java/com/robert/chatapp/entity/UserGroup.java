package com.robert.chatapp.entity;

import com.robert.chatapp.embeddable.UserGroupId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_to_group")
public class UserGroup {

    @EmbeddedId
    private UserGroupId id;


    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    @Column(name = "is_blocked")
    @GeneratedValue(generator = "0")
    private boolean isBlocked;

    public UserGroup() {
    }

    public UserGroup(User user, Group group) {
        this.user = user;
        this.group = group;
        this.id = new UserGroupId(user.getId(), group.getId());
    }

    public UserGroupId getId() {
        return id;
    }

    public void setId(UserGroupId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        UserGroup userGroup = (UserGroup) o;
        return  Objects.equals(user, userGroup.user) &&
                Objects.equals(group, userGroup.group);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user, group);
    }
}
