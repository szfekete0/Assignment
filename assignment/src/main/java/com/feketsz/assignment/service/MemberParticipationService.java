package com.feketsz.assignment.service;

import com.feketsz.assignment.dto.MemberPointsOverviewDto;
import com.feketsz.assignment.dto.SurveyResultDto;
import com.feketsz.assignment.mapper.MemberPointsMapper;
import com.feketsz.assignment.model.Member;
import com.feketsz.assignment.model.Status;
import com.feketsz.assignment.repository.MemberRepository;
import com.feketsz.assignment.repository.ParticipationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
public class MemberParticipationService {

    private final MemberRepository memberRepository;
    private final ParticipationRepository participationRepository;
    private final MemberPointsMapper memberPointsMapper;

    public MemberParticipationService(
            MemberRepository memberRepository,
            ParticipationRepository participationRepository,
            MemberPointsMapper memberPointsMapper
    ) {
        this.memberRepository = memberRepository;
        this.participationRepository = participationRepository;
        this.memberPointsMapper = memberPointsMapper;
    }

    public MemberPointsOverviewDto getCollectedPointsByMemberId(Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found: " + memberId));

        List<SurveyResultDto> surveyResults = participationRepository.findByMemberId(memberId)
                .stream()
                .map(memberPointsMapper::toSurveyResultDto)
                .filter(result -> result != null && result.points() != null)
                .sorted(Comparator.comparing(SurveyResultDto::name))
                .toList();

        int totalPoints = surveyResults.stream()
                .mapToInt(SurveyResultDto::points)
                .sum();

        return memberPointsMapper.toMemberPointsOverviewDto(member, totalPoints, surveyResults);
    }

    public List<SurveyResultDto> getCompletedSurveysByMemberId(Integer memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found: " + memberId);
        }

        return participationRepository.findCompletedSurveysByMemberId(memberId, Status.COMPLETED)
                .stream()
                .map(memberPointsMapper::toSurveyResultDto)
                .toList();
    }
}
