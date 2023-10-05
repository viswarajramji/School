package com.application.management.school.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserObject {
    private Long userId;
    private String userName;
    private Long roleId;
}
