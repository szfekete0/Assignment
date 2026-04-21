package com.feketsz.assignment.mapper;

import com.feketsz.assignment.dto.SurveyStatisticsDto;
import com.feketsz.assignment.model.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SurveyStatisticsMapper {

    @Mapping(target = "surveyId", source = "survey.id")
    @Mapping(target = "surveyName", source = "survey.name")
    @Mapping(target = "numberOfCompletes", source = "numberOfCompletes")
    @Mapping(target = "numberOfFilteredParticipants", source = "numberOfFilteredParticipants")
    @Mapping(target = "numberOfRejectedParticipants", source = "numberOfRejectedParticipants")
    @Mapping(target = "averageLengthMinutes", source = "averageLengthMinutes")
    SurveyStatisticsDto toSurveyStatisticsDto(
            Survey survey,
            Long numberOfCompletes,
            Long numberOfFilteredParticipants,
            Long numberOfRejectedParticipants,
            Double averageLengthMinutes
    );
}
