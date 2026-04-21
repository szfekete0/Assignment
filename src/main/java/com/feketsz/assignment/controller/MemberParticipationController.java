package com.feketsz.assignment.controller;

import com.feketsz.assignment.dto.MemberPointsOverviewDto;
import com.feketsz.assignment.dto.SurveyResultDto;
import com.feketsz.assignment.service.MemberParticipationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/member")
public class MemberParticipationController {

    private final MemberParticipationService memberParticipationService;

    @GetMapping("/{memberId}/points")
    public MemberPointsOverviewDto getCollectedPoints(@PathVariable Integer memberId) {
        return memberParticipationService.getCollectedPointsByMemberId(memberId);
    }

    @GetMapping("/{memberId}/surveys/completed")
    public List<SurveyResultDto> getCompletedSurveysByMemberId(@PathVariable Integer memberId) {
        return memberParticipationService.getCompletedSurveysByMemberId(memberId);
    }
}
