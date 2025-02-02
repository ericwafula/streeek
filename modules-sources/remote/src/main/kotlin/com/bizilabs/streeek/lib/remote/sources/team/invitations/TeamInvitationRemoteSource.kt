package com.bizilabs.streeek.lib.remote.sources.team.invitations

import com.bizilabs.streeek.lib.remote.helpers.NetworkResult
import com.bizilabs.streeek.lib.remote.helpers.Supabase
import com.bizilabs.streeek.lib.remote.helpers.asJsonObject
import com.bizilabs.streeek.lib.remote.helpers.safeSupabaseCall
import com.bizilabs.streeek.lib.remote.models.supabase.CreateTeamInvitationDTO
import com.bizilabs.streeek.lib.remote.models.supabase.CreateTeamInvitationRequestDTO
import com.bizilabs.streeek.lib.remote.models.supabase.DeleteTeamInvitationRequestDTO
import com.bizilabs.streeek.lib.remote.models.supabase.GetTeamInvitationsRequestDTO
import com.bizilabs.streeek.lib.remote.models.supabase.TeamInvitationDTO
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

interface TeamInvitationRemoteSource {
    suspend fun createInvitation(
        accountId: Long,
        teamId: Long,
        duration: Long = 86400,
    ): NetworkResult<CreateTeamInvitationDTO>

    suspend fun getInvitations(
        teamId: Long,
        accountId: Long,
    ): NetworkResult<List<TeamInvitationDTO>>

    suspend fun deleteInvitation(
        invitationId: Long,
        accountId: Long,
    ): NetworkResult<Boolean>
}

class TeamInvitationRemoteSourceImpl(
    private val supabase: SupabaseClient,
) : TeamInvitationRemoteSource {
    override suspend fun createInvitation(
        accountId: Long,
        teamId: Long,
        duration: Long,
    ): NetworkResult<CreateTeamInvitationDTO> =
        safeSupabaseCall {
            val parameters =
                CreateTeamInvitationRequestDTO(
                    teamId = teamId,
                    accountId = accountId,
                    duration = duration,
                )
            supabase.postgrest
                .rpc(
                    function = Supabase.Functions.Teams.Invitations.CREATE,
                    parameters = parameters.asJsonObject(),
                ).decodeAs()
        }

    override suspend fun getInvitations(
        teamId: Long,
        accountId: Long,
    ): NetworkResult<List<TeamInvitationDTO>> =
        safeSupabaseCall {
            val parameters =
                GetTeamInvitationsRequestDTO(
                    teamId = teamId,
                    accountId = accountId,
                )
            supabase.postgrest
                .rpc(
                    function = Supabase.Functions.Teams.Invitations.GET,
                    parameters = parameters.asJsonObject(),
                ).decodeList()
        }

    override suspend fun deleteInvitation(
        invitationId: Long,
        accountId: Long,
    ): NetworkResult<Boolean> =
        safeSupabaseCall {
            val parameters =
                DeleteTeamInvitationRequestDTO(
                    invitationId = invitationId,
                    accountId = accountId,
                )
            supabase.postgrest
                .rpc(
                    function = Supabase.Functions.Teams.Invitations.DELETE,
                    parameters = parameters.asJsonObject(),
                )
            true
        }
}
