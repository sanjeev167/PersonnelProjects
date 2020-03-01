/**
 * 
 */
package com.custom.validation.classLevelValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author Sanjeev
 *
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
//@Target( {ElementType.FIELD, ElementType.METHOD})
@Retention( RetentionPolicy.RUNTIME)
@Constraint( validatedBy = ModuleRequiredWithTreeNodeTypeValidator.class)
@Documented
public @interface ModuleRequiredWithTreeNodeType {
    String message() default "Parent node required";
    Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};     	
    String nodeType();
    String moduleId();   
}