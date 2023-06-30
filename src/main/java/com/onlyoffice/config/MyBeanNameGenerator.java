package com.onlyoffice.config;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.beans.Introspector;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 自定义Bean名称使用@Service、@Controller注解时默认是按照类命取值
 * 去掉Bean名称的impl，需要搭配@CompentScan
 */
public class MyBeanNameGenerator implements BeanNameGenerator {
    public MyBeanNameGenerator() {
    }

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if (definition instanceof AnnotatedBeanDefinition) {
            String beanName = this.determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            if (StringUtils.hasText(beanName)) {
                return beanName;
            }
        }

        return buildDefaultBeanName(definition);
    }

    @Nullable
    protected String determineBeanNameFromAnnotation(AnnotatedBeanDefinition annotatedDef) {
        AnnotationMetadata amd = annotatedDef.getMetadata();
        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        Iterator var5 = types.iterator();

        while (var5.hasNext()) {
            String type = (String) var5.next();
            AnnotationAttributes attributes = AnnotationAttributes.fromMap(amd.getAnnotationAttributes(type, false));
            if (attributes != null && this.isStereotypeWithNameValue(type, amd.getMetaAnnotationTypes(type), attributes)) {
                Object value = attributes.get("value");
                if (value instanceof String) {
                    String strVal = (String) value;
                    if (StringUtils.hasLength(strVal)) {
                        if (beanName != null && !strVal.equals(beanName)) {
                            throw new IllegalStateException("Stereotype annotations suggest inconsistent component names: '" + beanName + "' versus '" + strVal + "'");
                        }

                        beanName = strVal;
                    }
                }
            }
        }
        return beanName;
    }


    protected boolean isStereotypeWithNameValue(String annotationType, Set<String> metaAnnotationTypes, @Nullable Map<String, Object> attributes) {
        boolean isStereotype = annotationType.equals("org.springframework.stereotype.Component") || metaAnnotationTypes.contains("org.springframework.stereotype.Component") || annotationType.equals("javax.annotation.ManagedBean") || annotationType.equals("javax.inject.Named");
        return isStereotype && attributes != null && attributes.containsKey("value");
    }

    /**
     * 编写Bean名称的规则，默认情况下在没有使用注解的属性的则直接取Bean的类型名称，且去掉后尾的impl（省去我们自己去@Service添加名称的步骤）
     *
     * @param definition
     * @return
     */
    protected String buildDefaultBeanName(BeanDefinition definition) {
        String beanClassName = definition.getBeanClassName();
        Assert.state(beanClassName != null, "No bean class name set");
        String shortClassName = ClassUtils.getShortName(beanClassName);

        // 如果存在 xxxImpl的情况下，则直接去掉impl的Bean名称
        if (shortClassName.endsWith("Impl")) {
            shortClassName = shortClassName.substring(0, shortClassName.length() - 4);
        }
        return Introspector.decapitalize(shortClassName);
    }
}
