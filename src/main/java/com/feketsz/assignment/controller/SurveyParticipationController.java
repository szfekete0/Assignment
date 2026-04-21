package com.feketsz.assignment.controller;

import com.feketsz.assignment.dto.MemberDto;
import com.feketsz.assignment.dto.SurveyStatisticsDto;
import com.feketsz.assignment.service.SurveyParticipationService;
import com.feketsz.assignment.service.SurveyStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/survey")
public class SurveyParticipationController {

    private final SurveyParticipationService surveyParticipationService;
    private final SurveyStatisticsService surveyStatisticsService;

    @GetMapping("/{surveyId}/submitters")
    public List<MemberDto> getSubmittersBySurveyId(@PathVariable Integer surveyId) {
        return surveyParticipationService.getSubmittersBySurveyId(surveyId);
    }

    @GetMapping("/{surveyId}/invitees")
    public List<MemberDto> getInviteesBySurveyId(@PathVariable Integer surveyId) {
        return surveyParticipationService.getInviteesBySurveyId(surveyId);
    }

    @GetMapping("/stats")
    public List<SurveyStatisticsDto> getSurveyStatistics() {
        return surveyStatisticsService.getSurveyStatistics();
    }

}
