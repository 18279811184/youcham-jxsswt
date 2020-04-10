package io.youcham.common.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ActivitiConfiguration implements ProcessEngineConfigurationConfigurer{
    
    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        // 流程图字体
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        
    }
    
}

