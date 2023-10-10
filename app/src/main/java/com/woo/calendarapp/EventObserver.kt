package com.woo.calendarapp

import androidx.lifecycle.Observer
import com.woo.calendarapp.viewmodel.MainViewModel

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) :
    Observer<EventObserver.Event<T>> {
    override fun onChanged(event:Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }

    open class Event<out T>(private val content: T) {
        var hasBeenHandled = false
            private set

        fun getContentIfNotHandled(): T? {
            return if (hasBeenHandled) { // 이벤트가 이미 처리 되었다면
                null // null을 반환하고,
            } else { // 그렇지 않다면
                hasBeenHandled = true // 이벤트가 처리되었다고 표시한 후에
                content // 값을 반환합니다.
            }
        }

    }

}
