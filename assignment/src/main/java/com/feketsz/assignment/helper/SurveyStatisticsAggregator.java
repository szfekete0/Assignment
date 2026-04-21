package com.feketsz.assignment.helper;

import com.feketsz.assignment.dto.SurveyStatisticsDto;
import com.feketsz.assignment.mapper.SurveyStatisticsMapper;
import com.feketsz.assignment.model.Participation;
import com.feketsz.assignment.model.Status;
import com.feketsz.assignment.model.Survey;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SurveyStatisticsAggregator {

    private final SurveyStatisticsMapper surveyStatisticsMapper;

    public SurveyStatisticsAggregator(SurveyStatisticsMapper surveyStatisticsMapper) {
        this.surveyStatisticsMapper = surveyStatisticsMapper;
    }

    public List<SurveyStatisticsDto> buildSurveyStatistics(List<Survey> surveys, List<Participation> participations) {
        Map<Integer, SurveyStatsAccumulator> statsBySurveyId = new HashMap<>();
        for (Participation participation : participations) {
            Integer surveyId = participation.getSurvey().getId();
            SurveyStatsAccumulator accumulator = statsBySurveyId.computeIfAbsent(surveyId, ignored -> new SurveyStatsAccumulator());
            accumulator.apply(participation);
        }

        return surveys.stream()
                .map(survey -> {
                    SurveyStatsAccumulator accumulator = statsBySurveyId.getOrDefault(survey.getId(), new SurveyStatsAccumulator());
                    return surveyStatisticsMapper.toSurveyStatisticsDto(
                            survey,
                            accumulator.completes,
                            accumulator.filtered,
                            accumulator.rejected,
                            accumulator.averageLengthMinutes()
                    );
                })
                .toList();
    }

    private static class SurveyStatsAccumulator {
        private long completes;
        private long filtered;
        private long rejected;
        private long lengthSum;
        private long lengthCount;

        private void apply(Participation participation) {
            Status status = participation.getStatus();
            if (status == Status.COMPLETED) {
                completes++;
            } else if (status == Status.FILTERED) {
                filtered++;
            } else if (status == Status.REJECTED) {
                rejected++;
            }

            Integer length = participation.getLength();
            if (length != null) {
                lengthSum += length;
                lengthCount++;
            }
        }

        private Double averageLengthMinutes() {
            if (lengthCount == 0) {
                return null;
            }
            return BigDecimal.valueOf((double) lengthSum / lengthCount)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }
    }
}
