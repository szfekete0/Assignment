package com.feketsz.assignment.mapper;

import com.feketsz.assignment.dto.MemberDto;
import com.feketsz.assignment.model.Member;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberMapperTest {

    private final MemberMapper mapper = Mappers.getMapper(MemberMapper.class);

    @Test
    void toMemberDtoMapsFields() {
        Member member = new Member();
        member.setId(10);
        member.setFullName("Member Name10");
        member.setEmailAddress("member10@example.com");
        member.setIsActive(true);

        MemberDto dto = mapper.toMemberDto(member);

        assertEquals(10, dto.id());
        assertEquals("Member Name10", dto.fullName());
        assertEquals("member10@example.com", dto.emailAddress());
        assertEquals(true, dto.isActive());
    }
}
