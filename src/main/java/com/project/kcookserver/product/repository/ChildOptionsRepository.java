package com.project.kcookserver.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.entity.ChildOptions;
import com.project.kcookserver.product.entity.Options;

public interface ChildOptionsRepository extends JpaRepository<ChildOptions, Long> {

	List<ChildOptions> findAllByOptionsAndStatus(Options options, Status status);
}
