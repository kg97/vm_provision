package com.vm.entity;

import com.vm.enums.Status;
import lombok.Data;

import javax.persistence.*;

/**
 * VMProvision entity
 */
@Data
@Entity
@Table(name = "VMProvision")
public class VMProvision {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vmProvisionId;

    @ManyToOne
    @JoinColumn(name="userId", nullable=false)
    private VMUser vmUser;

    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    private String osName;

    private Integer ramValue=0;

    private String hardDisk;

    private Integer cpuCores=0;

}
