package com.osekiller.projet.service;

import java.time.LocalDate;
import java.util.List;

public interface InterviewService {
    void inviteApplicantToInterview(long studentId, long companyId, List<LocalDate> proposedInterviewDates);
}
