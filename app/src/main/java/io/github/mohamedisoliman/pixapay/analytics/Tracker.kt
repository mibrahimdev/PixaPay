package io.github.mohamedisoliman.pixapay.analytics

interface Tracker {

    fun isAnalyticsEnabled(): Boolean

    fun enableAnalytics(enabled: Boolean)

    fun logLevel(logLevel: Int)

    fun start()

    fun apiKey(): String

    fun trackEvent(eventTrack: Trackable)

}

interface Trackable {
    val eventName: String?
    val properties: Map<String, String>?
}

sealed class Event : Trackable {

    data class EventTrack(
        override val eventName: String?,
        override val properties: Map<String, String>? = null,
    ) : Event() {
        companion object
    }
}