package com.amazingapps.restaurantfinder.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "users")
public class User extends AbstractDocument {

    @Indexed(unique = true)
    @Field("email")
    private String email;

    @Field("password_hash")
    private String passwordHash;

    @Field("restaurants")
    private List<String> restaurantIds;
}