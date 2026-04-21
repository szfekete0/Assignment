package com.feketsz.assignment.service;

import com.feketsz.assignment.dto.MemberDto;
import com.feketsz.assignment.dto.MemberPointsOverviewDto;
import com.feketsz.assignment.dto.SurveyResultDto;
import com.feketsz.assignment.mapper.MemberPointsMapper;
import com.feketsz.assignment.model.Member;
import com.feketsz.assignment.model.Participation;
import com.feketsz.assignment.model.Status;
import com.feketsz.assignment.model.Survey;
import com.feketsz.assignment.repository.MemberRepository;
import com.feketsz.assignment.repository.ParticipationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberParticipationServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ParticipationRepository participationRepository;
    @Mock
    private MemberPointsMapper memberPointsMapper;

    @InjectMocks
    private MemberParticipationService service;

    @Test
    void getCollectedPointsByMemberIdThrowsNotFoundWhenMemberMissing() {
        when(memberRepository.findById(10)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> service.getCollectedPointsByMemberId(10));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void getCollectedPointsByMemberIdCalculatesTotalAndUsesMapper() {
        Member member = new Member();
        member.setId(10);
        member.setFullName("Member Name10");
        member.setEmailAddress("member10@example.com");

        Participation p1 = newParticipation("Survey B", Status.COMPLETED, 20, 5);
        Participation p2 = newParticipation("Survey A", Status.FILTERED, 20, 7);
        Participation p3 = newParticipation("Survey C", Status.REJECTED, 20, 5);

        SurveyResultDto dtoB = new SurveyResultDto(2, "Survey B", 20);
        SurveyResultDto dtoA = new SurveyResultDto(1, "Survey A", 7);
        MemberPointsOverviewDto overview = new MemberPointsOverviewDto(
                new MemberDto(10, "Member Name10", "member10@example.com", null),
                27,
                List.of(dtoA, dtoB)
        );

        when(memberRepository.findById(10)).thenReturn(Optional.of(member));
        when(participationRepository.findByMemberId(10)).thenReturn(List.of(p1, p2, p3));
        when(memberPointsMapper.toSurveyResultDto(p1)).thenReturn(dtoB);
        when(memberPointsMapper.toSurveyResultDto(p2)).thenReturn(dtoA);
        when(memberPointsMapper.toSurveyResultDto(p3)).thenReturn(null);
        when(memberPointsMapper.toMemberPointsOverviewDto(member, 27, List.of(dtoA, dtoB))).thenReturn(overview);

        MemberPointsOverviewDto actual = service.getCollectedPointsByMemberId(10);

        assertEquals(overview, actual);
    }

    @Test
    void getCompletedSurveysByMemberIdThrowsNotFoundWhenMemberMissing() {
        when(memberRepository.existsById(5)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> service.getCompletedSurveysByMemberId(5));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void getCompletedSurveysByMemberIdMapsResult() {
        Survey survey = new Survey();
        survey.setId(9);
        survey.setName("Survey 9");
        survey.setCompletionPoints(30);

        SurveyResultDto dto = new SurveyResultDto(9, "Survey 9", 30);

        when(memberRepository.existsById(5)).thenReturn(true);
        when(participationRepository.findCompletedSurveysByMemberId(5, Status.COMPLETED)).thenReturn(List.of(survey));
        when(memberPointsMapper.toSurveyResultDto(survey)).thenReturn(dto);

        List<SurveyResultDto> result = service.getCompletedSurveysByMemberId(5);

        assertEquals(1, result.size());
        assertEquals(dto, result.getFirst());
    }

    private Participation newParticipation(String surveyName, Status status, int completionPoints, int filteredPoints) {
        Survey survey = new Survey();
        survey.setName(surveyName);
        survey.setCompletionPoints(completionPoints);
        survey.setFilteredPoints(filteredPoints);

        Participation participation = new Participation();
        participation.setSurvey(survey);
        participation.setStatus(status);
        return participation;
    }
}
