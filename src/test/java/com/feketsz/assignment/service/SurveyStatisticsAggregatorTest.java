package com.feketsz.assignment.service;

import com.feketsz.assignment.dto.SurveyStatisticsDto;
import com.feketsz.assignment.helper.SurveyStatisticsAggregator;
import com.feketsz.assignment.mapper.SurveyStatisticsMapper;
import com.feketsz.assignment.model.Participation;
import com.feketsz.assignment.model.Status;
import com.feketsz.assignment.model.Survey;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SurveyStatisticsAggregatorTest {

    private final SurveyStatisticsAggregator aggregator =
            new SurveyStatisticsAggregator(Mappers.getMapper(SurveyStatisticsMapper.class));

    @Test
    void buildSurveyStatisticsAggregatesCountsAndRoundsAverage() {
        Survey survey = new Survey();
        survey.setId(1);
        survey.setName("Survey 1");

        Participation completed = newParticipation(survey, Status.COMPLETED, 10);
        Participation filtered = newParticipation(survey, Status.FILTERED, 11);
        Participation rejected = newParticipation(survey, Status.REJECTED, 13);

        List<SurveyStatisticsDto> result = aggregator.buildSurveyStatistics(
                List.of(survey),
                List.of(completed, filtered, rejected)
        );

        SurveyStatisticsDto dto = result.getFirst();
        assertEquals(1, dto.surveyId());
        assertEquals("Survey 1", dto.surveyName());
        assertEquals(1L, dto.numberOfCompletes());
        assertEquals(1L, dto.numberOfFilteredParticipants());
        assertEquals(1L, dto.numberOfRejectedParticipants());
        assertEquals(11.33, dto.averageLengthMinutes());
    }

    @Test
    void buildSurveyStatisticsReturnsNullAverageWhenNoLengthValues() {
        Survey survey = new Survey();
        survey.setId(2);
        survey.setName("Survey 2");

        Participation completedWithoutLength = newParticipation(survey, Status.COMPLETED, null);

        List<SurveyStatisticsDto> result = aggregator.buildSurveyStatistics(
                List.of(survey),
                List.of(completedWithoutLength)
        );

        SurveyStatisticsDto dto = result.getFirst();
        assertNull(dto.averageLengthMinutes());
    }

    @Test
    void buildSurveyStatisticsIncludesSurveysWithoutParticipation() {
        Survey survey = new Survey();
        survey.setId(3);
        survey.setName("Survey 3");

        List<SurveyStatisticsDto> result = aggregator.buildSurveyStatistics(List.of(survey), List.of());
        SurveyStatisticsDto dto = result.getFirst();

        assertEquals(0L, dto.numberOfCompletes());
        assertEquals(0L, dto.numberOfFilteredParticipants());
        assertEquals(0L, dto.numberOfRejectedParticipants());
        assertNull(dto.averageLengthMinutes());
    }

    private Participation newParticipation(Survey survey, Status status, Integer length) {
        Participation participation = new Participation();
        participation.setSurvey(survey);
        participation.setStatus(status);
        participation.setLength(length);
        return participation;
    }
}
