package com.bizilabs.streeek.lib.data.mappers

import com.bizilabs.streeek.lib.domain.helpers.DateFormats
import com.bizilabs.streeek.lib.domain.helpers.SystemLocalDateTime
import com.bizilabs.streeek.lib.domain.helpers.asDate
import com.bizilabs.streeek.lib.domain.helpers.asString
import com.bizilabs.streeek.lib.domain.helpers.datetimeSystem
import com.bizilabs.streeek.lib.domain.models.StreakDomain
import com.bizilabs.streeek.lib.local.models.StreakCache
import com.bizilabs.streeek.lib.remote.models.supabase.StreakDTO

fun StreakDTO.toDomain(): StreakDomain =
    StreakDomain(
        current = currentStreak,
        longest = longestStreak,
        updatedAt =
            updatedAt
                .replaceAfter(".", "")
                .replace(".", "")
                .asDate(format = DateFormats.YYYY_MM_DDTHH_MM_SS)
                ?.datetimeSystem
                ?: SystemLocalDateTime,
    )

fun StreakDomain.toCache(): StreakCache =
    StreakCache(
        current = current,
        longest = longest,
        updatedAt = updatedAt.asString(format = DateFormats.ISO_8601_Z) ?: "",
    )

fun StreakCache.toDomain() =
    StreakDomain(
        current = current,
        longest = longest,
        updatedAt =
            updatedAt.asDate(format = DateFormats.ISO_8601_Z)?.datetimeSystem
                ?: SystemLocalDateTime,
    )
