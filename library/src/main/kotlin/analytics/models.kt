package analytics

data class Event @JvmOverloads constructor(
    val name: String,
    val context: Map<String, Any> = emptyMap()
)
