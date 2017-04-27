package com.teamtreehouse.instateam.converter;

import com.teamtreehouse.instateam.dao.CollaboratorDao;
import com.teamtreehouse.instateam.model.Collaborator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringCollaboratorConverter implements Converter<String, Collaborator> {

    @Autowired
    private CollaboratorDao collaboratorDao;

    @Override
    public Collaborator convert(String id) {
        return collaboratorDao.findById(Long.valueOf(id));
    }

/*    @Bean
    public ConversionService getConversionService(){
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(new StringCollaboratorConverter());
        bean.setConverters(converters);
        return bean.getObject();
    }*/
}
