package com.vm.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VMProvisionRequest {

    @NotBlank(message = "OS Name is mandatory")
    private String osName;

    private Integer ramValue=0;

    @NotBlank(message = "HardDisk Name is mandatory")
    private String hardDisk;

    private Integer cpuCores=0;

}
