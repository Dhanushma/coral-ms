package com.coral.repository;

import com.coral.entity.StatementRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatementRepository extends JpaRepository<StatementRequest, Long> {
}
