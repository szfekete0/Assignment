package com.feketsz.assignment.repository;

import com.feketsz.assignment.model.Member;
import com.feketsz.assignment.model.Participation;
import com.feketsz.assignment.model.ParticipationId;
import com.feketsz.assignment.model.Status;
import com.feketsz.assignment.model.Survey;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, ParticipationId> {

    @Query("""
            select distinct p.member
            from Participation p
            where p.survey.id = :surveyId
              and p.status = :status
            """)
    List<Member> findCompletedRespondentsBySurveyId(
            @Param("surveyId") Integer surveyId,
            @Param("status") Status status
    );

    @Query("""
            select distinct p.survey
            from Participation p
            where p.member.id = :memberId
              and p.status = :status
            """)
    List<Survey> findCompletedSurveysByMemberId(
            @Param("memberId") Integer memberId,
            @Param("status") Status status
    );

    @EntityGraph(attributePaths = "survey")
    List<Participation> findByMemberId(@Param("memberId") Integer memberId);
}
