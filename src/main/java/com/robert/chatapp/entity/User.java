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

    @Column(name = "username",
            unique = true)
    private String username;

    @Column(name = "email_address",
            unique = true)
    private String emailAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "notification_type",
    insertable = false)
    private String notificationType = "email";

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "is_active",
    insertable = false)
    private Boolean active = false;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserGroup> groups;

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST},
            orphanRemoval = true
    )
    private List<Message> messages;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST},
            mappedBy = "userId",
            orphanRemoval = true)
    private VerificationToken verificationToken;

    @OneToMany(mappedBy = "createdBy",
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.PERSIST},
            orphanRemoval = true)
    private List<Group> createdGroups = new ArrayList<>();


    public void addGroup(Group group, UserType type) {

        if (groups == null) {

            groups = new ArrayList<>();
        }

        UserGroup userGroup = new UserGroup(this, group);
        userGroup.setUserType(type);

        groups.add(userGroup);

        if (group.getUsers() == null) {

            group.setUsers(new ArrayList<>());
        }

        group.getUsers().add(userGroup);
    }

    public void removeGroup(Group group) {
        groups.forEach(userGroup -> {

            if (userGroup.getUser().equals(this) &&
                    userGroup.getGroup().equals(group)) {

                userGroup.getGroup().getUsers().remove(userGroup);
                userGroup.setUser(null);
                userGroup.setGroup(null);
            }
        });
    }

    public void blockUser(Group group) {

        groups.forEach(userGroup -> {

            if (userGroup.getUser().equals(this) &&
                    userGroup.getGroup().equals(group)) {

                userGroup.setBlocked(true);
            }
        });
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


    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public List<Group> getCreatedGroups() {
        return createdGroups;
    }

    public void setCreatedGroups(List<Group> createdGroups) {
        this.createdGroups = createdGroups;
    }

    public VerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.verificationToken = verificationToken;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", notificationType='" + notificationType + '\'' +
                ", dateCreated=" + dateCreated +
                ", active=" + active +
                '}';
    }
}
