package jioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated class should be managed by the JIOC framework and is considered as a candidate for auto-detection when using annotation-based configuration.
 * Accepts an optional initMethod parameter specifying the logic to run right after a constructor call to the annotated class.
 *
 * @Author: Fahd Arhalai
 * @see jioc.annotation.AutoInject
 * @see jioc.annotation.AnnotatedPea
 * @see jioc.configurer.AnnotationApplicationConfig
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Pea {
    public String initMethod() default "";
}
