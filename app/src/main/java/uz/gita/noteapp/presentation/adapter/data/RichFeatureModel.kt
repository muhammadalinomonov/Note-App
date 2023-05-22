package uz.gita.noteapp.presentation.adapter.data

data class RichFeatureModel(
    val id:Int,
    val type:RichFeatureType,
    var isEnabled:Boolean = false,
    val image:Int
)
enum class RichFeatureType{
    BOLD,
    ITALIC,
    SUBSCRIPT,
    SUPERSCRIPT,
    STRIKETHROUGH,
    UNDERLINE,
    H1,
    H2,
    H3,
    H4,
    H5,
    H6,
    JUSTIFYCENTER,
    JUSTIFYLEFT,
    JUSTIFYRIGHT
}