package com.cs5500.team209.service;


import com.cs5500.team209.Driver;
import com.cs5500.team209.model.*;
import com.cs5500.team209.repository.ReportRepository;
import org.junit.Test;
import com.cs5500.team209.model.dto.UpdateReportResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Driver.class)
public class ReportServiceTest {

    @TestConfiguration
    static class ReportServiceTestContextConfiguration {

        @Bean
        public ReportService reportService() {
            return new ReportService();
        }
    }

    @Autowired
    private ReportService reportService;

    @MockBean
    private ReportRepository reportRepository;

    @Test
    public void createReport() {
        Report report1 = new Report("Assignment1","student1","student2","result",45.0);
        reportRepository.save(report1);
        Mockito.when(reportRepository.findReportByReportId(report1.getReportId())).thenReturn(report1);
        UpdateReportResult found = reportService.createReport(report1);
        Mockito.when(reportRepository.save(Mockito.any(Report.class))).thenReturn(report1);
    }
}