package com.trackit.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserInfo {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String email;
}
