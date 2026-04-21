package com.feketsz.assignment.dto;

public record MemberDto(
        Integer id,
        String fullName,
        String emailAddress,
        Boolean isActive
) {
}
