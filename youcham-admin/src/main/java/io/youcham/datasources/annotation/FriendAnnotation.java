package io.youcham.datasources.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME )
public @interface FriendAnnotation {
    String value();
}
