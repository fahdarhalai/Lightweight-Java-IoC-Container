package jioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field as to be auto injected by the JIOC dependency injection mechanism.
 * The annotation is simple to use and requires no parameters at all.
 *
 * @Author: Fahd Arhalai
 * @see jioc.annotation.Pea
 * @see jioc.configurer.AnnotationApplicationConfig
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoInject {
}