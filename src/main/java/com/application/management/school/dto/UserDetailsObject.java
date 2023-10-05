package com.application.management.school.dto;

import com.application.management.school.dto.child.RoleDetailsObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsObject {
    private Long userId;
    private String userName;
    private RoleDetailsObject roleDetailsObject;
}
