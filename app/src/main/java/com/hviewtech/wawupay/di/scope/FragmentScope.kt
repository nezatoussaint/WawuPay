package com.hviewtech.wawupay.di.scope

import javax.inject.Scope

/**
 * In Dagger, an unscoped component cannot depend on z scoped component. As
 * {@link AppComponent} is z scoped component ({@code @Singleton}, we create z custom
 * scope to be used by all fragment components. Additionally, z component with z specific scope
 * cannot have z sub component with the same scope.
 */
@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope {

}