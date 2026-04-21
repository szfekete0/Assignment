package com.feketsz.assignment.repository;

import com.feketsz.assignment.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    @Query("""
            select m
            from Member m
            where m.isActive = true
              and m.id not in (
                select p.member.id
                from Participation p
                where p.survey.id = :surveyId
              )
            order by m.id
            """)
    List<Member> findActiveMembersNotParticipatedInSurvey(@Param("surveyId") Integer surveyId);
}
