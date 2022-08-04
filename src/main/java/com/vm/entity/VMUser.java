package com.vm.entity;

import com.vm.enums.RoleType;
import com.vm.enums.Status;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * VMUser entity
 */
@Data
@Entity
@Table(name = "VMUser",uniqueConstraints={@UniqueConstraint(columnNames={"emailAddress","mobileNo","role"})})
public class VMUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    private String emailAddress;

    @ColumnDefault("NULL")
    private String mobileNo;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role=RoleType.NON_MASTER;

    @OneToMany(mappedBy="user",fetch = FetchType.EAGER)
    private Set<VMProvision> vmProvision;

    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
}
