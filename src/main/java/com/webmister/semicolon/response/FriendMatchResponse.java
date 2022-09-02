package com.webmister.semicolon.response;

import com.webmister.semicolon.domain.UserInfo;
import com.webmister.semicolon.enumclass.FriendStatus;
import lombok.Data;
import org.apache.catalina.User;

import java.util.List;

@Data
public class FriendMatchResponse {
    String receiveFriendNickname;
    FriendStatus friendStatus;
}
