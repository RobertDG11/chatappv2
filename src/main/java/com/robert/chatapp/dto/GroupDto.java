package com.robert.chatapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class GroupDto {

    private Long id;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateCreated;

    private boolean isPrivate;

    private ListUserDto createdBy;

    private int numberOfUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public ListUserDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(ListUserDto createdBy) {
        this.createdBy = createdBy;
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public GroupDto setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;

        return this;
    }
}
