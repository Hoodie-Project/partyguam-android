package com.party.data.mapper

import com.party.data.entity.search.PartyDto
import com.party.data.entity.search.PartyTypeDto
import com.party.data.entity.search.PositionDto
import com.party.data.entity.search.SearchDto
import com.party.data.entity.search.SearchedPartyDataDto
import com.party.data.entity.search.SearchedPartyDto
import com.party.data.entity.search.SearchedPartyRecruitmentDto
import com.party.data.entity.search.SearchedRecruitmentDataDto
import com.party.domain.model.search.Party
import com.party.domain.model.search.PartyType
import com.party.domain.model.search.Position
import com.party.domain.model.search.Search
import com.party.domain.model.search.SearchedParty
import com.party.domain.model.search.SearchedPartyData
import com.party.domain.model.search.SearchedPartyRecruitment
import com.party.domain.model.search.SearchedRecruitmentData

object SearchMapper {
    fun mapperSearch(searchDto: SearchDto): Search{
        return Search(
            party = mapperSearchParty(searchDto.party),
            partyRecruitment = mapperSearchRecruitment(searchDto.partyRecruitment)
        )
    }

    private fun mapperSearchParty(searchedPartyDto: SearchedPartyDto): SearchedParty{
        return SearchedParty(
            total = searchedPartyDto.total,
            parties = searchedPartyDto.parties.map { mapperSearchedPartyData(it) }
        )
    }

    private fun mapperSearchRecruitment(searchedRecruitment: SearchedPartyRecruitmentDto): SearchedPartyRecruitment{
        return SearchedPartyRecruitment(
            total = searchedRecruitment.total,
            partyRecruitments = searchedRecruitment.partyRecruitments.map { mapperSearchedRecruitmentData(it) }
        )
    }

    private fun mapperSearchedPartyData(searchedPartyDataDto: SearchedPartyDataDto): SearchedPartyData{
        return SearchedPartyData(
            id = searchedPartyDataDto.id,
            partyType = mapperPartyType(searchedPartyDataDto.partyType),
            tag = searchedPartyDataDto.tag,
            title = searchedPartyDataDto.title,
            content = searchedPartyDataDto.content,
            image = searchedPartyDataDto.image,
            status = searchedPartyDataDto.status,
            createdAt = searchedPartyDataDto.createdAt,
            updatedAt = searchedPartyDataDto.updatedAt,
            recruitmentCount = searchedPartyDataDto.recruitmentCount
        )
    }

    private fun mapperSearchedRecruitmentData(searchedRecruitmentDataDto: SearchedRecruitmentDataDto): SearchedRecruitmentData{
        return SearchedRecruitmentData(
            partyRecruitmentId = searchedRecruitmentDataDto.partyRecruitmentId,
            main = searchedRecruitmentDataDto.main,
            sub = searchedRecruitmentDataDto.sub,
            content = searchedRecruitmentDataDto.content,
            recruitingCount = searchedRecruitmentDataDto.recruitingCount,
            recruitedCount = searchedRecruitmentDataDto.recruitedCount,
            applicationCount = searchedRecruitmentDataDto.applicationCount,
            createdAt = searchedRecruitmentDataDto.createdAt,
            party = mapperParty(searchedRecruitmentDataDto.party),
            position = mapperPositionDto(searchedRecruitmentDataDto.position)
        )
    }

    private fun mapperPositionDto(positionDto: PositionDto): Position{
        return Position(
            id = positionDto.id,
            main = positionDto.main,
            sub = positionDto.sub
        )
    }

    private fun mapperPartyType(partyTypeDto: PartyTypeDto): PartyType{
        return PartyType(
            id = partyTypeDto.id,
            type = partyTypeDto.type
        )
    }

    private fun mapperParty(partyDto: PartyDto): Party{
        return Party(
            title = partyDto.title,
            image = partyDto.image,
            partyType = mapperPartyType(partyDto.partyType)
        )
    }
}