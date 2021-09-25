// The name of the generated FinalizeEscapeGC class can be changed using the @JvmName annotation
@file:JvmName("JavaName")
// 使相同JvmName的多个不同.kt文件生成单个Java Class(包含所有声明)
@file:JvmMultifileClass

// To use an experimental API in all functions and classes in a file
// @file:UseExperimental(ExperimentalDateTime::class)

package com.lgjy.deeper.common.kotlin

import android.os.*
import androidx.annotation.RequiresApi
import kotlinx.parcelize.*
import java.io.IOException
import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator
import kotlin.math.*
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Created by LGJY on 19/8/6.
 * Email：yujye@sina.com
 *
 * Learning Kotlin
 */

// Check if a number is out of range
private fun checkOutOfRange() {
    val list = listOf("a", "b", "c")
    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }
    if (list.size !in list.indices) {
        println("list size is out of valid list indices range, too")
    }
}

private fun forMap() {
    val map = mapOf<String, String>()
    for ((k, v) in map) {
        println("$k->$v")
    }
}

private fun collectionLambda() {
    val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
    fruits.filter { it.startsWith("a") }
        .sortedBy { it }
        .map { it.toUpperCase() }
        .onEach { println("onEach: $it") }
        .forEach { println("forEach: $it") }
}

private val minusArray = IntArray(3).apply { fill(-1) }

private fun swapTwoVariables() {
    var a = 1
    var b = 2
    a = b.also { b = a }
}

private const val MODIFIERS_SORT =
    "public/protected/private/internal" +
            "expect/actual" +
            "final/open/abstract/sealed/const" +
            "external" +    // external: marks a declaration as implemented not in Kotlin (accessible through JNI or in JavaScript)
            "override" +
            "lateinit" +
            "tailrec" +
            "vararg" +
            "suspend" +
            "inner" +
            "enum/annotation" +
            "companion" +
            "inline" +
            "infix" +
            "operator" +
            "data"

// Bad: use of mutable collection type for value which will not be mutated
private fun badSet(badSet: HashSet<String>) {}

// Good: immutable collection type used instead
private fun goodSet(goodSet: Set<String>) {}

// Bad: arrayListOf() returns ArrayList<T>, which is a mutable collection type
private val badList = arrayListOf("a")

// Good: listOf() returns List<T>
private val goodList = listOf("a")

// Type aliases
private typealias AnyStringHandler = (Any, String) -> Unit

private typealias StringToInt = Map<String, Int>

// To maintain indentation in multiline strings(instead of embedding \n ),
// use trimIndent when the resulting string does not require any internal indentation,
// or trimMargin when internal indentation is required
private fun mainTrim() {
    val trimIndentValue =
        """
        Foo
        Bar
        """

    println("-------------------------------")
    println(trimIndentValue)
    println("-------------------------------")
    println(trimIndentValue.trimIndent())
    println("-------------------------------")

    val trimMarginValue = """if(a>1){
        |  return a
        |}"""

    println(trimMarginValue)
    println("-------------------------------")
    println(trimMarginValue.trimMargin())
    println("-------------------------------")
}

// If you have an object with multiple overloaded constructors that don't call different superclass constructors
// and can't be reduced to a single constructor with default argument values, prefer to replace the overloaded constructors with factory private functions.

// boxing of numbers does not necessarily preserve identity
private fun mainRepresentation() {
    val a: Int = 10000
    println(a === a)    // Prints 'true'
    println(a == a)   // Prints 'true'
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a
    println(boxedA === anotherBoxedA)   // !!!Prints 'false'!!!
    println(boxedA == anotherBoxedA)   // Prints 'true'
}

// @kotlin.ExperimentalUnsignedTypes   // to propagate experimentality(another way see app.build)
private val unsignedInt: UInt = 12U

// @UseExperimental(ExperimentalUnsignedTypes::class)  // to opt-in without propagating experimentality(see app.build)
private val unsignedLong: ULong = 12UL

// 自定义可以进行for循环的遍历器
private class CustomArray(private val size: Int) {

    private var x = 1

    operator fun iterator(): Iterator<Int> = object : Iterator<Int> {

        override fun hasNext(): Boolean = x < size

        override fun next(): Int = x++
    }
}

private fun mainForCustomArray() {
    val customArray = CustomArray(12)
    for (i in customArray) {
        print(i)
    }
}

private fun forWithIndex() {
    val a = listOf(1, 2, 3)
    for ((index, value) in a.withIndex()) {
        println("the element at $index is $value")
    }
}

private fun mainReturnLabel() {
    run lgjy@{
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@lgjy 1   // means "return 1 at label @lgjy"
            println(it)
        }
    }
    println("done with nested loop")
}

// If the class has a primary constructor, each secondary constructor needs to delegate to the primary constructor(by this()),
// either directly or indirectly through another secondary constructor(s).
// Delegation to the primary constructor happens as the first statement of a secondary constructor,
// so the code in all initializer blocks is executed before the secondary constructor body.
// Even if the class has no primary constructor.
private class Constructors() {

    init {
        println("print first")
    }

    constructor(i: Int) : this() {
        println("print second")
    }
}

// Do not want your class to have a public constructor
private class DontCreateMe private constructor()

private open class Father {

    fun father() {}
    open fun forSon() {}
    open fun forChildren() {}
}

private open class Son : Father() {

    // A member marked override is itself open, i.e. it may be overridden in subclasses.
    override fun forChildren() {
        super.forChildren()
        println("Still can be overridden")
    }

    // If you want to prohibit re-overriding, use final
    final override fun forSon() {
        super.forSon()
        println("Can't be overridden any more")
    }
}

private class GrandSon : Son() {

    override fun forChildren() {
        super.forChildren()
        println()
    }
}

// You can use the override keyword as part of the property declaration in a primary constructor.
private interface Inter {

    val count: Int
}

private class Imple(override val count: Int) : Inter

// Note: During construction of a new instance of a derived class, the base class initialization is done as the first step.
// When designing a base class, you should avoid using open members in the constructors, property initializers, and init blocks.
private open class Base(val name: String) {

    init {
        println(2)
    }

    open val size: Int = name.length.also { println(3) }

    open fun base() {}
}

private class Derived(name: String, lastName: String) : Base(name.capitalize().also { println(1) }) {

    init {
        println(4)
    }

    override val size: Int = (super.size + lastName.length).also { println(5) }

    // Inside an inner class, accessing the superclass of the outer class is done with the super keyword qualified with
    // the outer class name: super@Outer
    inner class InnerDerived {

        fun inner() {
            println(super@Derived.size)  // Uses Base's implementation of size's getter
        }
    }
}

// if a class inherits many implementations of the same member from its immediate superclasses,
// it must override this member and provide its own implementation
private open class A {

    open fun o() = 1
}

private interface B {

    fun o(): Int = 2
}

private class C : A(), B {

    override fun o(): Int = super<A>.o() + super<B>.o()
}

// We can override a non-abstract open member with an abstract one
private open class Open {

    open fun open() {}
}

private abstract class DerivedOpen : Open() {

    abstract override fun open()
}

// On the JVM: The access to private properties with default getters and setters is optimized so no function call overhead is introduced in this case.
private var _table: Map<String, Int>? = null
public val table: Map<String, Int>
    get() {
        if (_table == null) {
            _table = HashMap<String, Int>()
        }
        return _table ?: throw AssertionError("Set to null by another thread")
    }

private lateinit var topLateInit: A

private class LATEINIT {

    lateinit var lateinit: A

    inner class LATEINITCHILD {

        lateinit var lateinitChild: LATEINITCHILD

        fun checkWhetherInitialized() {
            if (::topLateInit.isInitialized && this::lateinitChild.isInitialized && this@LATEINIT::lateinit.isInitialized) {
                println("Check 'isInitialized' at top level in the same file, same type and outer type")
            }
        }
    }
}

private interface MyInterFace {

    val abstractProperty: Int

    // Properties declared in interfaces can't have backing fields
    val propertyWithImple: String
        get() = "inlineOrNot"
    // set(value) {
    //     field = value
    // }

    fun foo() {
        println("$abstractProperty and $propertyWithImple")
    }
}

public var i: Int = 1       // visible everywhere
    private set             // visible inside the file
internal val ii: Int = 1    // visible everywhere in the same module
// protected val iii: Int = 1 // protected is not available for top-level declarations

open class Visibility {
    private val a = 1       // visible inside this class only (including all its members)

    // If you override a protected member and do not specify the visibility explicitly, the overriding member will also have protected visibility.
    // Note: FinalizeEscapeGC allows accessing protected members from other classes in the same package and Kotlin doesn't
    protected open val aa = 1    // same as private + visible in subclasses too
    internal val aaa = 1    // any client inside this module who sees the declaring class sees its internal members
    // Note that in Kotlin, outer class does not see private members of its inner classes.
}

private open class Outer {
    private val a = 1
    protected open val b = 2
    internal val c = 3
    val d = 4  // public by default

    protected class Nested {
        public val e: Int = 5
    }
}

private class Subclass : Outer() {
    // a is not visible
    // b, c and d are visible
    // Nested and e are visible

    //
    override val b = 5   // 'b' is protected
}

private class Unrelated(val o: Outer) {
    // o.a, o.b are not visible
    // o.c and o.d are visible (same module)
    // Outer.Nested is not visible, and Nested::e is not visible either
}

private class Extension {

    fun printE() {
        println("Class Method")
    }
}

private fun Extension.printE() {
    println("Extension function")
}

private fun mainExtension() {
    Extension().printE()    // print "Class Method" member always wins
}

// Extension properties
// Note that, since extensions do not actually insert members into classes, there's no efficient way for an extension property
// to have a backing field. This is why initializers are not allowed for extension properties.
// Their behavior can only be defined by explicitly providing getters/setters.
private val <T> List<T>.lastPosition: Int
    inline get() = size - 1     // You can now mark property accessors with the inline modifier if the properties don't have a backing field.

private data class User(var name: String, var age: Int)

private fun destructuringDeclarations() {
    val jane = User("Jane", 25)
    val (name, age) = jane
    println("$name,$age years of age")
}

// A sealed class is abstract by itself
// Subclasses of sealed class must be declared in the same file as the sealed class itself
// Sealed classes are not allowed to have non-private constructors (their constructors are private by default).
private sealed class Expr

private data class Const(val number: Double) : Expr()
private data class Sum(val e1: Expr, val e2: Expr) : Expr()
private object NotANumber : Expr()

// For java Generics, Producer-Extends, Consumer-Super: only read from Producers and write to Consumers
// For Kotlin, Producer-out, Consumer-in
private interface Source<out T> {

    fun nextT(): T
}

private fun producerOut(strs: Source<String>) {
    val objects: Source<Any> = strs
}

private interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}

private fun consumerIn(x: Comparable<Number>) {
    x.compareTo(1.0)    // 1.0 has type Double, which is a subtype of Number
    // Thus, we can assign x to a variable of type Comparable<Double>
    val y: Comparable<Double> = x
}

private fun superIn(array: Array<in Base>, value: Base) {
    val item = array[0] // item Type is Any
    array[1] = value    // Consumer-in
}

private fun extendOut(array: Array<out Base>, value: Base) {
    val item = array[0] // item Type is Base
    // array[1] = value     // Producer-out
}

/**
 * Star-projections syntax
 *
 * For Foo<out T : TUpper>, where T is a covariant type parameter with the upper bound TUpper, Foo<*> is equivalent to Foo<out TUpper>.
 * It means that when the T is unknown you can safely read values of TUpper from Foo<*>.
 *
 * For Foo<in T>,where T is a contravariant type parameter, Foo<*> is equivalent to Foo<in Nothing>.
 * It means there is nothing you can write to Foo<*> in a safe way when T is unknown.
 *
 * For Foo<T : TUpper>, where T is an invariant type parameter with the upper bound TUpper,
 * Foo<*> is equivalent to Foo<out TUpper> for reading values and to Foo<in Nothing> for writing values.
 *
 * For example:
 *
 * interface Function<in T, out U>
 *
 * Function<*, String> means Function<in Nothing, String>;
 * Function<Int, *> means Function<Int, out Any?>;
 * Function<*, *> means Function<in Nothing, out Any?>.
 */

// If the same type parameter needs more than one upper bound, we need a separate where-clause
private fun <T> copyWhenGreater(
    list: List<T>,
    threshold: T
): List<String> where T : CharSequence, T : kotlin.Comparable<T> {
    return list.filter { it > threshold }.map { it.toString() }
}

// Inner class can access members of outer class
private class Out {
    private val bar: Int = 1

    class Nest {
        fun foo() = 2
    }

    inner class Inner {
        fun fooo() = bar
    }
}

private fun readInnerMember() {
    val nest = Out.Nest().foo()
    val inner = Out().Inner().fooo()    // Inner classes carry a reference to an object of an outer class
}

// Enum constants can also declare their own anonymous classes with their corresponding methods, as well as overriding base methods.
private enum class ProtocolState {

    WAITING {
        override fun signal() = TALKING // Enum entries can contain nested types
    },
    TALKING {
        override fun signal() = WAITING
    };

    // separate the enum constant definitions from the member definitions with a semicolon.

    abstract fun signal(): ProtocolState
}

// An enum class may implement an interface (but not derive from a class)
// providing either a single interface members implementation for all of the entries, or separate ones for each entry within its anonymous class.
@RequiresApi(Build.VERSION_CODES.N)
private enum class IntArithmetics : BinaryOperator<Int>, IntBinaryOperator {
    PLUS {
        override fun apply(p0: Int, p1: Int): Int = p0 + p1
    },
    TIMES {
        override fun apply(p0: Int, p1: Int): Int = p0 * p1
    };

    override fun applyAsInt(p0: Int, p1: Int): Int = apply(p0, p1)
}

// In a generic way, using the enumValues<T>() and enumValueOf<T>() functions to access the constants in an enum class
private inline fun <reified T : Enum<T>> printAllValues() {
    print(enumValues<T>().joinToString { it.name })
}

private fun printEnumInfo() {
    printAllValues<IntArithmetics>()    // prints: PLUS,TIMES

    println(IntArithmetics.PLUS.name)   // prints: PLUS
    println(IntArithmetics.PLUS.ordinal) // prints: 0
}

private fun generateObject() {
    val xy = object {
        var x = 1
        var y = 2
    }
    println(xy.x + xy.y)
}

/**
 * Note that anonymous objects can be used as types only in local and private declarations.If you use an anonymous
 * object as a return type of a public function or the type of a public property, the actual type of that function or
 * property will be the declared supertype of the anonymous object, or Any if you didn't declare any supertype.
 * Members added in the anonymous object will not be accessible.
 */
private class AnonymousObjects {
    // Private function, so the return type is the anonymous object type
    private fun foo() = object {
        val x: String = "x"
    }

    // Public function, so the return type is Any
    fun publicFoo() = object {
        val x: String = "x"
    }

    private fun bar() {
        val x1 = foo().x        // Works
        //        val x2 = publicFoo().x  // ERROR: Unresolved reference 'x'
    }
}

/**
 * An inline class must have a single property initialized in the primary constructor.
 * At runtime, instances of the inline class will be represented using this single property.
 * Method and property(only get()=...) in inline class is called as a static method.
 * Inline classes cannot have init blocks.
 * Inline class properties cannot have backing fields.
 * It follows that inline classes can only have simple computable properties (no lateinit/delegated properties.
 * Inline classes are allowed to inherit from interfaces, but still called as a static method.
 * It is forbidden for inline classes to participate in a class hierarchy. This means that inline classes cannot extend other classes and must be final.
 *
 * In generated code, the Kotlin compiler keeps a wrapper for each inline class. Inline class instances
 * can be represented at runtime either as wrappers or as the underlying type.
 * This is similar to how Int can be represented either as a primitive int or as the wrapper Integer.
 *
 * As a rule of thumb, inline classes are boxed whenever they are used as another type.
 */
private inline class InlineString(val str: String)

// No actual instantiation of class 'InlineString' happens, At Runtime 'inlineStr' contains just '123'
private val inlineStr = InlineString("123")

private interface I

private inline class Foo(val i: Int) : I

private fun asInline(f: Foo) {}
private fun <T> asGeneric(x: T) {}
private fun asInterface(i: I) {}
private fun asNullable(i: Foo?) {}

private fun <T> id(x: T): T = x

private fun boxOrUnboxed() {
    val f = Foo(42)

    asInline(f)    // unboxed: used as Foo itself
    asGeneric(f)   // boxed: used as generic type T
    asInterface(f) // boxed: used as type I
    asNullable(f)  // boxed: used as Foo?, which is different from Foo

    // below, 'f' first is boxed (while being passed to 'id') and then unboxed (when returned from 'id')
    // In the end, 'c' contains unboxed representation (just '42'), as 'f'
    val c = id(f)
}

// Implementation by Delegation
private interface InterA {
    val msg: String
    fun printMsg()
    fun printSelf()
}

private class InterAImple(val x: Int) : InterA {

    override val msg: String
        get() = "interAMsg"

    override fun printMsg() {
        println("$msg is $x")
    }

    override fun printSelf() {
        println("InterAImple")
    }
}

// a will be stored internally in objects of DerivedA and the compiler will generate all the methods of InterA that forward to a
private class DerivedA(a: InterA) : InterA by a {

    override val msg: String
        get() = "DerivedAMsg"

    override fun printSelf() {
        println("DerivedA")
    }
}

private fun printDelegation() {
    val a = InterAImple(10)
    DerivedA(a).printMsg()    // print 'interAMsg is 10' not 'DerivedAMsg is 10'
    DerivedA(a).printSelf()   // print 'DerivedA' not 'InterAImple' because 'override' take precedence
}


// Delegated Properties
// -----------------------------------第一种方式
// getValue() and/or setValue() functions may be provided either as member functions of the delegate class or extension functions.
// Both of the functions need to be marked with the operator keyword.
//private class Delegate {
//    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
//        return "$thisRef,thank you for delegating '${property.name}' to me"
//    }
//
//    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
//        println("$value has been assigned to ${property.name} in $thisRef")
//    }
//}

// -----------------------------------第二种方式
// Extension functions is handy when you need to delegate property to an object which doesn't originally provide these functions.
//private operator fun Delegate.getValue(thisRef: Any?, property: KProperty<*>): String {
//    return "$thisRef,thank you for delegating '${property.name}' to me"
//}
//
//private operator fun Delegate.setValue(thisRef: Any?, property: KProperty<*>, value: String) {
//    println("$value has been assigned to ${property.name} in $thisRef")
//}

// -----------------------------------第三种方式
// The delegate class may implement one of the interfaces ReadOnlyProperty and ReadWriteProperty
private class Delegate : ReadWriteProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef,thank you for delegating '${property.name}' to me"
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to ${property.name} in $thisRef")
    }
}

/**
 * Translation Rules
 *
 * class C {
 *   var prop: Type by MyDelegate()
 * }
 *
 * this code is generated by the compiler instead:
 *
 * class C {
 *
 *   private val prop$delegate = MyDelegate()
 *   var prop: Type
 *     // the first argument 'this' refers to an instance of the outer class C and 'this::prop' is a reflection object of the KProperty type describing prop itself.
 *     get() = prop$delegate.getValue(this, this::prop)
 *     set(value: Type) = prop$delegate.setValue(this, this::prop, value)
 * }
 */
private class Delegation {
    var dele: String by Delegate()
}

private fun printDelegate() {
    val e = Delegation()
    println(e.dele) // print "Delegation@7a4f0f29,thank you for delegating 'dele' to me"
    e.dele = "New"  // print "New has been assigned to dele in Delegation@7a4f0f29"
}

// By default, the evaluation of lazy properties is synchronized, the value is computed only in one thread, and all threads will see the same value.
private val lazyDefault: String by lazy {
    println("computed!")
    Thread.sleep(2000L)
    "Hello!"
}

private fun printLazyDefault() {
    Thread(Runnable { println(lazyDefault) }).start()
    Thread(Runnable { println(lazyDefault) }).start()
    // print "computed! Hello! Hello!"
}

// If the synchronization of initialization delegate is not required, so that multiple threads can execute it simultaneously,
// pass LazyThreadSafetyMode.PUBLICATION as a parameter to the lazy() function.
private val lazyPublication: String by lazy(LazyThreadSafetyMode.PUBLICATION) {
    println("computed!")
    Thread.sleep(2000L)
    "Hello!"
}

private fun printLazyPublication() {
    Thread(Runnable { println(lazyPublication) }).start()
    Thread(Runnable { println(lazyPublication) }).start()
    // print "computed! computed! Hello! Hello!"
}

// If you're sure that the initialization will always happen on the same thread as the one where you use the property,
// you can use LazyThreadSafetyMode.NONE: it doesn't incur any thread-safety guarantees and the related overhead.
private val lazyNone: String by lazy(LazyThreadSafetyMode.NONE) {
    println("computed!")
    Thread.sleep(2000L)
    "Hello!"
}

private fun printLazyNone() {
    Thread(Runnable {
        println(lazyNone)
        println(lazyNone)
    }).start()
    // print "computed! Hello! Hello!"
}

private class Username {
    var name: String by Delegates.observable("<no name>")
    {   // called after the assignment has been performed
            property, oldValue, newValue ->
        println("$oldValue->$newValue")
    }

    var old: Int by Delegates.vetoable(1)
    {   // called before the assignment of a new property value has been performed.
            property, oldValue, newValue ->
        // If the callback returns `true` the value of the property is being set to the new value,
        // and if the callback returns `false` the new value is discarded and the property remains its old value.
        return@vetoable newValue > 0
    }
}

private fun printDelegates() {
    val username = Username()
    username.name = "first"     // print <no name>->first
    username.name = "second"    // print first->second

    username.old = 2
    println(username.old)       // print 2
    username.old = -1
    println(username.old)       // print 2
}

// Storing Properties in a Map
private class DataCenter(map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}

private fun printDataCenter() {
    val data = DataCenter(mapOf("name" to "John Doe", "age" to 25))
    println(data.name) // Prints "John Doe"
    println(data.age)  // Prints 25
}

// By defining the provideDelegate operator you can extend the logic of creating the object to which the property implementation is delegated.
// If the object used on the right hand side of by defines provideDelegate as a member or extension function,
// provideDelegate() will be called to create the property delegate instance.
// One of the possible use cases of provideDelegate is to check property consistency when the property is created, not only in its getter or setter.
// Note that the provideDelegate method affects only the creation of the auxiliary property and doesn't affect the code generated for getter or setter.
// For example, if you want to check the property name before binding, you can write something like this:
/**
 *class ResourceDelegate<T> : ReadOnlyProperty<MyUI, T> {
 *    override fun getValue(thisRef: MyUI, property: KProperty<*>): T { ... }
 *}
 *
 *class ResourceLoader<T>(id: ResourceID<T>) {
 *    operator fun provideDelegate(thisRef: MyUI, prop: KProperty<*>): ReadOnlyProperty<MyUI, T> {
 *        checkProperty(thisRef, prop.name)
 *        // create delegate
 *        return ResourceDelegate()
 *    }
 *
 *    private fun checkProperty(thisRef: MyUI, name: String) { ... }
 *}
 *
 *class MyUI {
 *    fun <T> bindResource(id: ResourceID<T>): ResourceLoader<T> { ... }
 *
 *    val image by bindResource(ResourceID.image_id)
 *    val text by bindResource(ResourceID.text_id)
 *}
 */

private open class M {
    open fun foo(a: Int = 0) {}
}

private class N : M() {
    // Overriding methods always use the same default parameter values as the base method.
    //     override fun inlineOrNot(a: Int = 1) {}    // An overriding function is not allowed to specify default values for its parameters.
    override fun foo(a: Int) {}
}

private fun vararg(start: String, vararg strings: String, end: String) {}

// On the JVM: the named argument syntax cannot be used when calling FinalizeEscapeGC functions because FinalizeEscapeGC bytecode does not always preserve names of function parameters.
private fun callVararg() {
    vararg("start1", strings = *arrayOf("a", "b", "c"), end = "end1")   // *: spread operator
    vararg("start2", "a", "b", "c", end = "end2")
}

// Infix function calls have lower precedence than the arithmetic operators, type casts, and the rangeTo operator.
// But have higher precedence than that of the boolean operators && and ||, is- and in-checks, and some other operators.
private class Infix {

    val i: Int = 0

    // extension functions
    infix fun Int.he(x: Int): Int {
        return this + x
    }

    // member functions
    infix fun cha(x: Int): Int {
        return i - x
    }

    val a = 1 he 2
    val b = this cha 2
}

// Kotlin supports local functions, local function can access local variables of outer functions (i.e. the closure)
private fun outFun(): Int {
    var a = 0
    fun inFun() {
        a++
    }
    for (i in 0..10) {
        inFun()
    }
    return a
}

// When a function is marked with the tailrec modifier,the compiler optimises out the recursion and without the risk of stack overflow.
// A tailrec function must call itself as the last operation it performs, and you cannot use it within try/catch/finally blocks.
tailrec fun findFixPoint(x: Double = 1.0): Double =
    if (abs(x - cos(x)) < 1E-10) x else findFixPoint(cos(x))

private fun typeFun(typeFun: Int.(Int) -> Int): Int = 1.typeFun(2)
private fun nullableFun(nullableFun: ((Int, Int) -> Int)?): Int? = nullableFun?.invoke(1, 2)
private fun combinedFun(combinedFun: (Int) -> ((Int) -> Unit)) {}
private fun combinedFun2(combinedFun: (Int) -> (Int) -> Unit) {}    // is equivalent to combinedFun

// Implements a function type as an interface
private class Int2Str : (Int) -> String {
    override fun invoke(p1: Int): String = p1.toString()
}

private val int2Str: (Int) -> String = Int2Str()

// Non-literal values of function types with and without receiver are interchangeable,
// so that the receiver can stand in for the first parameter, and vice versa.
// For instance, a value of type (A, B) -> C can be passed or assigned where a A.(B) -> C is expected and the other way around:
private val repeatFun: String.(Int) -> String = { times: Int -> this.repeat(times) }
private val twoParams: (String, Int) -> String = repeatFun  // OK
private fun runRepeat(foo: String.(Int) -> String): String {
    //    return inlineOrNot.invoke("hello",3)  // OK
    //    return "hello".inlineOrNot(3)         // OK
    return foo("hello", 3)        // OK
}

private val repeatRet = runRepeat(twoParams)   // OK

private val chaInfix: Infix.(Int) -> Int = { other: Int -> cha(other) }  // cha is called on the receiver object - Infix

// anonymous function
private val heInfix = fun Infix.(other: Int): Int {
    return this.i + other
}

@DslMarker
private annotation class HtmlTagMarker

@HtmlTagMarker
private abstract class Tag

private class Head : Tag()
private class Body : Tag()

// Lambda expressions can be used as function literals with receiver when the receiver type can be inferred from context
private class HTML : Tag() {
    fun head(init: Head.() -> Unit) = Head().init()
    fun body(init: Body.() -> Unit) = Body().init()
}

// `init` is a function type with receiver, this means that we need to pass an instance of type HTML (a receiver) to the function
private fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}

private val html =
    // we can call members of the receiver inside the function
    html {
        head {
            // Because of `@DslMarker`, making the compiler start controlling scopes, the Kotlin compiler knows which implicit receivers
            // are part of the same DSL and only allows to call members of the nearest receivers, or we could write head{} in head scope like below:
            // head {}
        }
        body {}
    }

// Inlinable lambdas can only be called inside the inline functions or passed as inlinable arguments,
// but noinline ones can be manipulated in any way we like: stored in fields, passed around etc.
//@Suppress("NOTHING_TO_INLINE")
private inline fun inlineOrNot(noinline notInlined: () -> Unit, inlined: () -> Unit) {}

private fun returns(): Boolean {
    inlineOrNot({
        return@inlineOrNot  // To exit a lambda, we have to use a label, and a bare return is forbidden inside a lambda, because a lambda cannot make the enclosing function return
    }, {
        return true // If the lambda is inlined, the return can be inlined as well, so it is allowed bare return
    })
    return false
}

// Note that some inline functions may call the lambdas passed to them as parameters not directly from the function body,
// but from another execution context, such as a local object or a nested function.
// In such cases, non-local control flow is also not allowed in the lambdas.
// To indicate that, the lambda parameter needs to be marked with the crossinline modifier
private inline fun crossinline(crossinline body: () -> Unit) {
    val f = object : Runnable {
        override fun run() {
            body()
        }
    }
}

/**
 * See more about refied: https://github.com/JetBrains/kotlin/blob/master/spec-docs/reified-type-parameters.md
 * *****************************************************************reified usage
 *
 *  fun <T> TreeNode.findParentOfType(clazz: Class<T>): T? {
 *      var p = parent
 *      while (p != null && !clazz.isInstance(p)) {
 *          p = p.parent
 *      }
 *      @Suppress("UNCHECKED_CAST")
 *      return p as T?
 *  }
 *
 *  treeNode.findParentOfType(MyTreeNode::class.java)
 *
 * *****************************************************************after reified optimized
 *
 *  inline fun <reified T> TreeNode.findParentOfType(): T? {
 *      var p = parent
 *      while (p != null && p !is T) {  // T is accessible inside the function
 *          p = p.parent
 *      }
 *      return p as T?
 *  }
 *
 *  treeNode.findParentOfType<MyTreeNode>()
 *
 */

// Normal functions (not marked as inline) cannot have reified parameters.
// Use reflection with a reified type
private inline fun <reified T> membersOf() = T::class.members

private fun printMembers() {
    println(M::class.members.joinToString("\n"))
    println(membersOf<M>().joinToString("\n"))
}

// *********************Restrictions for public API inline functions*********************
// To eliminate the risk of binary incompatibility being introduced by a change in non-public(private or internal) API of a module,
// the public-API(public or protected) inline functions are not allowed to use non-public-API(private or internal) declarations,
// i.e. private and internal declarations and their parts, in their bodies.
// An internal declaration can be annotated with @PublishedApi, which allows its use in public API inline functions
// and its body is checked too, as if it were public.
@PublishedApi
internal val iiP: Int = 2

public inline fun publicInline(foo: () -> Unit) {
    //    inlineOrNot({}, {})     // private: not allowed
    //    ii      // internal: not allowed
    iiP     // internal: but allowed because of @PublishedApi
}

private fun List<String>.getShortWordsTo(shortWords: MutableList<String>, maxLength: Int) {
    this.filterTo(shortWords) { it.length <= maxLength }
    // throwing away the articles
    val articles = setOf("a", "A", "an", "An", "the", "The")
    shortWords -= articles
}

// Two lists are considered equal if they have the same sizes and structurally equal elements at the same positions.
private fun equalLists() {
    val bob = User("Bob", 31)
    val people = listOf<User>(User("Adam", 20), bob, bob)
    val people2 = listOf<User>(User("Adam", 20), User("Bob", 31), bob)
    println(people == people2)  // true
    bob.age = 32
    println(people == people2)  // false
}

// Set<T> stores unique elements; their order is generally undefined.
private fun equalSets() {
    // The default implementation of Set – LinkedHashSet – preserves the order of elements insertion
    // HashSet: No order, requires less memory
    val numbers = setOf(1, 2, 3, 4)
    val numbers2 = setOf(4, 3, 2, 1)
    println(numbers == numbers2)                    // true
    println(numbers.first() == numbers2.first())    // false
    println(numbers.first() == numbers2.last())     // true
}

// Two maps containing the equal pairs are equal regardless of the pair order.
private fun equalMaps() {
    // The default implementation of Map – LinkedHashMap – preserves the order of elements insertion when iterating the map.
    // HashMap: No order, requires less memory
    val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)
    val anotherMap = mapOf("key2" to 2, "key1" to 1, "key4" to 1, "key3" to 3)
    println(numbersMap == anotherMap)   // true
}

private fun copyMap(map: Map<String, Any>) {
    val mapCopy = map.toMap()
    val mapCopy2 = map.toMutableMap()
}

// Avoiding creating short-living Pair objects("" to "") to avoid excessive memory usage.
private val numMap = mutableMapOf<String, String>().apply { this["one"] = "1";this["two"] = "2" }

private val indexedList = List(3) { it * 2 }    // [0, 2, 4]
private val mapList = indexedList.map { it * 3 }
private val mapIndexedList = indexedList.mapIndexed { index, i -> index * i }
private val listToMap = indexedList.associateWith { it }

private fun mutableListIterator() {
    val numbers = mutableListOf("one", "four", "four", "five")
    val iterator = numbers.listIterator()   // ListIterator supports iterating lists in forwards and backwards

    println("Index: ${iterator.nextIndex()}, value: ${iterator.next()}")  // Index: 0, value: one
    iterator.add("two")
    println("Index: ${iterator.nextIndex()}, value: ${iterator.next()}")  // Index: 2, value: four
    iterator.set("three")
    println("Index: ${iterator.nextIndex()}, value: ${iterator.next()}")  // Index: 3, value: four
    println("Index: ${iterator.nextIndex()}, value: ${iterator.next()}")  // Index: 4, value: five
    iterator.remove()
    println(numbers)    // [one, two, three, four]
    while (iterator.hasPrevious()) {
        println("Index: ${iterator.previousIndex()}, value: ${iterator.previous()}")  // Index: 3, value: four
    }
}

private val even = (1..100).filter { it % 2 == 0 }

private val suquence = sequenceOf("s")

// ******************************************Sequences******************************************
// When the processing of an Iterable includes multiple steps, they are executed eagerly:
// each processing step completes and returns its result – an intermediate collection. The following step executes on this collection.
// In turn, multi-step processing of sequences is executed lazily when possible:
// actual processing(computing) happens only when the result of the whole processing chain is requested.
// 在处理序列过程中，我们通常会对单个元素进行一系列的整体操作，然后再对下一个元素做进行一系列的整体操作，直到处理完集合中所有元素为止。
// 在处理iterable过程中，我们是每一步操作都是针对整个集合进行，直到所有操作步骤执行完毕为止
// So, the sequences let you avoid building results of intermediate steps, therefore improving the performance of the whole collection processing chain.
// However, the lazy nature of sequences adds some overhead which may be significant when processing smaller collections or doing simpler computations.
// Conclusion: Use Sequence for bigger collections with more than one processing step.
// 即当我们有多个处理步骤时，使用序列处理集合通常比直接处理集合更快。
// Java中的Stream用于计算量较大的处理以及需要启用并行模式的场景(Java的Stream流支持可以使用parallel函数以并行模式使用Java流)
private fun sequence() {
    val list = listOf(1, 2, 3)
    print(list.filter { it % 2 == 1 })          // Prints: [1, 3]
    // 序列的filter函数是一个中间操作，所以它不会做任何的计算，而是经过新的处理步骤对序列进行了加工。最终的计算将在终端操作中完成，如toList,sum函数
    val seq = list.asSequence()
    print(seq.filter { it % 2 == 1 })           // Prints: kotlin.sequences.FilteringSequence@XXXXXXXX
    print(seq.filter { it % 2 == 1 }.toList())  // Prints: [1, 3]

    list
        .filter { println("Filter $it, "); it % 2 == 1 }
        .map { println("Map $it, "); it * 2 }
    // Prints: Filter 1, Filter 2, Filter 3, Map 1, Map 3,

    seq
        .filter { println("Filter $it, "); it % 2 == 1 }
        .map { println("Map $it, "); it * 2 }
        .toList()
    // Prints: Filter 1, Map 1, Filter 2, Filter 3, Map 3,

    generateSequence(1) { it + 1 }  // The sequence generation stops when the provided function returns null.
        .map { it * 2 }
        .take(10)
        .forEach(::print)
    // Prints: 2468101214161820
    // generateSequence.toList() will cause OutOfMemoryError: FinalizeEscapeGC heap space

    val oddNumbers = sequence {
        yield(1)
        yieldAll(listOf(3, 5))
        yieldAll(generateSequence(7) { it + 2 })
    }
    println(oddNumbers.take(5).toList())    // [1, 3, 5, 7, 9]
}

// Destination is a mutable collection to which the function appends its resulting items instead of returning them in a new object.
private fun destination() {
    val nums = listOf("one", "two", "three", "four")
    val filterRet = mutableListOf<String>()  // destination object
    nums.filterTo(filterRet) { it.length > 3 }
    nums.filterIndexedTo(filterRet) { index, s -> index == 0 }
    println(filterRet)  // [three, four, one]: contains results of both operations

    // filter numbers right into a new hash set,
    // thus eliminating duplicates in the result
    val result = nums.mapTo(HashSet()) { it.length }
    println(result)     // [3, 4, 5]
}

private fun zip() {
    val colors = listOf("red", "brown", "grey")
    val animals = listOf("fox", "bear", "wolf", "cat")

    val zipInfix = colors zip animals
    println(zipInfix)       // [(red, fox), (brown, bear), (grey, wolf)]

    val zipTransform = colors.zip(animals) { color, animal -> "The ${animal.capitalize()} is $color" }
    println(zipTransform)   // [The Fox is red, The Bear is brown, The Wolf is grey]

    val unzipPairs = zipInfix.unzip()
    println(unzipPairs)     // ([red, brown, grey], [fox, bear, wolf])
}

private fun associate() {
    val nums = listOf("one", "two", "three", "four")

    // If two elements are equal, only the last one remains in the map.
    val numAsKey = nums.associateWith { it.length }
    println(numAsKey)       // {one=3, two=3, three=5, four=4}

    val numAsValue = nums.associateBy { it.first().toUpperCase() }
    println(numAsValue)     // {O=one, T=three, F=four}

    val numAsMap = nums.associateBy(keySelector = { it.first().toUpperCase() }, valueTransform = { it.length })
    println(numAsMap)       // {O=3, T=5, F=4}

    // Note that associate() produces short-living Pair objects which may affect the performance
    val names = listOf("Alice Adams", "Brian Brown", "Clara Campbell")
    val nameMap = names.associate { name -> name.split(" ").let { it[0] to it[1] } }
    println(nameMap)        // {Alice=Adams, Brian=Brown, Clara=Campbell}
}

private data class StringContainer(val values: List<String>)

private fun flat() {
    val numSets = listOf(setOf(1, 2, 3), setOf(4, 5, 6), setOf(1, 2))
    val nums = numSets.flatten()
    println(nums)       // [1, 2, 3, 4, 5, 6, 1, 2]

    // flatMap() behaves as a subsequent call of map() (with a collection as a mapping result) and flatten()
    val containers = listOf(
        StringContainer(listOf("one", "two", "three")),
        StringContainer(listOf("four", "five", "six")),
        StringContainer(listOf("seven", "eight"))
    )
    val strs = containers.flatMap { it.values }
    println(strs)       // [one, two, three, four, five, six, seven, eight]
}

private fun join() {
    val nums = listOf("one", "two", "three", "four")
    println(nums)                   // [one, two, three, four]
    println(nums.toString())        // [one, two, three, four]
    println(nums.joinToString())    // "one, two, three, four"

    val listStr = StringBuffer("The list of numbers:")
    nums.joinTo(listStr)
    println(listStr)                // "The list of numbers:one, two, three, four"
}

private fun filter() {
    val nums = listOf(null, 1, "two", 3.0, "four")
    val strs = nums.filterIsInstance<String>()
    println(strs)       // [two, four]

    val nums2 = listOf(null, "one", "two", null)
    val strs2 = nums2.filterNotNull()
    println(strs2)      // [one, two]
}

private fun partition() {
    val nums = listOf("one", "two", "three", "four")
    val (match, rest) = nums.partition { it.length > 3 }
    println(match)      // [three, four]
    println(rest)       // [one, two]
}

private fun testPredicates() {
    val nums = listOf("one", "two", "three", "four")
    println(nums.any { it.endsWith("e") })      // true
    println(nums.none { it.endsWith("a") })     // true
    println(nums.all { it.endsWith("e") })      // false
    println(emptyList<Int>().all { it > 5 })          // true   请注意，当在空集合上使用任何有效谓词调用时，all()将返回true。
}

private fun plusAndMinus() {
    val nums = listOf("one", "two", "three", "four")
    // The return value is a new read-only collection
    val plusList = nums + "five"
    val minusList = nums - listOf("three", "four", "other")
    println(plusList)       // [one, two, three, four, five]
    println(minusList)      // [one, two]

    val nums2 = mutableListOf("one", "two", "three", "four")
    nums2 += "five"
    println(nums2)          // [one, two, three, four, five]
    nums2 -= listOf("three", "four", "other")
    println(nums2)          // [one, two, five]
}

private fun group() {
    val nums = listOf("one", "two", "three", "four", "five")
    // Each key is the lambda result and the corresponding value is the List of elements on which this result is returned.
    val map1 = nums.groupBy { it.first().toUpperCase() }
    println(map1)   // {O=[one], T=[two, three], F=[four, five]}

    val map2 = nums.groupBy(keySelector = { it.first() }, valueTransform = { it.toUpperCase() })
    println(map2)   // {o=[ONE], t=[TWO, THREE], f=[FOUR, FIVE]}

    // If you want to group elements and then apply an operation to all groups at one time, use the function groupingBy().
    val nums2 = listOf("one", "two", "three", "four", "five", "six")
    val grouping = nums2.groupingBy { it.first() }

    val map3 = grouping.eachCount()   // eachCount(): counts the elements in each group
    println(map3)   // {o=1, t=2, f=2, s=1}

    // fold() and reduce() perform fold and reduce operations on each group as a separate collection and return the results.
    //    val map4 = grouping.fold(...)
    //    val map5 = grouping.reduce(...)
    // aggregate() applies a given operation subsequently to all the elements in each group and returns the result.
    // This is the generic way to perform any operations on a Grouping. Use it to implement custom operations when fold or reduce are not enough.
    //    val map6 = grouping.aggregate(...)
}

private fun slice() {
    val nums = listOf("one", "two", "three", "four", "five", "six")
    println(nums.slice(1..3))           // [two, three, four]
    println(nums.slice(0..4 step 2))    // [one, three, five]
    println(nums.slice(setOf(3, 5, 0)))        // [four, six, one]
}

private fun takeAndDrop() {
    val nums = listOf("one", "two", "three", "four", "five", "six")
    println(nums.take(3))           // [one, two, three]
    println(nums.takeLast(3))       // [four, five, six]
    println(nums.drop(1))           // [two, three, four, five, six]
    println(nums.dropLast(5))       // [one]

    println(nums.takeWhile { !it.startsWith('f') })     // [one, two, three]
    println(nums.takeLastWhile { it != "three" })            // [four, five, six]
    println(nums.dropWhile { it.length == 3 })               // [three, four, five, six]
    println(nums.dropLastWhile { it.contains('i') })    // [one, two, three, four]
}

private fun chunk() {
    val nums = (0..13).toList()
    val chunkedLists = nums.chunked(3)
    println(chunkedLists)      // [[0, 1, 2], [3, 4, 5], [6, 7, 8], [9, 10, 11], [12, 13]]

    val transferChunkedLists = nums.chunked(3) { it.sum() }  // `it` is a chunk of the original collection
    println(transferChunkedLists)      // [3, 12, 21, 30, 25]
}

private fun window() {
    val nums = listOf("one", "two", "three", "four", "five")
    val windowedLists = nums.windowed(3)
    println(windowedLists)  // [[one, two, three], [two, three, four], [three, four, five]]

    val nums2 = (1..10).toList()
    println(nums2.windowed(3, step = 2, partialWindows = true)) // [[1, 2, 3], [3, 4, 5], [5, 6, 7], [7, 8, 9], [9, 10]]
    println(nums2.windowed(3, step = 2, partialWindows = false)) // [[1, 2, 3], [3, 4, 5], [5, 6, 7], [7, 8, 9]]
    println(nums2.windowed(3) { it.sum() }) // [6, 9, 12, 15, 18, 21, 24, 27]

    val twoElementWindows = nums.zipWithNext()
    println(twoElementWindows)      // [(one, two), (two, three), (three, four), (four, five)]
    println(nums.zipWithNext { s1, s2 -> s1.length > s2.length })   // [false, false, true, false]
}

private fun retrievingElements() {
    val nums = linkedSetOf("one", "two", "three", "four", "five")
    println(nums.elementAt(3))      // four
    println(nums.elementAtOrNull(5))    // null
    println(nums.elementAtOrElse(5) { i -> "index: $i undefined!" })    // index: 5 undefined!

    val nums2 = sortedSetOf("one", "two", "three", "four")   // elements are stored in the ascending order
    println(nums2.elementAt(0))     // four

    println(nums2.first())      // four
    println(nums2.last())       // two

    val nums3 = listOf("one", "two", "three", "four", "five", "six")
    println(nums3.first { it.length > 3 })              // three
    println(nums3.firstOrNull { it.length > 6 })        // null
    // find() == firstOrNull()
    println(nums3.find { it.length > 6 })               // null
    println(nums3.last { it.startsWith("f") })    // five
    println(nums3.lastOrNull { it.length > 6 })         // null
    // findLast() instead of lastOrNull()
    println(nums3.findLast { it.length > 6 })           // null

    println(nums3.random())     // -random

    println(nums3.contains("four"))                     // true
    println("zero" in nums3)                            // false
    println(nums3.containsAll(listOf("four", "two")))   // true
    println(nums3.containsAll(listOf("one", "zero")))   // false
}

private fun order() {
    val abc = listOf("aaa", "bb", "c")

    val lengthComparator = kotlin.Comparator { str1: String, str2: String -> str1.length - str2.length }
    println(abc.sortedWith(lengthComparator))          // [c, bb, aaa]
    println(abc.sortedWith(compareBy { it.length }))   // [c, bb, aaa]

    val nums = listOf("one", "two", "three", "four")

    println(nums.sorted())              // [four, one, three, two]
    println(nums.sortedDescending())    // [two, three, one, four]

    println(nums.sortedBy { it.length })            // [one, two, four, three]
    println(nums.sortedByDescending { it.last() })  // [four, two, one, three]

    println(nums.reversed())    // [four, three, two, one] reversed(): returns a new collection
    println(nums.asReversed())  // [four, three, two, one] asReversed(): returns a reversed view of the same collection instance

    // shuffled(): returns a new List in a random order
    println(nums.shuffled())

    // !!!!!!!!!!!!!!!!!!!!Note: For mutable List, above operations without the ed/d suffix !!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // If the original list is mutable, all its changes reflect in its reversed views and vice versa.
    val mutableNums = mutableListOf("one", "two", "three", "four")
    val reversedNums = mutableNums.asReversed()
    println(reversedNums)       // [four, three, two, one]
    mutableNums.add("five")
    println(reversedNums)       // [five, four, three, two, one]
}

private fun aggregateOperations() {
    val nums = listOf(5, 42, 10, 4)
    println(nums.minByOrNull { it % 3 })  // 42

    val strs = listOf("one", "two", "three", "four")
    println(strs.maxWithOrNull(compareBy { it.length }))  // three

    println(nums.sumBy { it * 2 })  // 122  return Int
    println(nums.sumByDouble { it.toDouble() / 2 }) // 30.5  return Double

    // reduce() and fold() that apply the provided operation to the collection elements sequentially and return the accumulated result
    // reduce() uses the list's first and second elements as arguments on the first step
    println(nums.reduce { sum, element -> sum + element * 2 })    // 117 not 122, the first element won't be doubled
    println(nums.fold(1) { sum, element -> sum + element * 2 })     // 123
    // start from the last element and then continue to previous.
    // Note that the operation arguments change their order
    println(nums.foldRight(0) { element, sum -> sum + element * 2 })    // 122
    println(nums.reduceRight { element, sum -> sum + element * 2 })     // 118

    println(nums.foldIndexed(0) { idx, sum, element -> if (idx % 2 == 0) sum + element else sum })      // 15
    println(nums.foldRightIndexed(0) { idx, element, sum -> if (idx % 2 == 0) sum + element else sum }) // 15
    println(nums.reduceIndexed { idx, sum, element -> if (idx % 2 == 0) sum + element else sum })             // 15
    println(nums.reduceRightIndexed { idx, element, sum -> if (idx % 2 == 0) sum + element else sum })        // 19
}

private fun remove() {
    val nums1 = mutableListOf(1, 2, 3, 3, 4, 5)
    nums1.remove(3)
    println(nums1)          // [1, 2, 3, 4, 5]  removes the first `3`

    val nums2 = mutableListOf(1, 2, 3, 3, 4, 5)
    nums2 -= 3
    println(nums2)          // [1, 2, 3, 4, 5]  removes the first `3`

    val nums3 = mutableListOf(1, 2, 3, 3, 4, 5)
    nums3 -= setOf(3, 4)    // 等同于removeAll()
    println(nums3)          // [1, 2, 5]
}

private fun listIndex() {
    val nums = mutableListOf(1, 2, 3, 4)
    println(nums.getOrNull(5))          // null
    println(nums.getOrElse(5) { -1 })   // -1

    // subList() returns a view of the specified elements range as a list, if an element of the original collection changes,
    // it also changes in the previously created sublists and vice versa.
    val subList = nums.subList(1, 2)
    println(subList)     // [2]
    nums[1] = 9
    println(subList)     // [9]
    subList[0] = 2
    println(nums)        // [1, 2, 3, 4]

    println(nums.indexOfFirst { it > 1 })       // 1
    println(nums.indexOfLast { it % 2 == 1 })   // 2
}

// binarySearch() requires the list to be sorted in ascending order(升序) according to a certain ordering
// When element isn't exists, it returns (-insertionPoint - 1), 'insertionPoint' is the index where this element should be inserted for keeping sorted.
// If there is more than one element with the given value, the search can return any of their indices.
private fun binarySearch() {
    val nums = mutableListOf("one", "two", "three", "four")
    nums.sort()
    println(nums)       // [four, one, three, two]
    println(nums.binarySearch("two"))   // 3
    println(nums.binarySearch("z"))     // -5 = (-4-1)
    // You can also specify an index range to search in: in this case, the function searches only between two provided indices.
    println(nums.binarySearch("two", 0, 2))     // -3 = (-2-1)

    val sortedUsers = listOf(
        User("a", 32),
        User("sad", 43),
        User("zx", 52)
    )
    // search User(..)
    println(sortedUsers.binarySearch(User("zx", 52), compareBy<User> { it.age }.thenBy { it.name }))  // 2

    // search age
    // It takes a comparison function mapping elements to Int values and searches for the element where the function returns zero.
    fun ageComparison(user: User, searchedAge: Int): Int = sign((user.age - searchedAge).toDouble()).toInt()
    println(sortedUsers.binarySearch { ageComparison(it, 43) })     // 1

    // a case-insensitive order for String elements.
    val sortedColors = listOf("Blue", "green", "ORANGE", "Red", "yellow")
    println(sortedColors.binarySearch("RED", String.CASE_INSENSITIVE_ORDER)) // 3
}

private fun set() {
    val nums = setOf("one", "two", "three")
    println(nums union setOf("four", "five"))       // [one, two, three, four, five]
    println(setOf("four", "five") union nums)       // [four, five, one, two, three]
    println(nums intersect setOf("two", "one"))     // [one, two]
    println(nums subtract setOf("three", "four"))   // [one, two]
    println(nums subtract setOf("four", "three"))   // [one, two]
}

private fun arrayManipulation() {
    val array = arrayOf("a", "b", "c")
    println(array.toString())           // [Ljava.lang.String;@78308db1
    println(array.contentToString())    // [a, b, c]
    // array.contentEquals(...)
    println(array.contentHashCode())    // 126145
}

@RequiresApi(Build.VERSION_CODES.N)
private fun map() {
    val numsMap = mapOf("one" to 1, "two" to 2, "three" to 3)
    println(numsMap.getOrDefault("four", 10))   // 10   (RequiresApi N)
    println(numsMap.getOrElse("four") { 10 })               // 10
    println(numsMap["five"])                                    // null
    //    println(numsMap.getValue("five"))                       // NoSuchElementException

    val keys = numsMap.keys
    val values = numsMap.values

    val numsMap2 = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
    val filteredMap = numsMap2.filter { (key, value) -> key.endsWith("1") && value > 10 }
    println(filteredMap)    // {key11=11}

    val filteredKeysMap = numsMap2.filterKeys { it.endsWith("1") }
    val filterValuesMap = numsMap2.filterValues { it < 10 }
    println(filteredKeysMap)    // {key1=1, key11=11}
    println(filterValuesMap)    // {key1=1, key2=2, key3=3}

    println(numsMap + Pair("four", 4))                  // {one=1, two=2, three=3, four=4}
    println(numsMap + Pair("one", 10))                  // {one=10, two=2, three=3}
    println(numsMap + mapOf("five" to 5, "one" to 11))  // {one=11, two=2, three=3, five=5}

    println(numsMap - "one")                    // {two=2, three=3}
    println(numsMap - listOf("two", "four"))    // {one=1, three=3}
    println(numsMap - setOf("two", "four"))     // {one=1, three=3}

    val numsMap3 = mutableMapOf("one" to 1, "two" to 2)
    val previousValue = numsMap3.put("one", 11)
    println(previousValue)  // 1
    numsMap3.remove("two", 3)
    println(numsMap3)       // {one=11, two=2}

    val numsMap4 = mutableMapOf("one" to 1, "two" to 2, "three" to 3, "threeAgain" to 3)
    numsMap4 -= "one"   // 等同于remove("one")
    println(numsMap4)   // {two=2, three=3, threeAgain=3}
    numsMap4.values.remove(3)   // values.remove() removes only the first entry with the given value.
    println(numsMap4)   // {two=2, threeAgain=3}
}

private fun destructuringDeclaration() {
    val user = User("asd", 11)
    val (name, age) = user    // name = user.component1() age = user.component2()
    val (_, year) = user
    // for ((a, b) in collection/map) { ... }
    // val (a, b) = function(...): DataClass
    // val (a, b) = function(...): Pair<A, B>
    // lambda: {(a, b), c->...}
}

private fun typeCheck() {
    val x = 1
    // val y = x as String
    // println(y)  // ClassCastException
    val z = x as? String
    println(z)  // null

    // Kotlin ensures type safety of operations involving generics at compile time, while, at runtime,
    // instances of generic types hold no information about their actual type arguments. For example, List<Foo> is erased to just List<*>.
    // In general, type erasure makes checking actual type arguments of a generic type instance impossible at runtime.

    // On the JVM, the array types (Array<Foo>) retain the information about the erased type of their elements,
    // and the type casts to an array type are partially checked: the nullability and actual type arguments of the elements type are still erased.
    // For example, the cast foo as Array<List<String>?> will succeed if foo is an array holding any List<*>, nullable or not.
    val foo = arrayOf(listOf(User("", 1)))
    val bar = foo as Array<List<String>?>   // succeed
    bar.forEach { println(it) }     // [User(name=, age=1)]
}

private inline fun <reified T> List<*>.asListOfType(): List<T>? =
    if (all { it is T })
        @Suppress("UNCHECKED_CAST")
        this as List<T> else
        null

private fun equality() {
    // == operator in Kotlin only compares the data or variables, whereas in FinalizeEscapeGC or other languages == is generally used to compare the references.
    // === operator is used to compare the reference of two variable or object.
    // For values which are represented as primitive types at runtime (for example, Int), the === equality check is equivalent to the == check.

    val int1 = 10
    val int2 = 10

    println(int1 == int2)           // true
    println(int1.equals(int2))      // true
    println(int1 === int2)          // true

    val first = Integer(10)   // true
    val second = Integer(10)  // true

    println(first == second)        // true
    println(first.equals(second))   // true
    println(first === second)       // false

    class Employee(val name: String)

    val emp1 = Employee("Suneet")
    val emp2 = Employee("Suneet")

    // As Empoyee is not a primitive datatype or wrapper class, all three compared the references
    println(emp1 == emp2)           // false
    println(emp1.equals(emp2))      // false
    println(emp1 === emp2)          // false

    // But in the case of string comparison, if only checks the contents of the string which were equal so it returns true for every case.
    println(emp1.name == emp2.name)         // true
    println(emp1.name.equals(emp2.name))    // true
    println(emp1.name === emp2.name)        // true

    data class Employer(val name: String)

    val emr1 = Employer("OK")
    val emr2 = Employer("OK")

    // If its a data class, the compiler compares the data and return true if the content is same.
    println(emr1 == emr2)         // true
    println(emr1.equals(emr2))    // true
    println(emr1 === emr2)        // false

    println(emr1.name == emr2.name)       // true
    println(emr1.name.equals(emr2.name))  // true
    println(emr1.name === emr2.name)      // true

    // The difference between == and .equals is in case of Float and Double comparison,
    // .equals disagrees with the IEEE 754 Standard for Floating-Point Arithmetic:
    // 1.NaN is considered equal to itself;
    // 2.NaN is considered greater than any other element including POSITIVE_INFINITY;
    // 3.-0.0 is considered less than 0.0.

    val negZero = -0.0f
    val posZero = 0.0f

    println(negZero == posZero)         // true
    println(negZero.equals(posZero))    // false
    println(negZero === posZero)        // true
}

private fun operatorOverloading() {
    data class Point(val x: Int, val y: Int)

    operator fun Point.unaryMinus() = Point(-x, -y)

    val point = Point(10, 20)

    println(-point)     // Point(x=-10, y=-20)

    data class Count(val index: Int) {
        operator fun plus(increment: Int): Count {
            return (Count(index + increment))
        }
    }

    val count = Count(2)

    println(count + 3)     // Counter(dayIndex=5)

    operator fun Count.get(x: Int, y: Int, z: Int): String {
        return "count[i1, ..., in] translated to count.get(i1, ..., in)"
    }

    val a = count[1, 2, 3]

    operator fun Count.set(x: Int, y: Int, z: Int, point: Point): String {
        return "count[i1, ..., in] = b translated to count.set(i1, ..., in, b)"
    }

    count[1, 3, 4] = point

    operator fun Count.invoke(a: Int, b: Int, c: Int): String {
        return "count(i1, ..., in) translated to count.invoke(i1, ..., in)"
    }

    count(1, 2, 3)
}

private fun nullSafety() {
    val nullableList: List<Int?> = listOf(1, 2, null, 4)
    val intList: List<Int> = nullableList.filterNotNull()
    println(intList)    // [1, 2, 4]
}

// Nothing is used to mark code locations that can never be reached, use Nothing to mark a function that never returns
private fun exceptions(): Nothing {
    val x = null    // 'x' has type `Nothing?`
    val l = listOf(null)    // 'l' has type `List<Nothing?>

    throw IllegalArgumentException("")   // throw is Nothing
    println("never reached")
}

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.EXPRESSION,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY_GETTER
    // ...
)
@Retention(AnnotationRetention.SOURCE)
@Repeatable
@MustBeDocumented
private annotation class Fancy(val cls: KClass<*> = Father::class, val tag: String = "")

@Target(AnnotationTarget.PROPERTY_SETTER)
private annotation class Jack(val fancy: Fancy = Fancy())  // '@' is omitted

@Fancy(Father::class)
private class OOO {
    @Fancy
    fun baz(@Fancy(tag = "param") foo: Int): Int {
        return (@Fancy 1)
    }

    var x: Int = 1
        @Fancy set

    val f = @Fancy { println("annotate lambda") }
}

// Annotation Use-site Targets:
//
// @file;
// @property (annotations with this target are not visible to FinalizeEscapeGC);
// @field;
// @get (property getter);
// @set (property setter);
// @receiver (receiver parameter of an extension function or property);
// @param (constructor parameter);
// @setparam (property setter parameter);
// @delegate (the field storing the delegate instance for a delegated property).
private class Example(
    @field:Fancy val foo: String,
    @get:Fancy val bar: String,
    @param:Fancy val quux: String
) {
    @set:[Fancy Jack]   // If you have multiple annotations with the same target, avoid repeating the target by adding brackets
    var exam: String = ""

    fun @receiver:Fancy String.foo() = ""
}

private val String.lastChar: Char
    get() = this[length - 1]

private fun reflection() {
    val cls = Father::class
    val javaCls = Father::class.java

    val father: Father = Son()
    println(father::class.qualifiedName)     // com.youyes.learning.Son

    fun isOdd(x: Int) = x % 2 != 0
    fun isOdd(s: String) = s == "brillig" || s == "slithy" || s == "tove"

    val predicate: (String) -> Boolean = ::isOdd
    println(listOf(1, 2, 3).filter(::isOdd))

    fun inTotal(x: Int, y: Int, z: Int) = x + y + z
    val kFun = ::inTotal

    // Note that even if you initialize a variable with a reference to an extension function, the inferred function type will have no receiver
    // (it will have an additional parameter accepting a receiver object). To have a function type with receiver instead, specify the type explicitly
    val isEmptyStrList = List<String>::isEmpty
    val isEmptyStrList2: List<String>.() -> Boolean = List<String>::isEmpty

    // To access properties as first-class objects
    val x = ::ii
    println(x.get())    // 1
    println(x.name)     // ii

    val y = ::i
    ::i.set(2)
    println(i)  // 2

    val strs = listOf("a", "bc", "def")
    val leng = String::length
    println(strs.map(leng))

    class A(val p: Int)

    val prop = A::p
    println(prop.get(A(1)))     // 1

    println(String::lastChar.get("abc"))    // c

//    println(A::p.javaGetter)    // public final int com.youyes.learning.fileName$main$A.getP()
//    println(A::p.javaField)     // private final int com.youyes.learning.fileName$main$A.p

    fun getKClass(o: Any): KClass<Any> = o.javaClass.kotlin

    fun function(factory: () -> Father) {
        val x: Father = factory()
    }

    val constructor = ::Father
    function(constructor)

    // Instead of calling the method matches directly we are storing a reference to it. Such reference is bound to its receiver.
    // It can be called directly or used whenever an expression of function type is expected
    val numberRegex = "\\d+".toRegex()
    println(numberRegex.matches("29"))
    val isNumber = numberRegex::matches
    println(isNumber("29"))

    class Outer {
        inner class Inner
    }

    val o = Outer()
    val boundInnerConstructor = o::Inner
}

@Deprecated("This experimental API marker is not used anymore. Remove its usages from your code.")
@Experimental(level = Experimental.Level.WARNING)   // Experimental.Level.ERROR by default
@Retention(AnnotationRetention.BINARY)  // Experimental required
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
private annotation class ExperimentalDateTime

@ExperimentalDateTime
private class DateProvider

//************************************Propagating use************************************start
// for third-party use (a library)
@ExperimentalDateTime
private fun getDate() {
    val dateProvider = DateProvider()
}

@ExperimentalDateTime
private fun printDate() {
    println(getDate())
}
//************************************Propagating use**************************************end

//**********************************Non-propagating use**********************************start
// for modules which don't provide their own API(application modules)
@UseExperimental(ExperimentalDateTime::class)
private fun getYear() {
    val dateProvider = DateProvider()
}

private fun printYear() {
    println(getYear())
}
//***********************************Non-propagating use***********************************end

class JavaField {

    // Usually backing fields are private but they can be exposed in FinalizeEscapeGC by @JvmField(transform from "javaField.getField();" to "javaField.field;")
    // @JvmField supports which has a backing field, not private, not open, not override, not const and not a delegated property.
    @JvmField
    var one: Int = 1

    // Late-Initialized properties are also exposed as fields in FinalizeEscapeGC, the visibility of the field = setter
    lateinit var lateF: String
        private set

    // Kotlin properties declared in a named object or a companion object will have static backing fields
    companion object {
        @JvmField   // @JvmField: transform from "JavaField.Companion.getNUMS();" to "JavaField.NUMS;"
        val NUMS = arrayListOf(1, 2, 3)

        // Properties declared as const are turned into static fields in FinalizeEscapeGC
        const val Tag = "===Kotlin==="

        // Call in FinalizeEscapeGC -> JavaField.Companion.NonStaticFun();
        fun nonStaticFun() {
        }

        // @JvmStatic: the compiler will additionally generate a static method -> "JavaField.staticFun();"
        @JvmStatic
        fun staticFun() {
        }

        var staticF: String = ""
            @JvmStatic get  // generate -> JavaField.getStaticF();
            @JvmStatic set  // generate -> JavaField.setStaticF(...);
    }
}

interface Robot {

    /**
     * @JvmDefault: The default implementation is available for FinalizeEscapeGC classes implementing the interface(Starting from JDK 1.8)
     * Note that if an interface with @JvmDefault methods is used as a delegate, the default method implementations are called
     * even if the actual delegate type provides its own implementations.
     * @see JvmDefault
     */
    // @JvmDefault     // kotlinOptions.freeCompilerArgs += ["-Xjvm-default=compatibility"]
    fun move() {
        println("move")
    }

    fun speak()
}

// Conversion from Class to KClass in FinalizeEscapeGC: kotlin.jvm.JvmClassMappingKt.getKotlinClass(Kotlin.class);

fun List<String>.validList(): Boolean = true

@JvmName("validIntList")    // Jvm signature clashes(may due to type erasure), handling it with @JvmName(JavaName.validIntList();)
fun List<Int>.validList(): Boolean = false

// @JvmOverloads: Exposing multiple overloads to FinalizeEscapeGC callers when Kotlin function with default parameter values,
// or it will be visible in FinalizeEscapeGC only as a full signature(It can't be used on abstract methods, including methods defined in interfaces).
class Circle @JvmOverloads constructor(centerX: Int, centerY: Int, radius: Double = 1.0) {

    @JvmOverloads
    fun draw(label: String, lineWidth: Int = 1, color: String = "red") {
    }
}

// Call it from FinalizeEscapeGC and catch the exception
@Throws(IOException::class)     // √
fun write2File() {

    throw IOException() // ×
}

private val rand = Random.nextInt(42)  // number is in range [0, limit)

private fun arrayCopy() {
    val sourceArr = arrayOf("k", "o", "t", "l", "i", "n")
    val targetArr = sourceArr.copyInto(arrayOfNulls<String>(6), 3, startIndex = 3, endIndex = 6)
    println(targetArr.contentToString())    // [null, null, null, l, i, n]

    sourceArr.copyInto(targetArr, startIndex = 0, endIndex = 2)
    println(targetArr.contentToString())    // [k, o, null, l, i, n]
}

@Parcelize
private class Par(
    val a: String,
    val b: Int
) : Parcelable {

    @IgnoredOnParcel
    val x: String = ""
}

private class ExternalClass(val value: Int)

// 在一个类的外部去Parcelize
private object ExternalClassParceler : Parceler<ExternalClass> {
    override fun create(parcel: Parcel) = ExternalClass(parcel.readInt())

    override fun ExternalClass.write(parcel: Parcel, flags: Int) {
        parcel.writeInt(value)
    }
}

// 三种通过注解支持外部Parcelize类的应用方式
// Class-local parceler
@Parcelize
@TypeParceler<ExternalClass, ExternalClassParceler>()
private class MyClass1(val external: ExternalClass) : Parcelable

// Property-local parceler
@Parcelize
private class MyClass2(@TypeParceler<ExternalClass, ExternalClassParceler>() val external: ExternalClass) : Parcelable

// Type-local parceler
@Parcelize
private class MyClass3(val external: @WriteWith<ExternalClassParceler>() ExternalClass) : Parcelable

