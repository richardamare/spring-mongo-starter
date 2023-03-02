package com.amarerichard.springmongostarter.model.response

import java.time.LocalDateTime

data class ErrorResponse(
    val code: Int,
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
) {
    private fun toMap() = mapOf(
        "code" to code,
        "message" to message,
        "timestamp" to timestamp.toString(),
    )

    fun toJson(): String {
        val json = StringBuilder()
        json.append("{")
        toMap().forEach { (key, value) ->
            if (value is String) json.append("\"$key\": \"$value\",")
            else
                json.append("\"$key\": $value,")
        }
        json.append("}")
        return json.toString()
    }
}