package uz.gita.noteapp.utils

fun <T> T. myApply(block: T.() -> Unit) {
    block(this)
}
