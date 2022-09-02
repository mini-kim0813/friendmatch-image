package com.webmister.semicolon.controller;

import com.webmister.semicolon.domain.UserInfo;
import com.webmister.semicolon.dto.EssentialFriendMatchDto;
import com.webmister.semicolon.request.DeleteUserRequest;
import com.webmister.semicolon.request.FindUserOnlyOneRequest;
import com.webmister.semicolon.request.Login;
import com.webmister.semicolon.response.FindUserOnlyOneResponse;
import com.webmister.semicolon.request.UserInfoRequest;
import com.webmister.semicolon.service.FriendMatchService;
import com.webmister.semicolon.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserInfoController {

    final UserInfoService userInfoService;


    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }


    @RequestMapping(value = "/printAll",
                    method = {RequestMethod.GET, RequestMethod.POST}
    )
    public ResponseEntity<List<UserInfo>> printAll(){
        List<UserInfo> userInfoList;

        userInfoList = userInfoService.findAll();

        log.debug(String.valueOf(userInfoList));

        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");

        return new ResponseEntity<>(userInfoList, resHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/get",
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    public ResponseEntity<FindUserOnlyOneResponse> createUser(@RequestBody FindUserOnlyOneRequest findUserOnlyOneRequest){
        UserInfo userInfo = userInfoService.findUserInfoById(findUserOnlyOneRequest.getId());

        FindUserOnlyOneResponse findUserOnlyOneResponse = new FindUserOnlyOneResponse(userInfo);

        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<>(findUserOnlyOneResponse ,resHeaders,  HttpStatus.OK);
    }

    @RequestMapping(value = "/login",
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    public  ResponseEntity<UserInfo> login(@RequestBody Login login){
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        UserInfo userInfo = userInfoService.login(login);
        return new ResponseEntity<>(userInfo ,resHeaders,  HttpStatus.OK);
    }

    @RequestMapping(value = "/{userNickname}",
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    public ResponseEntity<UserInfo> createUser(@PathVariable("userNickname") String userNickname){
        UserInfo user1 = userInfoService.findUserInfoByUserNickname(userNickname);
        log.debug(String.valueOf(user1));
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<>(user1 ,resHeaders,  HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<Boolean> signUp(@RequestBody UserInfoRequest userInfoRequest) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");

        try {
        if ( !userInfoService.checkDupicateUserNickname(userInfoRequest.getUserNickName()) &
                !userInfoService.checkDupicateEmail(userInfoRequest.getUserEmail()))
                userInfoService.signUp(userInfoRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(Boolean.FALSE, resHeaders, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Boolean.TRUE, resHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/userDelete/{userNickname}")
    public ResponseEntity<Boolean> userDelete(@PathVariable("userNickname")String userNickname){
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");

        try {
            userInfoService.deleteUser(userNickname);
        }catch (Exception e){
            return new ResponseEntity<>(Boolean.FALSE, resHeaders, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Boolean.TRUE, resHeaders, HttpStatus.OK);
    }
}
