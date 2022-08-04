package com.vm.service;

import com.vm.entity.VMUser;
import com.vm.entity.VMProvision;
import com.vm.enums.RoleType;
import com.vm.enums.Status;
import com.vm.repository.VMUserRepository;
import com.vm.repository.VMRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    VMUserRepository vmUserRepository;

    @Autowired
    VMRepository vmRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public void signupUser(VMUser vmUser) {
        vmUser.setPassword(bcryptEncoder.encode(vmUser.getPassword()));
        vmUserRepository.save(vmUser);
    }

    public void deleteUser(String emailAddress, String requestedEmail) throws Exception {
        VMUser requestedVMUser = null;
        VMUser loggedVMUser = vmUserRepository.findByEmailAddress(emailAddress);
        RoleType role = loggedVMUser.getRole();
        if(requestedEmail!=null) {
            requestedVMUser = vmUserRepository.findByEmailAddress(requestedEmail);
            if(role.equals(RoleType.MASTER)) {
                deleteVMs(requestedVMUser);
            } else {
                if(Objects.equals(emailAddress, requestedEmail)){
                    deleteVMs(requestedVMUser);
                } else{
                    throw new Exception("VMUser not authorized to delete");
                }
            }
        } else{
            deleteVMs(loggedVMUser);
        }
    }

    private void deleteVMs(VMUser vmUser){
        List<VMProvision> vmProvisionList = vmRepository.findAllByUserUserId(vmUser.getUserId());
        for(VMProvision vmProvision: vmProvisionList){
            vmProvision.setStatus(Status.DELETED);
            vmRepository.save(vmProvision);
        }
        vmUser.setStatus(Status.DELETED);
        vmUserRepository.save(vmUser);
    }

}
