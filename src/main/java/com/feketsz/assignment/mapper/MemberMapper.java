package com.feketsz.assignment.mapper;

import com.feketsz.assignment.dto.MemberDto;
import com.feketsz.assignment.model.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberDto toMemberDto(Member member);
}
