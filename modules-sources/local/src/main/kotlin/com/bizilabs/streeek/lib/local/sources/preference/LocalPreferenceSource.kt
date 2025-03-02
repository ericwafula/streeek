package com.bizilabs.streeek.lib.local.sources.preference

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

interface LocalPreferenceSource {
    val typography: Flow<String>
    val isSyncingContributions: Flow<Boolean>
    val hasNetworkConnection: Flow<Boolean>
    val userHasOnBoarded: Flow<Boolean>

    suspend fun setIsSyncingContributions(isSyncing: Boolean)

    suspend fun updateTypography(typography: String)

    suspend fun updateNetworkConnection(hasNetworkConnection: Boolean)

    suspend fun updateUserHasOnBoarded(hasOnBoarded: Boolean)
}

class LocalPreferenceSourceImpl(
    val source: PreferenceSource,
) : LocalPreferenceSource {
    object Keys {
        val SyncingContributions = booleanPreferencesKey("syncing_contributions")
        val typography = stringPreferencesKey("streeek.typography")
        val hasNetworkConnection = booleanPreferencesKey("streeek.network.connection")
        val userHasOnBoarded = booleanPreferencesKey("streeek.onboarding")
    }

    override val typography: Flow<String>
        get() = source.get(key = Keys.typography, default = "MONO")

    override val isSyncingContributions: Flow<Boolean>
        get() = source.get(key = Keys.SyncingContributions, default = false)

    override val hasNetworkConnection: Flow<Boolean>
        get() = source.get(key = Keys.hasNetworkConnection, default = true)

    override val userHasOnBoarded: Flow<Boolean>
        get() = source.get(key = Keys.userHasOnBoarded, default = false)

    override suspend fun setIsSyncingContributions(isSyncing: Boolean) {
        source.update(key = Keys.SyncingContributions, value = isSyncing)
    }

    override suspend fun updateTypography(typography: String) {
        source.update(key = Keys.typography, value = typography)
    }

    override suspend fun updateNetworkConnection(hasNetworkConnection: Boolean) {
        source.update(key = Keys.hasNetworkConnection, value = hasNetworkConnection)
    }

    override suspend fun updateUserHasOnBoarded(hasOnBoarded: Boolean) {
        source.update(key = Keys.userHasOnBoarded, value = hasOnBoarded)
    }
}
