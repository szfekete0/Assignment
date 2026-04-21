package com.feketsz.assignment.model.converter;

import com.feketsz.assignment.model.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StatusConverter implements AttributeConverter<Status, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Status status) {
        return status == null ? null : status.getCode();
    }

    @Override
    public Status convertToEntityAttribute(Integer code) {
        return Status.fromCode(code);
    }
}
