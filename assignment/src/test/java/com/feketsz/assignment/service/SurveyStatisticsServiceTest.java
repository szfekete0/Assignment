package com.feketsz.assignment.service;

import com.feketsz.assignment.dto.SurveyStatisticsDto;
import com.feketsz.assignment.helper.SurveyStatisticsAggregator;
import com.feketsz.assignment.model.Participation;
import com.feketsz.assignment.model.Survey;
import com.feketsz.assignment.repository.ParticipationRepository;
import com.feketsz.assignment.repository.SurveyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SurveyStatisticsServiceTest {

    @Mock
    private ParticipationRepository participationRepository;
    @Mock
    private SurveyRepository surveyRepository;
    @Mock
    private SurveyStatisticsAggregator surveyStatisticsAggregator;

    @InjectMocks
    private SurveyStatisticsService service;

    @Test
    void getSurveyStatisticsSortsSurveysAndDelegatesToAggregator() {
        Survey survey2 = new Survey();
        survey2.setId(2);
        Survey survey1 = new Survey();
        survey1.setId(1);

        Participation participation = new Participation();
        List<SurveyStatisticsDto> expected = List.of(
                new SurveyStatisticsDto(1, "Survey 1", 1L, 0L, 0L, 10.0)
        );

        when(surveyRepository.findAll()).thenReturn(List.of(survey2, survey1));
        when(participationRepository.findAll()).thenReturn(List.of(participation));
        when(surveyStatisticsAggregator.buildSurveyStatistics(List.of(survey1, survey2), List.of(participation)))
                .thenReturn(expected);

        List<SurveyStatisticsDto> result = service.getSurveyStatistics();

        assertEquals(expected, result);

        ArgumentCaptor<List<Survey>> surveysCaptor = ArgumentCaptor.forClass(List.class);
        verify(surveyStatisticsAggregator).buildSurveyStatistics(surveysCaptor.capture(), org.mockito.ArgumentMatchers.eq(List.of(participation)));
        assertEquals(List.of(survey1, survey2), surveysCaptor.getValue());
    }
}
