package com.iconsult.userservice.service.Impl;

import com.iconsult.userservice.model.entity.AppConfiguration;
import com.iconsult.userservice.repository.AppConfigurationRepository;
import com.iconsult.userservice.service.AppConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppConfigurationImpl implements AppConfigurationService
{
    @Autowired
    private AppConfigurationRepository appConfigurationRepository;

    @Override
    public AppConfiguration findByName(String name)
    {
        return this.appConfigurationRepository.findByName(name);
    }
}
