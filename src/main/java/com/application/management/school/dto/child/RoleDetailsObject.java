package com.application.management.school.dto.child;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class RoleDetailsObject {
    private Long roleId;
    private String roleName;
    private String roleDescription;
    private List<MenuDetailsObject> menuDetailsObjectList;
}
