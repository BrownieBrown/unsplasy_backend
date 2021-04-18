package mbraun.unsplasy.message

data class ResponseFile(
    val name: String,
    val url: String,
    val type: String,
    val size: Long
)