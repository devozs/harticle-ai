package com.devozs.components.harticle.training.repository;

import com.devozs.components.harticle.training.entity.ComputeResource;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComputeResourceRepository extends CrudRepository<ComputeResource, UUID> {

    Optional<ComputeResource> findByName(String name);

    /** Resolve an authenticated agent from the bearer token it presents (hashed). */
    Optional<ComputeResource> findByAgentTokenHash(String agentTokenHash);

    List<ComputeResource> findByEnrolledTrue();
}
