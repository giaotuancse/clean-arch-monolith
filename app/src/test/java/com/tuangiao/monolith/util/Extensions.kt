
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun MockWebServer.enqueueResponse(response: String, code: Int) {
    enqueue(
        MockResponse()
            .setResponseCode(code)
            .setBody(response)
    )
}


/**
 * Don't even ask about the runCurrent() part.
 * Forget everything you knew about coroutine testing before.
 * We were apes playing with mud it seems.
 * This change happened in coroutine testing 1.6.
 *
 * https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-test/MIGRATION.md#replace-advancetimebyn-with-advancetimebyn-runcurrent
 *
 * Why is it needed ? *shrugs*
 */
@ExperimentalCoroutinesApi
fun TestScope.advanceTimeByAndRun(delayTimeMillis: Long) {
    testScheduler.advanceTimeBy(delayTimeMillis)
    runCurrent()
}

/**
 * Observes a [LiveData] until the `block` is done executing.
 */
fun <T> LiveData<T>.observeForTesting(block: (LiveData<T>) -> Unit) {
    val observer = Observer<T> { }
    try {
        observeForever(observer)
        block(this)
    } finally {
        removeObserver(observer)
    }
}