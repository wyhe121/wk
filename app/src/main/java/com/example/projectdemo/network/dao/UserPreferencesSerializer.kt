package com.example.projectdemo.network.dao

import androidx.datastore.core.Serializer
import com.example.projectdemo.protobuf.UserPrefs
import java.io.InputStream
import java.io.OutputStream

/**
 * 作者: CQ
 * 日期: 2021-03-31
 * 说明: 序列化
 */
object UserPreferencesSerializer: Serializer<UserPrefs.UserPreferences> {
    override val defaultValue: UserPrefs.UserPreferences
        get() = UserPrefs.UserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPrefs.UserPreferences {
        return UserPrefs.UserPreferences.parseFrom(input)
    }

    override suspend fun writeTo(t: UserPrefs.UserPreferences, output: OutputStream) {
        t.writeTo(output)
    }
}
