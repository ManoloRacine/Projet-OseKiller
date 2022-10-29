package com.osekiller.projet.controller.payload.response;

import com.osekiller.projet.model.Interview;

import javax.validation.constraints.NotNull;
import java.util.List;

public record InterviewDto(
        @NotNull Long interviewId,
        @NotNull Long offerId,
        @NotNull List<String> proposedDates,
        String interviewDate
        ) {

        public static InterviewDto from(Interview interview){
            return new InterviewDto(
                    interview.getId(),
                    interview.getOffer().getId(),
                    interview.getProposedInterviewDates().stream().map(date -> date.toString()).toList(),
                    (interview.getChosenInterviewDate() != null ? interview.getChosenInterviewDate().toString() : null)
            );
        }
}
