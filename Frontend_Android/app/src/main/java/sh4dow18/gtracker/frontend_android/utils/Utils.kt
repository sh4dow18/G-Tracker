package sh4dow18.gtracker.frontend_android.utils

import com.google.gson.JsonObject
import com.google.gson.JsonParser

fun getErrorMessage(errorBody: String?): String {
    val jsonError: JsonObject = JsonParser.parseString(errorBody).asJsonObject
    return jsonError.getAsJsonPrimitive("debugMessage")?.asString ?: jsonError.getAsJsonPrimitive("detail")?.asString ?: "Server Unavailable"
}