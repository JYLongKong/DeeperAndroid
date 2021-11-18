package com.lgjy.deeper.common.learn

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.system.measureTimeMillis

/**
 * Created by LGJY on 19/4/30.
 * Email：yujye@sina.com
 *
 * Learning Coroutine
 */

private fun main1() {
    GlobalScope.launch {
        // launch a new coroutine in background and continue
        delay(1000) // non-blocking delay for 1 second (default time unit is ms)
        println("World")    // print after delay
    }
    println("Hello,")   // main thread continues while coroutine is delayed
    Thread.sleep(2000L) // block main thread for 2 second to keep JVM alive
}

private fun main2() {    // the same result as main()'s
    thread {
        //        delay(1000) // Error: Kotlin: Suspend functions are only allowed to be called from a coroutine or another suspend function
        Thread.sleep(1000)
        println("Universe")
    }
    println("Hello,")
    Thread.sleep(2000L)
}

private fun main3() {
    GlobalScope.launch {
        delay(1000)
        println("Sun!")
    }
    println("Hello,")   // main thread continues here immediately
    runBlocking {
        // but this expression blocks the main thread
        delay(2000L)    // ... while we delay for 2 seconds to keep JVM alive
    }
}

private fun main4() = runBlocking {
    // here we can use suspending functions using any assertion style that we like
    GlobalScope.launch {
        delay(1000)
        println("GGG")
    }
    println("Hello,")   // main coroutine continues here immediately
    delay(2000) // delaying for 2 seconds to keep JVM alive
}

private fun main5() = runBlocking {
    val job = GlobalScope.launch {
        // launch a new coroutine and keep a reference to its job
        delay(1000)
        println("SSS")
    }
    println("Hello,")
    job.join()  // wait until child coroutine completes
}

private fun main6() =
// Every coroutine builder, including runBlocking adds an instance of CoroutineScope to the scope of its code block
    // an outer coroutine (runBlocking) does not complete until all the coroutines launched in its scope complete
    runBlocking {
        launch {
            // launch a new corountine in the scope of runBlocking
            delay(1000)
            println("qqq")
        }
        println("Hello,")
    }

private fun main() = runBlocking {
    launch {
        delay(200)
        println("Task from runBlocking")
    }
    // The main difference between runBlocking and coroutineScope is that the runBlocking method blocks the current thread for waiting,
    // while coroutineScope just suspends, releasing the underlying thread for other usages.
    // runBlocking is a regular function
    // coroutineScope is a suspending function
    coroutineScope {
        // creates a coroutine scope
        launch {
            delay(500)
            println("Task from nested launch")
        }

        delay(100)
        println("Task from coroutine scope")    // this line will be printed before the nested launch
    }

    println("Coroutine scope is over")  // this line is not printed until the nested launch completes
}

private fun main8() = runBlocking {
    launch { doWorld() }
    println("Hello,")
}

private suspend fun doWorld() {
    delay(1000)
    println("World")
}

private fun main9() = runBlocking {
    repeat(100_000) {
        launch {
            delay(1000)
            print(".")
        }
    }
}

private fun main10() = runBlocking {
    // Active coroutines that were launched in GlobalScope do not keep the process alive. They are like daemon threads.
    GlobalScope.launch {
        repeat(1000) {
            println("I'm sleeping $it")
            delay(500)
        }
    }
    delay(1600)
}

private fun main11() = runBlocking {
    val job = launch {
        repeat(1000) {
            println("job: I'm sleeping $it ...")
            delay(500L)
        }
    }
    delay(1300L)
    println("main: I'm tired of waiting!")
    //    job.cancel()
    //    job.join()
    job.cancelAndJoin()
    println("main: Now I can quit.")
}

// if a coroutine is working in a computation and does not check for cancellation, then it cannot be cancelled
private fun main12() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // computation loop,just wastes CPU
            // yield() // invoke a suspending function that checks for cancellation
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job:I'm sleeping ${i++}...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

// Replace while (i < 5) in the previous example with while (isActive) and rerun it.
private fun main13() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) {  // explicitly check the cancellation status,cancellable computation loop
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job:I'm sleeping ${i++}...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

private fun main14() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            println("job: I'm running finally")
        }
    }
    delay(1300L)
    println("main: I'm running finally")
    job.cancelAndJoin()
    println("main: Now I can quit.")
}

private fun main15() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            // Any attempt to use a suspending function in the finally block of the previous example causes CancellationException,
            // because the coroutine running this code is cancelled
            // when you need to suspend in a cancelled coroutine you can wrap the corresponding code
            // in withContext(NonCancellable) {...} using withContext function and NonCancellable context
            withContext(NonCancellable) {
                println("job: I'm running finally")
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: Now I can quit.")
}

private fun main16() = runBlocking {
    withTimeout(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
}

private fun main17() = runBlocking {
    // withTimeoutOrNull: returns null on timeout instead of throwing an exception
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
        "Done"
    }
    println("Result is $result")
}

private fun main18() = runBlocking {
    val channel = Channel<Int>()
    launch {
        // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
        for (x in 1..5) {
            // delay(1000L)
            channel.send(x * x)
        }
    }
    // here we print five received integers:
    repeat(5) { println(channel.receive()) }
    println("Done!")
}

private fun main19() = runBlocking {
    val channel = Channel<Int>()
    launch {
        for (x in 1..5) {
            // delay(1000L)
            channel.send(x * x)
        }
        channel.close() // we're done sending
    }
    // here we print received values using 'for' loop (until the channel is closed)
    for (y in channel) println(y)
    println("Done!")
}

@ExperimentalCoroutinesApi
private fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) {
        // delay(1000L)
        send(x * x)
    }
}

@ExperimentalCoroutinesApi
private fun main20() = runBlocking {
    val squares = produceSquares()
    squares.consumeEach { println(it) }
    println("Done!")
}

@ExperimentalCoroutinesApi
private fun CoroutineScope.produceNumbers(): ReceiveChannel<Int> = produce {
    var x = 1
    while (true) {
        send(x++)
    }
}

// All functions that create coroutines are defined as extensions on CoroutineScope,
// so that we can rely on structured concurrency to make sure that we don't have lingering global coroutines in our application.
@ExperimentalCoroutinesApi
private fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) {
        send(x * x)
    }
}

@ExperimentalCoroutinesApi
private fun main21() = runBlocking {
    // produce integers from 1 and on
    val numbers = produceNumbers()
    // squares integers
    val squares = square(numbers)
    for (i in 1..5) println(squares.receive())
    println("Done!")
    // cancel children coroutines to let main finish
    coroutineContext.cancelChildren()
}

private fun numbersFrom(start: Int) = iterator {
    var x = start
    while (true) {
        yield(x++)
    }
}

private fun filter(numbers: Iterator<Int>, prime: Int) = iterator {
    for (x in numbers) {
        if (x % prime != 0) {
            yield(x)
        }
    }
}

// 求质数--standard library
private fun main22() {
    var cur = numbersFrom(2)
    for (i in 1..10) {
        val prime = cur.next()
        println(prime)
        cur = filter(cur, prime)
    }
}

@ExperimentalCoroutinesApi
private fun CoroutineScope.numbersFrom(start: Int) = produce {
    var x = start
    while (true) {
        send(x++)
    }
}

// sequence/iterator do not allow arbitrary suspension
// produce is fully asynchronous
@ExperimentalCoroutinesApi
private fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce {
    for (x in numbers) {
        if (x % prime != 0) {
            send(x)
        }
    }
}

// 求质数
// the benefit of a pipeline that uses channels as shown below is that
// it can actually use multiple CPU cores if you run it in Dispatchers.Default context.
@ExperimentalCoroutinesApi
private fun main23() = runBlocking {
    var cur = numbersFrom(2)
    for (i in 1..10) {
        val prime = cur.receive()
        println(prime)
        cur = filter(cur, prime)
    }
    coroutineContext.cancelChildren()
}

@ExperimentalCoroutinesApi
private fun CoroutineScope.produceNumbers2() = produce {
    var x = 1
    while (true) {
        send(x++)
        delay(1000L)
    }
}

private fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
    // Unlike consumeEach, this for loop pattern is perfectly safe to use from multiple coroutines
    // If one of the processor coroutines fails, then others would still be processing the channel,
    // while a processor that is written via consumeEach always consumes (cancels) the underlying channel on its normal or abnormal completion
    for (msg in channel) {
        println("Processor #$id received $msg")
    }
    // channel.consumeEach {
    //     println("Processor #$id received $it")
    // }
}

// Multiple coroutines may receive from the same channel, distributing work between themselves
@ExperimentalCoroutinesApi
private fun main24() = runBlocking {
    val producer = produceNumbers2()
    repeat(5) { launchProcessor(it, producer) }
    delay(9500L)
    producer.cancel() // cancel producer coroutine closes its channel and thus kill them all
}

private suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
    while (true) {
        delay(time)
        channel.send(s)
    }
}

private fun main25() = runBlocking {
    val channel = Channel<String>()
    launch { sendString(channel, "foo", 1000L) }
    launch { sendString(channel, "BAR!", 1500L) }
    repeat(6) {
        println(channel.receive())
    }
    coroutineContext.cancelChildren()
}

// Buffer allows senders to send multiple elements before suspending
private fun main26() = runBlocking {
    val channel = Channel<Int>(5)    // create buffered channel
    val sender = launch {
        repeat(10) {
            println("Before sending $it")
            channel.send(it)    // will suspend when buffer is full
            println("After sending $it")
        }
    }
    delay(1000L)
    sender.cancel()
}

private data class Ball(var hits: Int)

private suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) {
        ball.hits++
        println("$name $ball")
        delay(300L)
        table.send(ball)    // send the ball back
    }
}

// first-in first-out order
// the first coroutine to invoke receive gets the element
private fun main27() = runBlocking {
    val table = Channel<Ball>()
    launch { player("ping", table) }
    launch { player("pong", table) }
    table.send(Ball(0))
    delay(1000L)
    coroutineContext.cancelChildren()
}

// Note that ticker is aware of possible consumer pauses and, by default,
// adjusts next produced element delay if a pause occurs, trying to maintain a fixed rate of produced elements.
// TickerMode.FIXED_DELAY can be specified to maintain a fixed delay between elements
@ObsoleteCoroutinesApi
private fun main28() = runBlocking {
    val tickerChannel = ticker(delayMillis = 100, initialDelayMillis = 0)

    var nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
    println("Initial element is available immediately: $nextElement")   // initial delay hasn't passed yet

    nextElement = withTimeoutOrNull(50) { tickerChannel.receive() } // all subsequent elements has 100ms delay
    println("Next element is not ready in 50ms: $nextElement")

    nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
    println("Next element is ready in 100ms: $nextElement")

    println("Consumer pauses for 150ms")
    delay(170L)

    nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
    println("Next element is available immediately after large consumer delay: $nextElement")

    // Note that the pause between `receive` calls is taken into account and next element arrives faster
    nextElement = withTimeoutOrNull(31) { tickerChannel.receive() }
    println("Next element is ready in 30ms after consumer pause in 170ms: $nextElement")

    tickerChannel.cancel()  // indicate that no more elements are needed
}

private suspend fun doSomethingUsefulOne(): Int {
    delay(1000L)
    return 13
}

private suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L)
    return 29
}

private fun main29() = runBlocking {
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }
    println("Completed in $time ms")
}

private fun main30() = runBlocking {
    val time = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

private fun main31() = runBlocking {
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        // if we omitted start(), which is not the intended use-case for laziness
        // one.start()
        // two.start()
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

// Note that these xxxAsync functions are not suspending functions. They can be used from anywhere.
private fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

private fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

// strongly discouraged
private fun main32() {
    val time = measureTimeMillis {
        // we can initiate async actions outside of a coroutine
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // but waiting for a result must involve either suspending or blocking.
        // here we use `runBlocking { ... }` to block the main thread while waiting for the result
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}

// This way, if something goes wrong inside the code of concurrentSum function and it throws an exception,
// all the coroutines that were launched in its scope are cancelled.
private suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

private fun main33() = runBlocking {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}

private suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE)
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}

private fun main34() = runBlocking<Unit> {
    try {
        failedConcurrentSum()
    } catch (e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}

private fun main35() = runBlocking<Unit> {
    // context of the parent, main runBlocking coroutine
    launch {
        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
    }
    // not confined -- will work with main thread
    launch(Dispatchers.Unconfined) {
        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
    }
    // launch(Dispatchers.Default) { ... } uses the same dispatcher as GlobalScope.launch { ... }
    launch(Dispatchers.Default) {
        println("Default               : I'm working in thread ${Thread.currentThread().name}")
    }
    GlobalScope.launch {
        println("GlobalScope launch    : I'm working in thread ${Thread.currentThread().name}")
    }
    // In a real application it must be either released, when no longer needed,
    // using close function, or stored in a top-level variable and reused throughout the application.
    launch(Executors.newSingleThreadExecutor().asCoroutineDispatcher()) {
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }
}

private fun main36() = runBlocking<Unit> {
    // The Dispatchers.Unconfined coroutine dispatcher starts coroutine in the caller thread, but only until the first suspension point.
    // After suspension it resumes in the thread that is fully determined by the suspending function that was invoked.
    // Unconfined dispatcher is appropriate when coroutine does not consume CPU time nor updates any shared data (like UI) that is confined to a specific thread.
    // By default, a dispatcher for the outer CoroutineScope is inherited, The default dispatcher for runBlocking coroutine
    // So, the coroutine that had inherited context of runBlocking {...} continues to execute in the main thread,
    // while the unconfined one had resumed in the default executor thread that delay function is using.
    // Dispatchers.Unconfined 使用情形:一些操作马上进行，之后不需要专门为他调度协程
    launch(Dispatchers.Unconfined) {
        // not confined -- will work with main thread
        println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
        delay(500L)
        println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
    }
    launch {
        // context of the parent, main runBlocking coroutine
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(1000L)
        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
    }
}

private fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

private fun main37() = runBlocking {
    val a = async {
        log("I'm computing a piece of the answer")
        6
    }
    val b = async {
        log("I'm computing another piece of the answer")
        7
    }
    log("The answer is ${a.await() * b.await()}")
}

@ObsoleteCoroutinesApi
private fun main38() {
    // use:  to release threads that are created with newSingleThreadContext when they are no longer needed
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            // using runBlocking with an explicitly specified context
            runBlocking(ctx1) {
                log("Started in ctx1")
                // using withContext function to change a context of a coroutine
                withContext(ctx2) {
                    log("Working in ctx2")
                }
                log("Back to ctx1")
            }
        }
    }
}

private fun main39() = runBlocking {
    println("My job is ${coroutineContext[Job]} is Active ?: ${coroutineContext[Job]?.isActive == true}")
}

// When a coroutine is launched in the CoroutineScope of another coroutine, it inherits its context via CoroutineScope.coroutineContext
// and the Job of the new coroutine becomes a child of the parent coroutine's job.
// When the parent coroutine is cancelled, all its children are recursively cancelled, too.
// However, when GlobalScope is used to launch a coroutine,
// it is not tied to the scope it was launched from and operates independently.
private fun main40() = runBlocking {
    val job = launch {
        GlobalScope.launch {
            println("job1: I run in GlobalScope and execute independently!")
            delay(1000L)
            println("job1: I am not affected by cancellation of the job")
        }
        // inherits the parent context
        launch {
            delay(100L)
            println("job2: I am a child of the job coroutine")
            delay(1000L)
            println("job2: I will not execute this line if my parent job is cancelled")
        }
    }
    delay(500L)
    job.cancel()    // cancel processing of the job
    delay(1000L)    //delay a second to see what happens
    println("main: Who has survived job cancellation?")
}

// A parent coroutine always waits for completion of all its children.
private fun main41() = runBlocking {
    val job = launch {
        repeat(5) { i ->
            launch {
                delay((i + 1) * 200L) //variable 200ms, 400ms, 600ms
                println("Coroutine $i is done")
            }
        }
        println("job: I'm done and I don't explicitly join my children that are still active")
    }
    // it does not have to use Job.join to wait for them at the end
    job.join()
    println("Now processing of the job is complete")
}

private fun main42() = runBlocking(CoroutineName("main")) {
    log("Started main coroutine")
    // run two background value computations
    val v1 = async(CoroutineName("v1coroutine")) {
        delay(500L)
        log("Computing v1")
        252
    }
    val v2 = async(CoroutineName("v2coroutine")) {
        delay(1000L)
        log("Computing v2")
        6
    }
    log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
}

private fun main43() = runBlocking<Unit> {
    launch(Dispatchers.Default + CoroutineName("test")) {
        println("I'm working in thread ${Thread.currentThread().name}")
    }
}

private class Act : CoroutineScope by CoroutineScope(Dispatchers.Default) {

    fun destroy() {
        cancel()    //Extension on CoroutineScope
    }

    fun doSomething() {
        repeat(10) { i ->
            launch {
                delay((i + 1) * 200L)
                println("Coroutine $i is done")
            }
        }
    }
}

private fun main44() = runBlocking {
    val act = Act()
    act.doSomething()
    println("Launched coroutines")
    delay(1000L)
    println("Destroying activity")
    act.destroy()
    delay(1000L)
}

private val threadLocal = ThreadLocal<String?>()

private fun main45() = runBlocking {
    threadLocal.set("main")
    println("Pre-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    /**
     * For ThreadLocal, asContextElement extension function creates an additional context element,
     * which keeps the value of the given ThreadLocal and restores it every time the coroutine switches its context.
     * When thread-local is mutated, a new value is not propagated to the coroutine caller (as context element cannot track all ThreadLocal object accesses)
     * and updated value is lost on the next suspension. Use withContext to update the value of the thread-local in a coroutine
     * see [asContextElement] for more details.
     * For advanced usage, see [ThreadContextElement]
     */
    val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
        threadLocal.set("11111111111")
        println("Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        yield()
        println("After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        // Checks whether current thread local is present in the coroutine context and throws [IllegalStateException] if it is not.
        threadLocal.ensurePresent()
    }
    // threadLocal.ensurePresent() // throws [IllegalStateException]
    job.join()
    println("Post-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
}

private fun main46() = runBlocking {
    // propagating exceptions automatically (launch and actor), treat exceptions as unhandled, similar to FinalizeEscapeGC's Thread.uncaughtExceptionHandler
    val job = GlobalScope.launch {
        println("Throwing exception from launch")
        throw IndexOutOfBoundsException()   // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
    }
    job.join()
    println("Joined failed job")
    // exposing them to users (async and produce), relying on the user to consume the final exception
    val deferred = GlobalScope.async {
        println("Throwing exception from async")
        throw ArithmeticException()     // Nothing is printed, relying on user to call await
    }
    try {
        deferred.await()
        println("Unreached")
    } catch (e: ArithmeticException) {
        println("Caught ArithmeticException")
    }
}

private fun main47() = runBlocking {
    // CoroutineExceptionHandler is invoked only on exceptions which are not expected to be handled by the user,
    // so registering it in async builder and the like of it has no effect.
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught $throwable")
    }
    val job = GlobalScope.launch(handler) {
        throw AssertionError()
    }
    val deferred = GlobalScope.async(handler) {
        throw ArithmeticException()     // Nothing will be printed, relying on user to call deferred.await()
    }
    joinAll(job, deferred)
}

// CancellationException is ignored by all handlers, which can be obtained by catch block.
private fun main48() = runBlocking {
    val job = launch {
        val child = launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("Child is cancelled")
            }
        }
        yield()
        println("Cancelling child")
        child.cancel()
        child.join()
        yield()
        println("Parent is not cancelled")

    }
    job.join()
}

// If a coroutine encounters exception other than CancellationException, it cancels its parent with that exception.
// The original exception is handled by the parent when all its children terminate.
private fun main49() = runBlocking {
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught $throwable")
    }
    val job = GlobalScope.launch(handler) {
        launch {
            // the first child
            try {
                delay(Long.MAX_VALUE)
            } finally {
                /**
                 * @see main15()
                 */
                withContext(NonCancellable) {
                    println("Children are cancelled, but exception is not handled until all children terminate")
                    delay(100L)
                    println("The first child finished its non cancellable block")
                }
            }
        }
        launch {
            // the second child
            delay(10L)
            println("Second child throws an exception")
            throw ArithmeticException()
        }
    }
    job.join()
}

private fun main50() = runBlocking {
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught $throwable with suppressed ${throwable.suppressed.contentToString()}")
    }
    // "the first exception wins", so the first thrown exception is exposed to the handler.
    // additional exceptions are suppressed
    val job = GlobalScope.launch(handler) {
        launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                throw ArithmeticException()
            }
        }
        launch {
            delay(100)
            throw IOException()
        }
        delay(Long.MAX_VALUE)
    }
    job.join()
}

// Cancellation exceptions are transparent and unwrapped by default
private fun main51() = runBlocking {
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught original $throwable")
    }
    val job = GlobalScope.launch(handler) {
        val inner = launch { launch { launch { throw IOException() } } }
        try {
            inner.join()
        } catch (e: CancellationException) {
            println("Rethrowing CancellationException with original cause")
            throw e
        }
    }
    job.join()
}

private fun main52() = runBlocking {
    val supervisor = SupervisorJob()
    with(CoroutineScope(coroutineContext + supervisor)) {
        val firstChild = launch(CoroutineExceptionHandler { coroutineContext, throwable -> }) {
            println("First child is failing")
            throw AssertionError("First child is cancelled")
        }
        val secondChild = launch {
            firstChild.join()
            // Cancellation of the first child is not propagated to the second child
            println("First child is cancelled: ${firstChild.isCancelled}, but second one is still active")
            try {
                delay(Long.MAX_VALUE)
            } finally {
                // But cancellation of the supervisor is propagated
                println("Second child is cancelled because supervisor is cancelled")
            }
        }
        // wait until the first child fails & completes
        firstChild.join()
        println("Cancelling supervisor")
        supervisor.cancel()
        secondChild.join()
    }
}

private fun main53() = runBlocking {
    try {
        supervisorScope {
            val child = launch {
                try {
                    println("Child is sleeping")
                    delay(Long.MAX_VALUE)
                } finally {
                    println("Child is cancelled")
                }
            }
            // Give our child chance to execute and print using yield
            yield()
            println("Throwing exception from scope")
            throw AssertionError()
        }
    } catch (e: AssertionError) {
        println("Caught assertion error")
    }
}

// supervisor jobs' child's failure is not propagated to the parent
private fun main54() = runBlocking {
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught $throwable")
    }
    supervisorScope {
        val child = launch(handler) {
            println("Child throws an exception")
            throw AssertionError()
        }
        println("Scope is completing")
    }
    println("Scope is completed")
}

@ExperimentalCoroutinesApi
private fun CoroutineScope.fizz() = produce {
    while (true) {
        delay(300L)
        send("Fizz")
    }
}

@ExperimentalCoroutinesApi
private fun CoroutineScope.buzz() = produce {
    while (true) {
        delay(500L)
        send("Buzz")
    }
}

private suspend fun selectFizzBuzz(fizz: ReceiveChannel<String>, buzz: ReceiveChannel<String>) {
    // <Unit> means that this select expression does not produce any result
    select<Unit> {
        fizz.onReceive { value ->
            println("fizz->$value")
        }
        buzz.onReceive { value ->
            println("buzz->$value")
        }
    }
}

// Using receive suspending function we can receive either from one channel or the other.
// But select expression allows us to receive from both simultaneously using its onReceive clauses:
@ExperimentalCoroutinesApi
private fun main55() = runBlocking {
    val fizz = fizz()
    val buzz = buzz()
    repeat(7) {
        selectFizzBuzz(fizz, buzz)
    }
    coroutineContext.cancelChildren()   // cancel fizz & buzz coroutines
}

@ExperimentalCoroutinesApi
private suspend fun selectAorB(a: ReceiveChannel<String>, b: ReceiveChannel<String>): String =
    select {
        a.onReceiveCatching { ret ->
            ret.getOrNull()?.let { "a->$it" } ?: "Channel 'a' is closed"
        }
        b.onReceiveCatching {
            if (it == null) {
                "Channel b is closed"
            } else {
                "b->$it"
            }
        }
    }

// select is biased to the first clause
// onReceiveOrNull gets immediately selected when the channel is already closed.
@ExperimentalCoroutinesApi
private fun main56() = runBlocking {
    val a = produce {
        repeat(4) { send("Hello $it") }
    }
    val b = produce {
        repeat(4) { send("World $it") }
    }
    repeat(8) {
        println(selectAorB(a, b))
    }
    coroutineContext.cancelChildren()
}

@ExperimentalCoroutinesApi
private fun CoroutineScope.produceNumbers(side: SendChannel<Int>) = produce<Int> {
    for (num in 1..10) {
        delay(100L)
        /**
         * see [SendChannel.onSend]
         */
        select<Unit> {
            onSend(num) {}   // Send to primary channel
            side.onSend(num) {}  // or to the side channel
        }
    }
}

// producer of integers that sends its values to a side channel when the consumers on its primary channel cannot keep up with it
@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
private fun main57() = runBlocking {
    val side = Channel<Int>()
    launch {
        // this is a very fast consumer for the side channel
        side.consumeEach {
            println("Side channel has $it")
        }
    }
    produceNumbers(side).consumeEach {
        println("Consuming $it")
        delay(250L)     // let us digest the consumed number properly, do not hurry
    }
    println("Done consuming")
    coroutineContext.cancelChildren()
}

private fun CoroutineScope.asyncString(time: Int) = async {
    delay(time.toLong())
    "Waited for $time ms"
}

private fun CoroutineScope.asyncStringsList(): List<Deferred<String>> {
    val random = Random(4)
    return List(12) { asyncString(random.nextInt(1000)) }
}

private fun main58() = runBlocking {
    val list = asyncStringsList()
    val result = select<String> {
        list.withIndex().forEach { (index, deferred) ->
            deferred.onAwait {
                "Deferred $index produced answer '$it'"
            }
        }
    }
    println(result)
    val countActive = list.count { it.isActive }
    println("$countActive coroutines are still active")
}

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
private fun CoroutineScope.switchMapDeferreds(input: ReceiveChannel<Deferred<String>>) = produce {
    var current = input.receive()     // start with first received deferred value
    while (isActive) {   // loop while not cancelled/closed
        val next = select<Deferred<String>?> {
            // return next deferred value from this select or null
            // 以下两组竞争谁先获取deferred数值
            input.onReceiveCatching {
                it.getOrThrow()
            }
            current.onAwait { value ->
                send(value) // send value that current deferred has produced
                input.receiveOrNull()   // and use the next deferred from the input channel
            }
        }
        if (next == null) {
            println("Channel was closed")
            break   // out of loop
        } else {
            current = next
        }
    }
}

private fun CoroutineScope.asyncString(str: String, time: Long) = async {
    delay(time)
    str
}

private fun main59() = runBlocking {
    val chan = Channel<Deferred<String>>()
    launch {
        for (s in switchMapDeferreds(chan)) {
            println(s)  // println each received string
        }
    }
    chan.send(asyncString("BEGIN", 100))
    delay(200)  // enough time for "BEGIN" to be produced
    chan.send(asyncString("Slow", 500))
    delay(100)  // not enough time to produce slow
    chan.send(asyncString("Replace", 100))
    delay(500)  // give it time before the last one
    chan.send(asyncString("END", 500))
    delay(1000) // give it time to process
    chan.close()    // close the channel...
    delay(500)  // and wait some time to let it finish
}

private suspend fun massiveRun(action: suspend () -> Unit) {
    val time = measureTimeMillis {
        coroutineScope {
            repeat(100) {
                launch {
                    repeat(1000) { action() }
                }
            }
        }
    }
    println("Completed 100000 actions in $time ms")
}

// @Volatile   // Volatiles are of no help and works slower
private var counter = 0

private fun main60() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun { counter++ }
    }
    println("Counter=$counter")
}

private var atomicInteger = AtomicInteger()

private fun main61() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            atomicInteger.incrementAndGet()
        }
    }
    println("Counter=$atomicInteger")
}

private val counterContext = newSingleThreadContext("CounterContext")

// works very slowly because Each individual increment switches from multi-threaded Dispatchers.Default context to
// the single-threaded context using withContext(counterContext) block.
private fun main62() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            // confine each increment to a single-threaded context
            withContext(counterContext) {
                counter++
            }
        }
    }
    println("Counter=$counter")
}

// works much faster
private fun main63() = runBlocking {
    // confine everything to a single-threaded context
    withContext(counterContext) {
        massiveRun {
            counter++
        }
    }
    println("Counter=$counter")
}

private val mutex = Mutex()

private fun main64() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            // protect each increment with lock
            mutex.withLock {
                counter++
            }
        }
    }
    println("Counter=$counter")
}

// Message types for counterActor
private sealed class CounterMsg

private object IncCounter : CounterMsg()  // one-way message to increment counter
private class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()    // a request with reply

// This is function launches a new counter actor
private fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var counter = 0   // actor state
    for (msg in channel) {    // iterate over incoming messages
        when (msg) {
            is IncCounter -> counter++
            is GetCounter -> msg.response.complete(counter)
        }
    }
}

// Indeed, actors may modify their own private state, but can only affect each other through messages (avoiding the need for any locks).
// Actor is more efficient than locking under load, because in this case it always has work to do and it does not have to switch to a different context at all.
// an actor coroutine builder(receive) 和 produce coroutine builder(send) 是一对
private fun main65() = runBlocking<Unit> {
    val counter = counterActor()
    withContext(Dispatchers.Default) {
        massiveRun {
            counter.send(IncCounter)
        }
    }
    // send a message to get a counter value from an actor
    val response = CompletableDeferred<Int>()
    counter.send(GetCounter(response))
    println("Counter=${response.await()}")
    counter.close() // shutdown the actor
}
