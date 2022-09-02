package com.webmister.semicolon.repository;

import com.webmister.semicolon.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findReportByReportId(Long reportId);



}
