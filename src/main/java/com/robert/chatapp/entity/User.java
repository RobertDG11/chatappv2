package com.robert.chatapp.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "email_address",
            unique = true)
    private String emailAddress;

    @Column(name = "phone_number",
            unique = true)
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "notification_type",
    insertable = false)
    private String notificationType;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "confirmation_token",
            unique = true)
    private String confirmationToken;

    @Column(name = "is_active",
    insertable = false)
    private Boolean active;

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST},
            orphanRemoval = true
    )
    private List<UserGroup> groups = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST},
            orphanRemoval = true
    )
    private List<Message> messages;

    public User(String firstName, String lastName, String username, String emailAddress, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        dateCreated = new Date();
    }

    public User() {
    }

    public void addGroup(Group group) {
        UserGroup userGroup = new UserGroup(this, group);
        groups.add(userGroup);
        group.getUsers().add(userGroup);
    }

    public void removeGroup(Group group) {
        for (Iterator<UserGroup> iterator = groups.iterator();
             iterator.hasNext(); ) {
            UserGroup userGroup = iterator.next();

            if (userGroup.getUser().equals(this) &&
                    userGroup.getGroup().equals(group)) {

                iterator.remove();
                userGroup.getGroup().getUsers().remove(userGroup);
                userGroup.setUser(null);
                userGroup.setGroup(null);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificatipnType) {
        this.notificationType = notificatipnType;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        active = active;
    }

    public List<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName);
    }
}
