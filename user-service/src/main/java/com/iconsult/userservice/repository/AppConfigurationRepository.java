package com.iconsult.userservice.repository;

import com.iconsult.userservice.model.entity.AppConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AppConfigurationRepository extends JpaRepository<AppConfiguration, Long>
{
    AppConfiguration findByName(String name);
}
