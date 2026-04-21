package com.feketsz.assignment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "surveys")
public class Survey {

    @Id
    @Column(name = "survey_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "expected_completes", nullable = false)
    private Integer expectedCompletes;

    @Column(name = "completion_points", nullable = false)
    private Integer completionPoints;

    @Column(name = "filtered_points", nullable = false)
    private Integer filteredPoints;


}