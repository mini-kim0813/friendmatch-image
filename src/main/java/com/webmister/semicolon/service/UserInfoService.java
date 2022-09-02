package com.webmister.semicolon.service;

import com.webmister.semicolon.domain.UserInfo;
import com.webmister.semicolon.enumclass.UserStatus;
import com.webmister.semicolon.repository.UserInfoRepository;
import com.webmister.semicolon.request.DeleteUserRequest;
import com.webmister.semicolon.request.Login;
import com.webmister.semicolon.request.UserInfoRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserInfoService {

    final UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfo findUserInfoById(Long id){
      return userInfoRepository.findById(id).orElse(new UserInfo());
    }

    public UserInfo findUserInfoByUserNickname(String userNickname){
        return userInfoRepository.findUserInfoByUserNickName(userNickname);
    }

    public List<UserInfo> findAll(){
        List<UserInfo> AllUser = userInfoRepository.findAll();
        System.out.println(AllUser);
        return AllUser;
    }

    public boolean checkDupicateEmail(String userEmail) {
        return userInfoRepository.existsByUserEmail(userEmail);
    }

    public boolean checkDupicateUserNickname(String userNickname) {
        return userInfoRepository.existsByUserNickName(userNickname);
    }

    public boolean checkDuplicateUserPassword(String userPassword)  {
        return userInfoRepository.existsByPassword(userPassword);
    }

   public UserInfo login(Login login) {
       return userInfoRepository.findByUserEmailAndPassword(login.getUserEmail(), login.getPassword())
               .orElseThrow(() -> new RuntimeException("로그인에 실패했습니다."));
   }

   public UserInfo updatePasswordService(String email, String password) {
        UserInfo userInfo = userInfoRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일을 찾을 수 없습니다."));
        userInfoRepository.save(userInfo.setPassword(password));
        return userInfo;
   }

    public Boolean signUp(UserInfoRequest userInfoRequest) {
        try {
            userInfoRepository.save(UserInfo.builder()
                    .password(userInfoRequest.getPassword())
                    .userEmail(userInfoRequest.getUserEmail())
                    .userNickName(userInfoRequest.getUserNickName())
                    .userUniqueID(UserStatus.USER)
                    .userProfileImageUrl(userInfoRequest.getUserProfileImageUrl())
                    .userDescription(userInfoRequest.getUserDescription())
                    .build());
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;

        }
    }

    public Boolean deleteUser(String userNickname){
        try{
            userInfoRepository.deleteById(userInfoRepository.findByUserNickName(userNickname).getUserInfoId());
        }catch (Exception e){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
