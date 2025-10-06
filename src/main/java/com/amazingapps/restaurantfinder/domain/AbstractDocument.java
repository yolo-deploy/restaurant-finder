package com.amazingapps.restaurantfinder.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class AbstractDocument {

    @Id
    private String id;

    @LastModifiedDate
    @Field("modify_date")
    private LocalDateTime modifyDate;

    @CreatedDate
    @Field("creation_date")
    private LocalDateTime creationDate;
}