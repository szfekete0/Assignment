package com.feketsz.assignment.dto;

public record SurveyStatisticsDto(
        Integer surveyId,
        String surveyName,
        Long numberOfCompletes,
        Long numberOfFilteredParticipants,
        Long numberOfRejectedParticipants,
        Double averageLengthMinutes
) {
}
