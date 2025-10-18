package com.renato.projects.redesocial.service;
public class FriendRequestDTO {
    private String fromUserName;
    private String message;

    public FriendRequestDTO(String fromUserName) {
        this.fromUserName = fromUserName;
        this.message = fromUserName + " enviou uma solicitação de amizade!";
    }

    // getters e setters
    public String getFromUserName() { return fromUserName; }
    public String getMessage() { return message; }
}
