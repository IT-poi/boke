package com.cuit.boke.service;

import com.cuit.boke.article.beans.entry.Article;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

@Service
public class HelloService {

    public static void main(String[] args) throws IntrospectionException {
        Article article = new Article();
        BeanInfo beanInfo = Introspector.getBeanInfo(Article.class, Object.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Method readMethod = propertyDescriptor.getReadMethod();
            System.out.println(readMethod.getName());
        }
    }
}
