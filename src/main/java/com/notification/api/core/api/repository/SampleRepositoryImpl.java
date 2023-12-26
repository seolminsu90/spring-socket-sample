package com.notification.api.core.api.repository;

import com.notification.api.core.api.entity.QSample;
import com.notification.api.core.api.entity.Sample;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SampleRepositoryImpl implements SampleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Sample> findAllUser() {
        QSample sample = QSample.sample;
        return queryFactory.selectFrom(sample)
                .fetch();
    }
}
