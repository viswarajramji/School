package com.application.management.school.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class MenuObject {
    private Long menuId;
    private String menuName;
    private String menuLink;
    private String menuDescription;
}
