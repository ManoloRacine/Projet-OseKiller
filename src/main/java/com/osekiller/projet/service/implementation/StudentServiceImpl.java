package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.InterviewDto;
import com.osekiller.projet.controller.payload.response.StudentWithCvStateDto;
import com.osekiller.projet.model.ERole;
import com.osekiller.projet.model.Interview;
import com.osekiller.projet.repository.InterviewRepository;
import com.osekiller.projet.service.CurrentDateFactory;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.CvRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.NotificationsService;
import com.osekiller.projet.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private CvRepository cvRepository;

    private CurrentDateFactory currentDateFactory;

    private InterviewRepository interviewRepository;

    private NotificationsService notificationsService ;

    private final int LAST_MONTH = 5 ;
    private final int LAST_DAY = 31 ;

    @Override
    public void validateCV(long studentId, String feedback) {
        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        student.get().setCvRejected(false);
        student.get().getCv().setValidated(true);
        student.get().getCv().setFeedback(feedback);
        studentRepository.save(student.get());
        notificationsService.addNotification(studentId, "votre CV a été validé");
    }

    @Override
    public void invalidateCV(long studentId, String feedback) {
        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        student.get().setCvRejected(true);
        student.get().getCv().setValidated(false);
        student.get().getCv().setFeedback(feedback);
        studentRepository.save(student.get());
        notificationsService.addNotification(studentId, "votre CV est invalide");
    }

    @Override
    public List<GeneralOfferDto> getApplications(long studentId) {
        Student student = studentRepository.findByIdAndFetchApplications(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return student.getApplications().stream().map(GeneralOfferDto::from).toList();
    }

    @Override
    public void saveCV(MultipartFile cv, long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        String fileName = StringUtils.cleanPath(cv.getOriginalFilename());
        student.getCv().setPdfName(fileName);

        try {
            student.getCv().setPdf(cv.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        studentRepository.save(student) ;
        notificationsService.addNotificationForRole(ERole.MANAGER.name(), student.getName() + " a ajouté un CV");
    }

    public Resource getCV(long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;
        if (cvRepository.findById(student.getCv().getId()).get().getPdf() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;

        return new ByteArrayResource(student.getCv().getPdf());
    }
    public List<StudentWithCvStateDto> getStudents() {
        return studentRepository.findAll().stream().map(
                StudentWithCvStateDto::from
        ).toList() ;
    }

    public StudentWithCvStateDto getStudent(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return StudentWithCvStateDto.from(student);
    }

    @Override
    public List<InterviewDto> getInterviews(long studentId) {
        if(!studentRepository.existsById(studentId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return interviewRepository.findAllByInterviewee_Id(studentId).stream().map(InterviewDto::from).toList();
    }

    public StudentWithCvStateDto updateSession(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        student.setSessionYear(getCurrentSession());
        studentRepository.save(student) ;
        return StudentWithCvStateDto.from(student) ;
    }

    public int getCurrentSession() {
        LocalDate currentDate = currentDateFactory.getCurrentDate() ;
        if (currentDate.isBefore(LocalDate.of(currentDate.getYear(), LAST_MONTH, LAST_DAY))) {
            return currentDate.getYear();
        }
        else {
            return currentDate.getYear() + 1;
        }
    }

    @Override
    public Boolean studentExists(long id) {
        return studentRepository.existsById(id);
    }

    @Override
    public Boolean studentOwnsInterview(long studentId, long interviewId) {
        Interview interview = interviewRepository.findById(interviewId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return interview.getInterviewee().getId().equals(studentId);
    }

    @Override
    public void confirmInterview(long id, String chosenDate) {
        Interview interview = interviewRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(!interview.getProposedInterviewDates().contains(LocalDate.parse(chosenDate))){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        interview.setChosenInterviewDate(LocalDate.parse(chosenDate));
        interviewRepository.save(interview);
        notificationsService.addNotification(interview.getOffer().getOwner().getId(),
                interview.getInterviewee().getName() + " a acceptée une date d'interview");
    }

}
