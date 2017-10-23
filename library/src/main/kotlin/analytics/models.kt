package analytics

data class Event(
    val name: String,
    val context: Map<String, Any> = emptyMap()
)
