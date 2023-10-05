package com.application.management.school.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "MENU_MODEL")
@Data
public class MenuModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID")
    private Long menuId;

    @Column(nullable = false,unique = true,name = "MENU_NAME")
    private String menuName;

    @Column(nullable = false,unique = true,name = "MENU_LINK")
    private String menuLink;

    @Column(name = "MENU_DESCRIPTION")
    private String menuDescription;

    @ManyToMany(mappedBy = "menuModelList")
    private List<RoleModel> roleModelList = new ArrayList<>();

}
