package com.hviewtech.wawupay.common.annotations;

import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@Target(allowedTargets = AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
public @interface ClickView {
    int[] value();
}
