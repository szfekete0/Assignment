package com.feketsz.assignment.service;

import com.feketsz.assignment.dto.SurveyStatisticsDto;
import com.feketsz.assignment.helper.SurveyStatisticsAggregator;
import com.feketsz.assignment.model.Survey;
import com.feketsz.assignment.repository.ParticipationRepository;
import com.feketsz.assignment.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SurveyStatisticsService {

    private final ParticipationRepository participationRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyStatisticsAggregator surveyStatisticsAggregator;

    public SurveyStatisticsService(
            ParticipationRepository participationRepository,
            SurveyRepository surveyRepository,
            SurveyStatisticsAggregator surveyStatisticsAggregator
    ) {
        this.participationRepository = participationRepository;
        this.surveyRepository = surveyRepository;
        this.surveyStatisticsAggregator = surveyStatisticsAggregator;
    }

    public List<SurveyStatisticsDto> getSurveyStatistics() {
        List<Survey> surveys = surveyRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Survey::getId))
                .toList();

        return surveyStatisticsAggregator.buildSurveyStatistics(surveys, participationRepository.findAll());
    }
}
