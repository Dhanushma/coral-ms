package com.coral.repository;

import com.coral.entity.StatementRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatementRequestRepository extends JpaRepository<StatementRequest, Long> {
}
