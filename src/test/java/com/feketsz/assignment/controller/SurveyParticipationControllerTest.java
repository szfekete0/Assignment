package com.feketsz.assignment.controller;

import com.feketsz.assignment.dto.MemberDto;
import com.feketsz.assignment.dto.SurveyStatisticsDto;
import com.feketsz.assignment.service.SurveyParticipationService;
import com.feketsz.assignment.service.SurveyStatisticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SurveyParticipationControllerTest {

    @Mock
    private SurveyParticipationService surveyParticipationService;
    @Mock
    private SurveyStatisticsService surveyStatisticsService;

    @InjectMocks
    private SurveyParticipationController controller;

    @Test
    void getSubmittersBySurveyIdDelegatesToService() {
        List<MemberDto> dtos = List.of(new MemberDto(1, "Member Name1", "member1@example.com", true));
        when(surveyParticipationService.getSubmittersBySurveyId(3)).thenReturn(dtos);

        List<MemberDto> result = controller.getSubmittersBySurveyId(3);

        assertEquals(dtos, result);
    }

    @Test
    void getInviteesBySurveyIdDelegatesToService() {
        List<MemberDto> dtos = List.of(new MemberDto(2, "Member Name2", "member2@example.com", true));
        when(surveyParticipationService.getInviteesBySurveyId(3)).thenReturn(dtos);

        List<MemberDto> result = controller.getInviteesBySurveyId(3);

        assertEquals(dtos, result);
    }

    @Test
    void getSurveyStatisticsDelegatesToService() {
        List<SurveyStatisticsDto> dtos = List.of(new SurveyStatisticsDto(1, "Survey A", 1L, 2L, 3L, 12.34));
        when(surveyStatisticsService.getSurveyStatistics()).thenReturn(dtos);

        List<SurveyStatisticsDto> result = controller.getSurveyStatistics();

        assertEquals(dtos, result);
    }
}
