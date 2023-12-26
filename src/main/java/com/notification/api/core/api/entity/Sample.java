package com.notification.api.core.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Sample")
public class Sample {
    @Id
    private Long seq;

    private String name;
}
