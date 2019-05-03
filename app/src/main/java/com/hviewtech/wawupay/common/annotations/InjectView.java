package com.hviewtech.wawupay.common.annotations;

import kotlin.Deprecated;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@Deprecated(message = "")
@Target(allowedTargets = AnnotationTarget.FIELD)
@Retention()
public @interface InjectView {
    int value();
}
