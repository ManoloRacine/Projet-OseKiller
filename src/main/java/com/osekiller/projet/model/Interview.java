package com.osekiller.projet.model;

import com.osekiller.projet.model.user.Student;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    @NonNull private Offer offer;
    @ManyToOne(cascade = CascadeType.MERGE)
    @NonNull private Student interviewee;
    @ElementCollection(fetch = FetchType.EAGER)
    @NonNull private List<LocalDate> proposedInterviewDates;
    private LocalDate chosenInterviewDate;

    public Interview(@NonNull Offer offer, @NonNull Student interviewee, @NonNull List<LocalDate> proposedInterviewDates) {
        if(proposedInterviewDates.size() != 3)
            throw new IllegalArgumentException("List of proposed interview dates must contain exactly 3 dates");

        this.offer = offer;
        this.interviewee = interviewee;
        this.proposedInterviewDates = proposedInterviewDates;
    }

    public void setProposedInterviewDates(List<LocalDate> proposedInterviewDates) {
        if(proposedInterviewDates.size() != 3)
            throw new IllegalArgumentException("List of proposed interview dates must contain exactly 3 dates");

        this.proposedInterviewDates = proposedInterviewDates;
    }
}
