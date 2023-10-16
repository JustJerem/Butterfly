package com.jeremieguillot.butterfly.data.network.util

import com.jeremieguillot.butterfly.BuildConfig

class PockethostHelper {

    companion object {
        fun getFilePath(collection: String, id: String, filename: String): String {
            return "${BuildConfig.API_BASE_URL}/files/$collection/$id/$filename"
        }
    }
}