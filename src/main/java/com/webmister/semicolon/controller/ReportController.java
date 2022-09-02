package com.webmister.semicolon.controller;

import com.webmister.semicolon.domain.Report;
import com.webmister.semicolon.domain.UserInfo;
import com.webmister.semicolon.request.DeleteReportRequest;
import com.webmister.semicolon.request.FindReportOnlyOneRequest;
import com.webmister.semicolon.request.UploadRequest;
import com.webmister.semicolon.response.FindReportOnlyOneResponse;
import com.webmister.semicolon.service.ReportService;
import com.webmister.semicolon.service.UserInfoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReportController {

    final ReportService reportService;
    final UserInfoService userInfoService;

    public ReportController(ReportService reportService, UserInfoService userInfoService) {
        this.reportService = reportService;
        this.userInfoService = userInfoService;
    }
    @PostMapping("/{userNickname}/reportUpload")
    public ResponseEntity<Boolean> reportUpload(@PathVariable("userNickname") String userNickname,
            @RequestBody UploadRequest uploadRequest){
        UserInfo userInfo = userInfoService.findUserInfoByUserNickname(userNickname);
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");

        try {
            reportService.reportUpload(uploadRequest, userInfo);
        }catch (Exception e){
            return new ResponseEntity<>(Boolean.FALSE, resHeaders, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(Boolean.TRUE, resHeaders, HttpStatus.OK);
    }

    @GetMapping("/{userNickname}/{reportId}")
    public ResponseEntity<FindReportOnlyOneResponse> createUser(@PathVariable("userNickname") String userNickname,@PathVariable("reportId") Long reportId ,
                                                              @RequestBody FindReportOnlyOneRequest findReportOnlyOneRequest){
        UserInfo userInfo = userInfoService.findUserInfoByUserNickname(userNickname);
        Report report1 = reportService.findReportByUserIdAndReportId(userInfo.getUserInfoId(), reportId);


        FindReportOnlyOneResponse findReportOnlyOneResponse = new FindReportOnlyOneResponse(report1);

        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<>(findReportOnlyOneResponse ,resHeaders,  HttpStatus.OK);
    }

    @DeleteMapping("/{userNickname}/reportDelete")
    public ResponseEntity<Boolean> reportDelete(@PathVariable("userNickname") String userNickname,
            @RequestBody DeleteReportRequest deleteReportRequest){
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");

        try {
            reportService.deleteReport(deleteReportRequest, userInfoService.findUserInfoByUserNickname(userNickname).getUserInfoId());
        }catch (Exception e){
            return new ResponseEntity<>(Boolean.FALSE, resHeaders, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Boolean.TRUE, resHeaders, HttpStatus.OK);
    }
}
