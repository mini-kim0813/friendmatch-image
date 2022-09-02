package com.webmister.semicolon.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.webmister.semicolon.enumclass.UserStatus;
import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @Column(name = "userInfoId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userInfoId;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userNickName;

    @Column(nullable = false)
    private LocalDateTime userInfoCreateDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userUniqueID;

    @Column
    private String userProfileImageUrl;

    @Column
    private String userDescription;

    @Column
    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Report> reportList = new ArrayList<Report>();

    @Column
    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<Comment>();

    @Column
    @OneToMany(mappedBy = "postFriendId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<FriendMatch> friendMatchList = new ArrayList<FriendMatch>();

    public UserInfo setPassword(String password) {
        this.password = password;
        return this;
    }
    @PrePersist
    public void UserInfoCreatDate(){
        this.userInfoCreateDate = LocalDateTime.now();
    }

}

