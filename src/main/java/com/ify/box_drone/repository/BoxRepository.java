package com.ify.box_drone.repository;

import com.ify.box_drone.models.entities.Box;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxRepository extends JpaRepository<Box, String> {
    Box findByTxref(String txref);
}
