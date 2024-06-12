package com.iconsult.userservice.service;

import com.iconsult.userservice.model.entity.AppConfiguration;

public interface AppConfigurationService
{
    AppConfiguration findByName(String name);
}
