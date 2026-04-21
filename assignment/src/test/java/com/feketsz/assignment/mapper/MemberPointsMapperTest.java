package com.feketsz.assignment.mapper;

import com.feketsz.assignment.dto.MemberDto;
import com.feketsz.assignment.dto.MemberPointsOverviewDto;
import com.feketsz.assignment.dto.SurveyResultDto;
import com.feketsz.assignment.model.Member;
import com.feketsz.assignment.model.Participation;
import com.feketsz.assignment.model.Status;
import com.feketsz.assignment.model.Survey;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MemberPointsMapperTest {

    private final MemberPointsMapper mapper = Mappers.getMapper(MemberPointsMapper.class);

    @Test
    void toSurveyResultDtoMapsCompletedStatusToCompletionPoints() {
        Participation participation = new Participation();
        participation.setStatus(Status.COMPLETED);
        participation.setSurvey(newSurvey(1, "Survey A", 45, 3));

        SurveyResultDto dto = mapper.toSurveyResultDto(participation);

        assertEquals(1, dto.id());
        assertEquals("Survey A", dto.name());
        assertEquals(45, dto.points());
    }

    @Test
    void toSurveyResultDtoMapsFilteredStatusToFilteredPoints() {
        Participation participation = new Participation();
        participation.setStatus(Status.FILTERED);
        participation.setSurvey(newSurvey(2, "Survey B", 40, 7));

        SurveyResultDto dto = mapper.toSurveyResultDto(participation);

        assertEquals(2, dto.id());
        assertEquals("Survey B", dto.name());
        assertEquals(7, dto.points());
    }

    @Test
    void toSurveyResultDtoReturnsNullPointsForRejectedStatus() {
        Participation participation = new Participation();
        participation.setStatus(Status.REJECTED);
        participation.setSurvey(newSurvey(2, "Survey B", 40, 7));

        SurveyResultDto dto = mapper.toSurveyResultDto(participation);

        assertNull(dto.points());
    }

    @Test
    void toMemberPointsOverviewDtoMapsAllFields() {
        Member member = new Member();
        member.setId(10);
        member.setFullName("Member Name10");
        member.setEmailAddress("member10@example.com");

        List<SurveyResultDto> surveyResults = List.of(new SurveyResultDto(1, "Survey A", 10));

        MemberPointsOverviewDto dto = mapper.toMemberPointsOverviewDto(member, 10, surveyResults);

        MemberDto mappedMember = dto.member();
        assertEquals(10, mappedMember.id());
        assertEquals("Member Name10", mappedMember.fullName());
        assertEquals("member10@example.com", mappedMember.emailAddress());
        assertEquals(10, dto.totalPoints());
        assertEquals(1, dto.surveyPoints().size());
    }

    @Test
    void toSurveyResultDtoMapsFieldsFromSurvey() {
        Survey survey = newSurvey(6, "Survey C", 25, 5);

        SurveyResultDto dto = mapper.toSurveyResultDto(survey);

        assertEquals(6, dto.id());
        assertEquals("Survey C", dto.name());
        assertEquals(25, dto.points());
    }

    private Survey newSurvey(int id, String name, int completionPoints, int filteredPoints) {
        Survey survey = new Survey();
        survey.setId(id);
        survey.setName(name);
        survey.setCompletionPoints(completionPoints);
        survey.setFilteredPoints(filteredPoints);
        survey.setExpectedCompletes(100);
        return survey;
    }
}
