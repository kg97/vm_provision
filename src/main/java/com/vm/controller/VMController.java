package com.vm.controller;

import com.vm.entity.VMProvision;
import com.vm.model.ApiResponse;
import com.vm.model.VMProvisionRequest;
import com.vm.service.VMService;
import com.vm.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Rest controller for VM provisioning related endpoints
 */
@RestController
public class VMController {

    @Autowired
    private VMService vmService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * API for requesting VM provisioning
     */
    @PostMapping("/request-vm")
    public ApiResponse provisionVM(@NotNull(message = "MISSING_TOKEN") @RequestHeader("Authorization") String token, @Valid @RequestBody VMProvisionRequest vmProvisionRequest) {
        String emailAddress = jwtTokenUtil.getUsernameFromToken(token);
        if(emailAddress!=null) {
            vmService.provisionVM(emailAddress, vmProvisionRequest);
            return new ApiResponse("SUCCESS", HttpStatus.CREATED);
        }
        return new ApiResponse("FAILURE",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * API for displaying list of VMs requested by particular user
     */
    @GetMapping("/getAllVMsByUser")
    public List<VMProvision> listAllVMsByUser(@NotNull(message = "MISSING_TOKEN") @RequestHeader("Authorization") String token) {
        String emailAddress = jwtTokenUtil.getUsernameFromToken(token);
        return vmService.listAllVMsByUser(emailAddress);
    }

    /**
     * API to list the top 'n' VMs by memory for logged in user
     */
    @GetMapping("/getTopMemoryVMsForUser")
    public List<VMProvision> listTopMemoryVMsForUser(@NotNull(message = "MISSING_TOKEN") @RequestHeader("Authorization") String token,@NotNull(message = "MISSING_LIMIT") @RequestParam("top") int number) {
        String emailAddress = jwtTokenUtil.getUsernameFromToken(token);
        return vmService.listTopMemoryVMsForUser(emailAddress, number);
    }

    /**
     * API to list the top 'n' VMs by memory across all users in system
     */
    @GetMapping("/getTopMemoryVMsForAllUsers")
    public List<VMProvision> listTopMemoryVMsForAllUsers(@NotNull(message = "MISSING_TOKEN") @RequestHeader("Authorization") String token, @NotNull(message = "MISSING_LIMIT") @RequestParam("top") int number) throws Exception {
        String emailAddress = jwtTokenUtil.getUsernameFromToken(token);
        return vmService.listTopMemoryVMsForAllUsers(emailAddress, number);
    }


}
