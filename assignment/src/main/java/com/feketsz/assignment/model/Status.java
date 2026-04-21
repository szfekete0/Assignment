package com.feketsz.assignment.model;

import lombok.Getter;

@Getter
public enum Status {
    NOT_ASKED(1, "Not asked"),
    REJECTED(2, "Rejected"),
    FILTERED(3, "Filtered"),
    COMPLETED(4, "Completed");

    private final int code;
    private final String label;

    Status(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static Status fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (Status status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status code: " + code);
    }
}
