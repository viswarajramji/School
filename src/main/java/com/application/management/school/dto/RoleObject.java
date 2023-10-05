package com.application.management.school.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class RoleObject {
    private Long roleId;
    private String roleName;
    private String roleDescription;
}
