package com.hviewtech.wawupay.common.utils

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.common.annotations.ClickView
import com.hviewtech.wawupay.common.annotations.ContentView
import com.hviewtech.wawupay.common.annotations.InjectView
import com.hviewtech.wawupay.contract.Contract
import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import javax.inject.Inject


object InjectUtils {

  private var clazz: Class<*>? = null

  fun inject(activity: Activity) {

    //获取activity的Class类
    clazz = activity.javaClass
    //    injectContent(activity)
    //injectView(activity)
    injectEvent(activity)
//    injectPresenter(activity, presenters)
  }

  fun inject(fragment: Fragment, view: View) {

    //获取activity的Class类
    clazz = fragment.javaClass
    //    injectContent(activity)
    //injectView(activity)
    injectEvent(fragment, view)
//    injectPresenter(fragment, presenters)
  }

  fun unInject() {
    clazz = null
  }

  /**
   * 对ContentView注解惊醒解析
   * @param activity
   * @hide
   */
  private fun injectContent(obj: Any) {

    //取的Activity中的ContentView注解
    val contentView = clazz!!.getAnnotation(ContentView::class.java)
    if (contentView != null) {

      //取出ContentView注解中的值
      val id = contentView.value
      if (id < 0) {
        return
      }
      try {
        //获取Activity中setContentView方法,执行setContentView方法为Activity设置ContentView
        //在这一步中我们也可以直接使用 activity.setContentView(id) 来设置ContentView
        if (obj is Activity) {
          clazz!!.getMethod("setContentView", Integer.TYPE).invoke(obj, id)
        } else if (obj is View) {

        }
      } catch (e: NoSuchMethodException) {
        e.printStackTrace()
      } catch (e: InvocationTargetException) {
        e.printStackTrace()
      } catch (e: IllegalAccessException) {
        e.printStackTrace()
      }

    }
  }

  /**
   * 对InjectView注解进行解析
   * @param activity
   * @hide
   */
  @Deprecated("Kotlin dont need findViewById")
  private fun injectView(obj: Any) {

    val fields = clazz!!.declaredFields
    for (field in fields) {
      val inject = field.getAnnotation(InjectView::class.java)
      if (inject != null) {
        val id = inject.value
        try {
          //这一步中同样也能够使用 Object view = activity.findViewById(id) 来获取View
          if (obj is View || obj is Activity) {
            val view = clazz!!.getMethod("findViewById", Integer.TYPE).invoke(obj, id)
            field.set(obj, view)
          }
        } catch (e: IllegalAccessException) {
          e.printStackTrace()
        } catch (e: NoSuchMethodException) {
          e.printStackTrace()
        } catch (e: InvocationTargetException) {
          e.printStackTrace()
        }

      }

    }
  }


  /**
   * 对OnClick注解进行解析
   * @param activity
   */
  private fun injectEvent(obj: Activity) {

    val methods = clazz!!.methods

    for (method in methods) {
      val click = method.getAnnotation(ClickView::class.java)
      if (click != null) {
        val ids = click.value

        val handler = MyInvocationHandler(obj, method)
        //通过Java中的动态代理来执行View.OnClickListener
        val listenerProxy = Proxy.newProxyInstance(
          View.OnClickListener::class.java.getClassLoader(),
          arrayOf<Class<*>>(View.OnClickListener::class.java), handler
        )
        for (id in ids) {
          if (id < 0) {
            continue
          }
          try {
            val view = clazz!!.getMethod("findViewById", Integer.TYPE).invoke(obj, id)
            val listenerMethod = view.javaClass
              .getMethod("setOnClickListener", View.OnClickListener::class.java)
            listenerMethod.invoke(view, listenerProxy)
          } catch (e: NoSuchMethodException) {
            e.printStackTrace()
          } catch (e: InvocationTargetException) {
            e.printStackTrace()
          } catch (e: IllegalAccessException) {
            e.printStackTrace()
          }

        }
      }
    }
  }

  /**
   * 对OnClick注解进行解析
   * @param activity
   */
  private fun injectEvent(obj: Fragment, rootView: View) {

    val methods = clazz!!.methods

    for (method in methods) {
      val click = method.getAnnotation(ClickView::class.java)
      if (click != null) {
        val ids = click.value
        for (id in ids) {
          if (id < 0) {
            continue
          }
          try {
            val view = rootView.findViewById<View>(id)
            view.setOnClickListener {
              method.invoke(obj, it)
            }
          } catch (e: NoSuchMethodException) {
            e.printStackTrace()
          } catch (e: InvocationTargetException) {
            e.printStackTrace()
          } catch (e: IllegalAccessException) {
            e.printStackTrace()
          }

        }
      }
    }
  }


  internal class MyInvocationHandler(target: Any, method: Method) : InvocationHandler {

    private var target: Any? = null
    private var method: Method? = null

    init {
      this.target = target
      this.method = method
    }

    @Throws(Throwable::class)
    override operator fun invoke(proxy: Any, method: Method, args: Array<Any>): Any {

      return this.method!!.invoke(target, args)
    }
  }


  fun injectPresenter(obj: Any?, presenters: MultiPresenter?) {

    if (presenters == null || obj == null) {
      return
    }
    clazz = obj.javaClass
    val fields = clazz!!.declaredFields
    for (field in fields) {

      val inject = field.getAnnotation(Inject::class.java)
      if (inject != null) {
        try {
          val value = field.get(obj)
          if (value is Contract.Presenter) {
            presenters.bind(value)
          }
        } catch (e: Exception) {
          e.printStackTrace()
        }
      }


    }
  }

}