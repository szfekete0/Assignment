package com.feketsz.assignment.mapper;

import com.feketsz.assignment.dto.MemberDto;
import com.feketsz.assignment.dto.MemberPointsOverviewDto;
import com.feketsz.assignment.dto.SurveyResultDto;
import com.feketsz.assignment.model.Member;
import com.feketsz.assignment.model.Participation;
import com.feketsz.assignment.model.Status;
import com.feketsz.assignment.model.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberPointsMapper {

    @Mapping(target = "id", source = "survey.id")
    @Mapping(target = "name", source = "survey.name")
    @Mapping(target = "points", expression = "java(resolvePoints(participation))")
    SurveyResultDto toSurveyResultDto(Participation participation);

    @Mapping(target = "member", expression = "java(toMemberDto(member))")
    @Mapping(target = "totalPoints", source = "totalPoints")
    @Mapping(target = "surveyPoints", source = "surveyPoints")
    MemberPointsOverviewDto toMemberPointsOverviewDto(Member member, Integer totalPoints, List<SurveyResultDto> surveyPoints);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "points", source = "completionPoints")
    SurveyResultDto toSurveyResultDto(Survey survey);

    default MemberDto toMemberDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getFullName(),
                member.getEmailAddress(),
                member.getIsActive()
        );
    }

    default Integer resolvePoints(Participation participation) {
        if (participation.getStatus() == Status.COMPLETED) {
            return participation.getSurvey().getCompletionPoints();
        }
        if (participation.getStatus() == Status.FILTERED) {
            return participation.getSurvey().getFilteredPoints();
        }
        return null;
    }
}
