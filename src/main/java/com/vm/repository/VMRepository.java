package com.vm.repository;

import com.vm.entity.VMProvision;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for VMProvision table
 */
@Repository
public interface VMRepository extends CrudRepository<VMProvision,Long> {

    List<VMProvision> findAllByUserUserId(Long userId);

    @Query(name="SELECT * FROM vmProvision v where v.userId :userId ORDER BY v.ramValue desc LIMIT :limit", nativeQuery=true)
    List<VMProvision> getVMsByRamValueAndUser(@Param("limit") int limit, @Param("userId") Long userId);

    @Query(name="SELECT * FROM vmProvision v ORDER BY v.ramValue desc LIMIT :limit", nativeQuery=true)
    List<VMProvision> getVMsByRamValue(@Param("limit") int limit);
}
