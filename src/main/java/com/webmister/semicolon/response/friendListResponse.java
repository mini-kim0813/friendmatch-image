package com.webmister.semicolon.response;

import com.webmister.semicolon.controller.FriendMatchController;
import com.webmister.semicolon.domain.FriendMatch;
import lombok.Data;

import java.util.List;

@Data
public class friendListResponse {
    List<FriendMatch> friendMatchList;
}
