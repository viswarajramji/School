package com.application.management.school.repository;

import com.application.management.school.Model.MenuModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuModel,Long> {
}
