package com.webmister.semicolon.dto;

import com.webmister.semicolon.domain.FriendMatch;
import com.webmister.semicolon.domain.UserInfo;
import lombok.Data;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class EssentialFriendMatchDto {
    UserInfo postFriendId;
    UserInfo receiveFriendId;


    public EssentialFriendMatchDto(UserInfo postFriendId, UserInfo receiveFriendId){
        this.postFriendId = postFriendId;
        this.receiveFriendId = receiveFriendId;
    }
}
