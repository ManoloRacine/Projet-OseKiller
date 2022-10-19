package com.osekiller.projet.model;

import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Student;
import lombok.*;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@NoArgsConstructor
@Data
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @NonNull private Company interviewer;
    @ManyToOne(cascade = CascadeType.MERGE)
    @NonNull private Student interviewee;
    @ElementCollection(fetch = FetchType.EAGER)
    @NonNull private List<LocalDate> proposedInterviewDates;

    public Interview(@NonNull Company interviewer, @NonNull Student interviewee, @NonNull List<LocalDate> proposedInterviewDates) {
        if(proposedInterviewDates.size() != 3)
            throw new IllegalArgumentException("List of proposed interview dates must contain exactly 3 dates");

        this.interviewer = interviewer;
        this.interviewee = interviewee;
        this.proposedInterviewDates = proposedInterviewDates;
    }

    public void setProposedInterviewDates(List<LocalDate> proposedInterviewDates) {
        if(proposedInterviewDates.size() != 3)
            throw new IllegalArgumentException("List of proposed interview dates must contain exactly 3 dates");

        this.proposedInterviewDates = proposedInterviewDates;
    }
}
