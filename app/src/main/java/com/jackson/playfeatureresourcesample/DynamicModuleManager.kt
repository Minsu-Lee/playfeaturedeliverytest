package com.jackson.playfeatureresourcesample

import android.content.Context
import android.net.Uri
import android.util.Log

interface DynamicModuleManager {
    companion object {
        private var _manager: DynamicModuleManager? = null

        fun getInstance(): DynamicModuleManager? {
            return try {
                _manager ?: (Class.forName("${MyApplication.FEATURE_PACKAGE}.DynamicModuleManagerImpl")
                    .kotlin.objectInstance as? DynamicModuleManager)?.also { _manager = it }
            } catch (e: Exception) {
                e.printStackTrace()
                _manager = null
                null
            }.also {
                Log.e("DynamicModuleManager", "DynamicModuleManager is ${if(it == null) "Null" else "Not Null"}")
            }
        }
    }

    fun getIntroRawUri(context: Context): Uri

    fun getResourceUri(context: Context, resourceType: String, resourceName: String): Uri
}