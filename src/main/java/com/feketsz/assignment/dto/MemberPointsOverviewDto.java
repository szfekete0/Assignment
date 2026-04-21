package com.feketsz.assignment.dto;

import java.util.List;

public record MemberPointsOverviewDto(
        MemberDto member,
        Integer totalPoints,
        List<SurveyResultDto> surveyPoints
) {
}
