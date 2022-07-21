package orlov.surf.summer.school.ui.profile

// Сработает для правильного ответа сервера в виде +71234567890
fun String.formatToPhone(): String {
    return if (this.length != 12) ""
    else {
        String.format("%s (%s) %s %s %s", this.substring(0, 2), this.substring(2,5), this.substring(5,8), this.substring(8, 10), this.substring(10,12))
    }
}