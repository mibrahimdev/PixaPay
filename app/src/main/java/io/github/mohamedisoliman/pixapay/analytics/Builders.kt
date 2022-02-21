package io.github.mohamedisoliman.pixapay.analytics

import android.content.Context


annotation class DslAnalytics

@DslAnalytics
open class EventBuilder(var name: String? = null) {

    private val data: MutableMap<String, String>? by lazy { mutableMapOf() }

    fun String.to(value: String) {
        data?.let {
            if (!it.containsKey(this)) {
                it[this] = value
            }
        }
    }

    fun put(key: String, value: String): EventBuilder {
        data?.let {
            if (!it.containsKey(key)) {
                it[key] = value
            }
        }
        return this@EventBuilder
    }

    fun data(block: MutableMap<String, String>.() -> Unit) {
        data?.apply { block() }
    }

    fun build(): Trackable = Event.EventTrack(name, data)
}

fun analytics(
    context: Context,
    initializer: AnalyticsBuilder.() -> Unit = {}
): AnalyticsContract = AnalyticsContract.getInstance(context, initializer)

@DslAnalytics
class AnalyticsBuilder(val context: Context) {

    private val trackers = mutableListOf<Tracker>()

    fun kit(tracker: Tracker) = apply {
        trackers += tracker
    }

    fun build(): AnalyticsContract = Analytics(trackers = trackers)
}