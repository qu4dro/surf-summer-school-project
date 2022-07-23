package orlov.surf.summer.school.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

// Сработает для правильного ответа сервера в виде +71234567890
fun String.formatToPhone(): String {
    return if (this.length != 12) ""
    else {
        String.format("%s (%s) %s %s %s", this.substring(0, 2), this.substring(2,5), this.substring(5,8), this.substring(8, 10), this.substring(10,12))
    }
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun Long.formatDate(): String {
    val outputFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    val dateFormatted = outputFormat.format(this)
    return dateFormatted
}