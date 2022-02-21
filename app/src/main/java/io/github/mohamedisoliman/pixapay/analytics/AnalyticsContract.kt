package io.github.mohamedisoliman.pixapay.analytics

import android.content.Context

interface AnalyticsContract {

    fun track(setup: EventBuilder.() -> Unit = {})

    companion object : SingletonHolder<AnalyticsContract, Context, AnalyticsBuilder.() -> Unit>(
        { context: Context, builder: AnalyticsBuilder.() -> Unit ->
            AnalyticsBuilder(context).apply(builder).build()
        }
    )


}

internal class Analytics(private val trackers: MutableList<Tracker>) : AnalyticsContract {
    init {
        trackers.forEach {
            if (it.isAnalyticsEnabled()) {
                it.start()
            }
        }
    }

    override fun track(setup: EventBuilder.() -> Unit) {
        val eventBuilder = EventBuilder()
        eventBuilder.setup()
        val event = eventBuilder.build()

        event.let {
            trackers.forEach { tracker ->
                tracker.trackEvent(it)
            }
        }
    }
}