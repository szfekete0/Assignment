package com.feketsz.assignment.service;

import com.feketsz.assignment.dto.MemberDto;
import com.feketsz.assignment.mapper.MemberMapper;
import com.feketsz.assignment.model.Status;
import com.feketsz.assignment.repository.MemberRepository;
import com.feketsz.assignment.repository.ParticipationRepository;
import com.feketsz.assignment.repository.SurveyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SurveyParticipationService {

    private final ParticipationRepository participationRepository;
    private final SurveyRepository surveyRepository;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public SurveyParticipationService(
            ParticipationRepository participationRepository,
            SurveyRepository surveyRepository,
            MemberRepository memberRepository,
            MemberMapper memberMapper
    ) {
        this.participationRepository = participationRepository;
        this.surveyRepository = surveyRepository;
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public List<MemberDto> getSubmittersBySurveyId(Integer surveyId) {
        if (!surveyRepository.existsById(surveyId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey not found: " + surveyId);
        }

        return participationRepository.findCompletedRespondentsBySurveyId(surveyId, Status.COMPLETED)
                .stream()
                .map(memberMapper::toMemberDto)
                .toList();
    }

    public List<MemberDto> getInviteesBySurveyId(Integer surveyId) {
        if (!surveyRepository.existsById(surveyId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey not found: " + surveyId);
        }

        return memberRepository.findActiveMembersNotParticipatedInSurvey(surveyId)
                .stream()
                .map(memberMapper::toMemberDto)
                .toList();
    }
}
