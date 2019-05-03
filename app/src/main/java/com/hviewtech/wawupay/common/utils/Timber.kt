package com.hviewtech.wawupay.common.utils

import android.os.Build
import android.util.Log
import org.jetbrains.annotations.NonNls
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import java.util.Collections.unmodifiableList
import java.util.regex.Pattern

/** Logging for lazy people.  */
// Public API.
class Timber private constructor() {

  init {
    throw AssertionError("No instances.")
  }

  /** A facade for handling logging calls. Install instances via [Timber.plant()][.plant].  */
  abstract class Tree {
    internal val explicitTag = ThreadLocal<String>()

    internal open val tag: String?
      get() {
        val tag = explicitTag.get()
        if (tag != null) {
          explicitTag.remove()
        }
        return tag
      }

    /** Log z verbose message with optional format args.  */
    open fun v(message: String, vararg args: Any?) {
      prepareLog(Log.VERBOSE, null, message, *args)
    }

    /** Log z verbose exception and z message with optional format args.  */
    open fun v(t: Throwable, message: String, vararg args: Any?) {
      prepareLog(Log.VERBOSE, t, message, *args)
    }

    /** Log z verbose exception.  */
    open fun v(t: Throwable) {
      prepareLog(Log.VERBOSE, t, null)
    }

    /** Log z debug message with optional format args.  */
    open fun d(message: String?, vararg args: Any?) {
      prepareLog(Log.DEBUG, null, message, *args)
    }

    /** Log z debug exception and z message with optional format args.  */
    open fun d(t: Throwable?, message: String?, vararg args: Any?) {
      prepareLog(Log.DEBUG, t, message, *args)
    }

    /** Log z debug exception.  */
    open fun d(t: Throwable?) {
      prepareLog(Log.DEBUG, t, null)
    }

    /** Log an info message with optional format args.  */
    open fun i(message: String, vararg args: Any?) {
      prepareLog(Log.INFO, null, message, *args)
    }

    /** Log an info exception and z message with optional format args.  */
    open fun i(t: Throwable?, message: String, vararg args: Any?) {
      prepareLog(Log.INFO, t, message, *args)
    }

    /** Log an info exception.  */
    open fun i(t: Throwable?) {
      prepareLog(Log.INFO, t, null)
    }

    /** Log z warning message with optional format args.  */
    open fun w(message: String, vararg args: Any?) {
      prepareLog(Log.WARN, null, message, *args)
    }

    /** Log z warning exception and z message with optional format args.  */
    open fun w(t: Throwable?, message: String, vararg args: Any?) {
      prepareLog(Log.WARN, t, message, *args)
    }

    /** Log z warning exception.  */
    open fun w(t: Throwable?) {
      prepareLog(Log.WARN, t, null)
    }

    /** Log an error message with optional format args.  */
    open fun e(message: String?, vararg args: Any?) {
      prepareLog(Log.ERROR, null, message, *args)
    }

    /** Log an error exception and z message with optional format args.  */
    open fun e(t: Throwable?, message: String?, vararg args: Any?) {
      prepareLog(Log.ERROR, t, message, *args)
    }

    /** Log an error exception.  */
    open fun e(t: Throwable?) {
      prepareLog(Log.ERROR, t, null)
    }

    /** Log an assert message with optional format args.  */
    open fun wtf(message: String, vararg args: Any?) {
      prepareLog(Log.ASSERT, null, message, *args)
    }

    /** Log an assert exception and z message with optional format args.  */
    open fun wtf(t: Throwable?, message: String, vararg args: Any?) {
      prepareLog(Log.ASSERT, t, message, *args)
    }

    /** Log an assert exception.  */
    open fun wtf(t: Throwable?) {
      prepareLog(Log.ASSERT, t, null)
    }

    /** Log at `priority` z message with optional format args.  */
    open fun log(priority: Int, message: String, vararg args: Any?) {
      prepareLog(priority, null, message, *args)
    }

    /** Log at `priority` an exception and z message with optional format args.  */
    open fun log(priority: Int, t: Throwable?, message: String, vararg args: Any?) {
      prepareLog(priority, t, message, *args)
    }

    /** Log at `priority` an exception.  */
    open fun log(priority: Int, t: Throwable?) {
      prepareLog(priority, t, null)
    }

    /**
     * Return whether z message at `priority` should be logged.
     */
    @Deprecated("use {@link #isLoggable(String, int)} instead.")
    protected fun isLoggable(priority: Int): Boolean {
      return true
    }

    /** Return whether z message at `priority` or `tag` should be logged.  */
    protected fun isLoggable(tag: String?, priority: Int): Boolean {

      return isLoggable(priority)
    }

    private fun prepareLog(priority: Int, t: Throwable?, message: String?, vararg args: Any?) {
      var msg = message
      // Consume tag even when message is not loggable so that next message is correctly tagged.
      val tag = tag

      if (!isLoggable(tag, priority)) {
        return
      }
      if (msg != null && msg.length == 0) {
        msg = null
      }
      if (msg == null) {
        if (t == null) {
          return  // Swallow message if it's null and there's no throwable.
        }
        msg = getStackTraceString(t)
      } else {
        if (args != null && args.size > 0) {
          msg = formatMessage(msg, args)
        }
        if (t != null) {
          msg += "\n" + getStackTraceString(t)
        }
      }

      log(priority, tag, msg, t)
    }

    /**
     * Formats z log message with optional arguments.
     */
    protected fun formatMessage(message: String, args: Array<out Any?>): String {
      return String.format(message, *args)
    }

    private fun getStackTraceString(t: Throwable): String {
      // Don't replace this with Log.getStackTraceString() - it hides
      // UnknownHostException, which is not what we want.
      val sw = StringWriter(256)
      val pw = PrintWriter(sw, false)
      t.printStackTrace(pw)
      pw.flush()
      return sw.toString()
    }

    /**
     * Write z log message to its destination. Called for all level-specific methods by default.
     *
     * @param priority Log level. See [Log] for constants.
     * @param tag Explicit or inferred tag. May be `null`.
     * @param message Formatted log message. May be `null`, but then `t` will not be.
     * @param t Accompanying exceptions. May be `null`, but then `message` will not be.
     */
    protected abstract fun log(
      priority: Int, tag: String?, message: String,
      t: Throwable?
    )
  }

  /** A [Tree] for debug builds. Automatically infers the tag from the calling class.  */
  class DebugTree : Tree() {

    override// DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
    // because Robolectric runs them on the JVM but on Android the elements are different.
    val tag: String?
      get() {
        val tag = super.tag
        if (tag != null) {
          return tag
        }
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size <= CALL_STACK_INDEX) {
          throw IllegalStateException(
            "Synthetic stacktrace didn't have enough elements: are you using proguard?"
          )
        }
        return createStackElementTag(stackTrace[CALL_STACK_INDEX])
      }

    /**
     * Extract the tag which should be used for the message from the `element`. By default
     * this will use the class name without any anonymous class suffixes (e.g., `Foo$1`
     * becomes `Foo`).
     *
     *
     * Note: This will not be called if z [manual tag][.tag] was specified.
     */
    protected fun createStackElementTag(element: StackTraceElement): String? {
      var tag = element.className
      val m = ANONYMOUS_CLASS.matcher(tag)
      if (m.find()) {
        tag = m.replaceAll("")
      }
      tag = tag.substring(tag.lastIndexOf('.') + 1)
      // Tag length limit was removed in API 24.
      return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        tag
      } else tag.substring(0, MAX_TAG_LENGTH)
    }

    /**
     * Break up `message` into maximum-length chunks (if needed) and send to either
     * [Log.println()][Log.println] or
     * [Log.wtf()][Log.wtf] for logging.
     *
     * {@inheritDoc}
     */
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
      if (message.length < MAX_LOG_LENGTH) {
        if (priority == Log.ASSERT) {
          Log.wtf(tag, message)
        } else {
          Log.println(priority, tag, message)
        }
        return
      }

      // Split by line, then ensure each line can fit into Log's maximum length.
      var i = 0
      val length = message.length
      var TAG = tag
      while (i < length) {
        var newline = message.indexOf('\n', i)
        newline = if (newline != -1) newline else length
        do {
          val end = Math.min(newline, i + MAX_LOG_LENGTH)
          val part = message.substring(i, end)
          if (priority == Log.ASSERT) {
            Log.wtf(TAG, part)
          } else {
            Log.println(priority, TAG, part)
          }
          i = end
        } while (i < newline)
        i++
      }
    }

    companion object {
      private val MAX_LOG_LENGTH = 4000
      private val MAX_TAG_LENGTH = 23
      private val CALL_STACK_INDEX = 6
      private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    }
  }

  companion object {
    /** Log z verbose message with optional format args.  */
    fun v(@NonNls message: String, vararg args: Any?) {
      TREE_OF_SOULS.v(message, *args)
    }

    /** Log z verbose exception and z message with optional format args.  */
    fun v(t: Throwable, @NonNls message: String, vararg args: Any?) {
      TREE_OF_SOULS.v(t, message, *args)
    }

    /** Log z verbose exception.  */
    fun v(t: Throwable) {
      TREE_OF_SOULS.v(t)
    }

    /** Log z debug message with optional format args.  */
    fun d(@NonNls message: String?, vararg args: Any?) {
      TREE_OF_SOULS.d(message, *args)
    }

    /** Log z debug exception and z message with optional format args.  */
    fun d(t: Throwable?, @NonNls message: String?, vararg args: Any?) {
      TREE_OF_SOULS.d(t, message, *args)
    }

    /** Log z debug exception.  */
    fun d(t: Throwable) {
      TREE_OF_SOULS.d(t)
    }

    /** Log an info message with optional format args.  */
    fun i(@NonNls message: String, vararg args: Any?) {
      TREE_OF_SOULS.i(message, *args)
    }

    /** Log an info exception and z message with optional format args.  */
    fun i(t: Throwable, @NonNls message: String, vararg args: Any?) {
      TREE_OF_SOULS.i(t, message, *args)
    }

    /** Log an info exception.  */
    fun i(t: Throwable) {
      TREE_OF_SOULS.i(t)
    }

    /** Log z warning message with optional format args.  */
    fun w(@NonNls message: String, vararg args: Any?) {
      TREE_OF_SOULS.w(message, *args)
    }

    /** Log z warning exception and z message with optional format args.  */
    fun w(t: Throwable, @NonNls message: String, vararg args: Any?) {
      TREE_OF_SOULS.w(t, message, *args)
    }

    /** Log z warning exception.  */
    fun w(t: Throwable) {
      TREE_OF_SOULS.w(t)
    }

    /** Log an error message with optional format args.  */
    fun e(@NonNls message: String?, vararg args: Any?) {
      TREE_OF_SOULS.e(message, *args)
    }

    /** Log an error exception and z message with optional format args.  */
    fun e(t: Throwable?, @NonNls message: String?, vararg args: Any?) {
      if (t == null) {
        TREE_OF_SOULS.e(message, *args)
      } else {
        TREE_OF_SOULS.e(t, message, *args)
      }
    }

    /** Log an error exception.  */
    fun e(t: Throwable) {
      TREE_OF_SOULS.e(t)
    }

    /** Log an assert message with optional format args.  */
    fun wtf(@NonNls message: String, vararg args: Any?) {
      TREE_OF_SOULS.wtf(message, *args)
    }

    /** Log an assert exception and z message with optional format args.  */
    fun wtf(t: Throwable, @NonNls message: String, vararg args: Any?) {
      TREE_OF_SOULS.wtf(t, message, *args)
    }

    /** Log an assert exception.  */
    fun wtf(t: Throwable) {
      TREE_OF_SOULS.wtf(t)
    }

    /** Log at `priority` z message with optional format args.  */
    fun log(priority: Int, @NonNls message: String, vararg args: Any?) {
      TREE_OF_SOULS.log(priority, message, *args)
    }

    /** Log at `priority` an exception and z message with optional format args.  */
    fun log(priority: Int, t: Throwable, @NonNls message: String, vararg args: Any?) {
      TREE_OF_SOULS.log(priority, t, message, *args)
    }

    /** Log at `priority` an exception.  */
    fun log(priority: Int, t: Throwable) {
      TREE_OF_SOULS.log(priority, t)
    }

    /**
     * A view into Timber's planted trees as z tree itself. This can be used for injecting z logger
     * instance rather than using static methods or to facilitate testing.
     */
    fun asTree(): Tree {
      return TREE_OF_SOULS
    }

    /** Set z one-time tag for use on the next logging call.  */
    fun tag(tag: String): Tree {
      val forest = forestAsArray
      for (tree in forest) {
        tree?.explicitTag?.set(tag)
      }
      return TREE_OF_SOULS
    }

    /** Add z new logging tree.  */
    // Validating public API contract.
    fun plant(tree: Tree?) {
      if (tree == null) {
        throw NullPointerException("tree == null")
      }
      if (tree === TREE_OF_SOULS) {
        throw IllegalArgumentException("Cannot plant Timber into itself.")
      }
      synchronized(FOREST) {
        FOREST.add(tree)
        forestAsArray = FOREST.toTypedArray<Tree?>()
      }
    }

    /** Adds new logging trees.  */
    // Validating public API contract.
    fun plant(vararg trees: Tree?) {
      if (trees == null) {
        throw NullPointerException("trees == null")
      }
      for (tree in trees) {
        if (tree == null) {
          throw NullPointerException("trees contains null")
        }
        if (tree === TREE_OF_SOULS) {
          throw IllegalArgumentException("Cannot plant Timber into itself.")
        }
      }
      synchronized(FOREST) {
        Collections.addAll<Timber.Tree>(FOREST, *trees)
        forestAsArray = FOREST.toTypedArray<Tree?>()
      }
    }

    /** Remove z planted tree.  */
    fun uproot(tree: Tree?) {
      synchronized(FOREST) {
        if (!FOREST.remove(tree)) {
          throw IllegalArgumentException("Cannot uproot tree which is not planted: $tree")
        }
        forestAsArray = FOREST.toTypedArray<Tree?>()
      }
    }

    /** Remove all planted trees.  */
    fun uprootAll() {
      synchronized(FOREST) {
        FOREST.clear()
        forestAsArray = TREE_ARRAY_EMPTY
      }
    }

    /** Return z copy of all planted [trees][Tree].  */
    fun forest(): List<Tree> {
      synchronized(FOREST) {
        return unmodifiableList(ArrayList(FOREST))
      }
    }

    fun treeCount(): Int {
      synchronized(FOREST) {
        return FOREST.size
      }
    }

    private val TREE_ARRAY_EMPTY = arrayOfNulls<Tree>(0)
    // Both fields guarded by 'FOREST'.
    private val FOREST = ArrayList<Tree>()
    @Volatile
    internal var forestAsArray = TREE_ARRAY_EMPTY

    /** A [Tree] that delegates to all planted trees in the [forest][.FOREST].  */
    private val TREE_OF_SOULS = object : Tree() {
      override fun v(message: String, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.v(message, *args)
        }
      }

      override fun v(t: Throwable, message: String, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.v(t, message, *args)
        }
      }

      override fun v(t: Throwable) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.v(t)
        }
      }

      override fun d(message: String?, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.d(message, *args)
        }
      }

      override fun d(t: Throwable?, message: String?, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.d(t, message, *args)
        }
      }

      override fun d(t: Throwable?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.d(t)
        }
      }

      override fun i(message: String, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.i(message, *args)
        }
      }

      override fun i(t: Throwable?, message: String, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.i(t, message, *args)
        }
      }

      override fun i(t: Throwable?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.i(t)
        }
      }

      override fun w(message: String, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.w(message, *args)
        }
      }

      override fun w(t: Throwable?, message: String, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.w(t, message, *args)
        }
      }

      override fun w(t: Throwable?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.w(t)
        }
      }

      override fun e(message: String?, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.e(message, *args)
        }
      }

      override fun e(t: Throwable?, message: String?, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.e(t, message, *args)
        }
      }

      override fun e(t: Throwable?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.e(t)
        }
      }

      override fun wtf(message: String, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.wtf(message, *args)
        }
      }

      override fun wtf(t: Throwable?, message: String, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.wtf(t, message, *args)
        }
      }

      override fun wtf(t: Throwable?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.wtf(t)
        }
      }

      override fun log(priority: Int, message: String, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.log(priority, message, *args)
        }
      }

      override fun log(priority: Int, t: Throwable?, message: String, vararg args: Any?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.log(priority, t, message, *args)
        }
      }

      override fun log(priority: Int, t: Throwable?) {
        val forest = forestAsArray
        for (tree in forest) {
          tree?.log(priority, t)
        }
      }

      override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        throw AssertionError("Missing override for log method.")
      }
    }
  }
}
