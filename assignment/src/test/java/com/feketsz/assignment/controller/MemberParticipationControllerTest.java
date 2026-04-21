package com.feketsz.assignment.controller;

import com.feketsz.assignment.dto.MemberDto;
import com.feketsz.assignment.dto.MemberPointsOverviewDto;
import com.feketsz.assignment.dto.SurveyResultDto;
import com.feketsz.assignment.service.MemberParticipationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberParticipationControllerTest {

    @Mock
    private MemberParticipationService memberParticipationService;

    @InjectMocks
    private MemberParticipationController controller;

    @Test
    void getCollectedPointsDelegatesToService() {
        MemberPointsOverviewDto dto = new MemberPointsOverviewDto(
                new MemberDto(10, "Member Name", "member@example.com", true),
                12,
                List.of(new SurveyResultDto(1, "Survey A", 12))
        );
        when(memberParticipationService.getCollectedPointsByMemberId(10)).thenReturn(dto);

        MemberPointsOverviewDto result = controller.getCollectedPoints(10);

        assertEquals(dto, result);
    }

    @Test
    void getCompletedSurveysByMemberIdDelegatesToService() {
        List<SurveyResultDto> dtos = List.of(new SurveyResultDto(1, "Survey A", 20));
        when(memberParticipationService.getCompletedSurveysByMemberId(10)).thenReturn(dtos);

        List<SurveyResultDto> result = controller.getCompletedSurveysByMemberId(10);

        assertEquals(dtos, result);
    }
}
