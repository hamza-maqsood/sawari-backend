package extensions

fun String?.ensureNotNull() {
    if (this == null) throw NullPointerException()
}

fun String?.ensureNeitherNullNorBlank() {
    if (this.isNullOrBlank()) throw NullPointerException()
}