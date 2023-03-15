package com.jackson.dynamicfeature

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.jackson.playfeatureresourcesample.DynamicModuleManager

object DynamicModuleManagerImpl: DynamicModuleManager {
    private const val DYNAMIC_MODULE_NAME = "dynamicfeature"

    override fun getIntroRawUri(context: Context): Uri {
        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(context.packageName)
            .appendPath("raw")
            .appendPath(String.format("%s:%s",
                context.resources.getResourcePackageName(R.raw.intro),
                context.resources.getResourceEntryName(R.raw.intro)
            )).build()
    }

    override fun getResourceUri(context: Context, resourceType: String, resourceName: String): Uri {
        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(context.packageName)
            .appendPath(resourceType)
            .appendPath(String.format("%s:%s",
                "${context.packageName}.$DYNAMIC_MODULE_NAME",
                resourceName
            )).build()
        // return Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${BuildConfig.APPLICATION_ID}/$resourceType/${BuildConfig.APPLICATION_ID}.dynamicfeature:$resourceName")
    }
}