package com.vm.service;

import com.vm.entity.VMUser;
import com.vm.entity.VMProvision;
import com.vm.enums.RoleType;
import com.vm.model.VMProvisionRequest;
import com.vm.repository.VMUserRepository;
import com.vm.repository.VMRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VMService {

    @Autowired
    VMRepository vmRepository;

    @Autowired
    VMUserRepository vmUserRepository;

    public void provisionVM(String emailAddress, VMProvisionRequest vmProvisionRequest) {
        VMUser vmUser = vmUserRepository.findByEmailAddress(emailAddress);
        VMProvision vmProvision=new VMProvision();
        vmProvision.setVmUser(vmUser);
        vmProvision.setOsName(vmProvisionRequest.getOsName());
        vmProvision.setCpuCores(vmProvisionRequest.getCpuCores());
        vmProvision.setRamValue(vmProvisionRequest.getRamValue());
        vmProvision.setHardDisk(vmProvisionRequest.getHardDisk());
        vmRepository.save(vmProvision);
    }

    public List<VMProvision> listAllVMsByUser(String emailAddress) {
        VMUser vmUser = vmUserRepository.findByEmailAddress(emailAddress);
        return vmRepository.findAllByUserUserId(vmUser.getUserId());
    }

    public List<VMProvision> listTopMemoryVMsForUser(String emailAddress, int number) {
        VMUser vmUser = vmUserRepository.findByEmailAddress(emailAddress);
        return vmRepository.getVMsByRamValueAndUser(number, vmUser.getUserId());
    }

    public List<VMProvision> listTopMemoryVMsForAllUsers(String emailAddress, int number) throws Exception {
        VMUser vmUser = vmUserRepository.findByEmailAddress(emailAddress);
        if(vmUser.getRole().equals(RoleType.MASTER))
            return vmRepository.getVMsByRamValue(number);
        throw new Exception("VMUser not authorized to view");
    }
}
