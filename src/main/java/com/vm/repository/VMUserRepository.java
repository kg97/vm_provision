package com.vm.repository;

import com.vm.entity.VMUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for VMUser table
 */
@Repository
public interface VMUserRepository extends CrudRepository<VMUser,Long> {

    public VMUser findByEmailAddress(String emailAddress);

}
