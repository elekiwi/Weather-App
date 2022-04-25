package com.leonsio.weatherappv2.util

open class Event<out T>(private val data: T) {

    //trigger the event 1 time
    private var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            data
        }
    }

    //return data event if it has been handled
    fun peekContent() = data
}