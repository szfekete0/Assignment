package com.feketsz.assignment.model;

import com.feketsz.assignment.model.converter.StatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "participation")
public class Participation {

    @EmbeddedId
    private ParticipationId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("memberId")
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("surveyId")
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @Convert(converter = StatusConverter.class)
    @Column(name = "status_id", nullable = false)
    private Status status;

    @Column(name = "length")
    private Integer length;

}