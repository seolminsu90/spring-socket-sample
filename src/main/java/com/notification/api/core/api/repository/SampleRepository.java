package com.notification.api.core.api.repository;

import com.notification.api.core.api.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Integer>, SampleRepositoryCustom{

}
