package com.osekiller.projet.service.implementation;

import com.osekiller.projet.model.Interview;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.InterviewRepository;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.InterviewService;
import com.osekiller.projet.service.NotificationsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class InterviewServiceImpl implements InterviewService {
    StudentRepository studentRepository;
    OfferRepository offerRepository;
    InterviewRepository interviewRepository;

    NotificationsService notificationsService;
    @Override
    public void inviteApplicantToInterview(long studentId, long offerId, List<LocalDate> proposedInterviewDates) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        for (LocalDate date : proposedInterviewDates) {
          if (date.isBefore(LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Interview interview;

        try {
            interview = new Interview(offer, student, proposedInterviewDates);
        } catch (IllegalArgumentException e){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        interviewRepository.save(interview);

        notificationsService.addNotification(studentId,
                "Des invitations à une interview vous ont étés envoyés pour une offre de " +
                offer.getOwner().getName() + " pour une position en tant que " + offer.getPosition());
    }
}
