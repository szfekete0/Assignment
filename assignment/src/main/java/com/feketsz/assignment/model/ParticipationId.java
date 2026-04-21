package com.feketsz.assignment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ParticipationId implements Serializable {

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "survey_id")
    private Integer surveyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParticipationId that = (ParticipationId) o;
        return Objects.equals(memberId, that.memberId) && Objects.equals(surveyId, that.surveyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, surveyId);
    }
}
