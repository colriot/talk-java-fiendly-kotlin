@file:JvmName("Model")

package analytics

data class Event @JvmOverloads constructor(val name: String, val context: Map<String, Any> = emptyMap())


const val EVENT_NAME = "event_name"
const val USER_ID = "user_id"
