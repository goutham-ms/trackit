package com.trackit.userservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserInfoDto {
    private String userId;
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String email;
}
