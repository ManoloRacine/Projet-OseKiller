package com.osekiller.projet.service.implementation;

import com.osekiller.projet.repository.InterviewRepository;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.InterviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class InterviewServiceImpl implements InterviewService {
    StudentRepository studentRepository;
    CompanyRepository companyRepository;
    InterviewRepository interviewRepository;
    @Override
    public void inviteApplicantToInterview(long studentId, long companyId, List<LocalDate> proposedInterviewDates) {

    }
}