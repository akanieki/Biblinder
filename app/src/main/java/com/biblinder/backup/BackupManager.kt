package com.biblinder.backup

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.OutputStream
import java.io.InputStream
import com.biblinder.data.local.AnimeEntity

object BackupManager {

    suspend fun exportToJson(context: Context, data: List<AnimeEntity>, output: OutputStream) {
        output.use { it.write(Json.encodeToString(data).toByteArray()) }
    }

    suspend fun importFromJson(input: InputStream): List<AnimeEntity> {
        val text = input.bufferedReader().readText()
        return Json.decodeFromString(text)
    }
}
