package com.application.management.school.dto.child;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuDetailsObject {
    private Long menuId;
    private String menuName;
    private String menuLink;
    private String menuDescription;
}
