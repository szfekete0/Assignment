package com.feketsz.assignment.mapper;

import com.feketsz.assignment.dto.SurveyStatisticsDto;
import com.feketsz.assignment.model.Survey;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SurveyStatisticsMapperTest {

    private final SurveyStatisticsMapper mapper = Mappers.getMapper(SurveyStatisticsMapper.class);

    @Test
    void toSurveyStatisticsDtoMapsAllFields() {
        Survey survey = new Survey();
        survey.setId(3);
        survey.setName("Survey 3");

        SurveyStatisticsDto dto = mapper.toSurveyStatisticsDto(survey, 7L, 2L, 1L, 13.48);

        assertEquals(3, dto.surveyId());
        assertEquals("Survey 3", dto.surveyName());
        assertEquals(7L, dto.numberOfCompletes());
        assertEquals(2L, dto.numberOfFilteredParticipants());
        assertEquals(1L, dto.numberOfRejectedParticipants());
        assertEquals(13.48, dto.averageLengthMinutes());
    }
}
