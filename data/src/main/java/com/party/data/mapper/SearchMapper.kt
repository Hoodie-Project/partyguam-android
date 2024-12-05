package com.party.data.mapper

import com.party.data.dto.search.PartyDto
import com.party.data.dto.search.PartyTypeDto
import com.party.data.dto.search.PositionDto
import com.party.data.dto.search.SearchDto
import com.party.data.dto.search.SearchedPartyDataDto
import com.party.data.dto.search.SearchedPartyDto
import com.party.data.dto.search.SearchedPartyRecruitmentDto
import com.party.data.dto.search.SearchedRecruitmentDataDto
import com.party.data.util.convertToImageUrl
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
            image = convertToImageUrl(searchedPartyDataDto.image),
            status = searchedPartyDataDto.status,
            createdAt = searchedPartyDataDto.createdAt,
            updatedAt = searchedPartyDataDto.updatedAt,
            recruitmentCount = searchedPartyDataDto.recruitmentCount
        )
    }

    private fun mapperSearchedRecruitmentData(searchedRecruitmentDataDto: SearchedRecruitmentDataDto): SearchedRecruitmentData{
        return SearchedRecruitmentData(
            id = searchedRecruitmentDataDto.id,
            content = searchedRecruitmentDataDto.content,
            recruitingCount = searchedRecruitmentDataDto.recruitingCount,
            recruitedCount = searchedRecruitmentDataDto.recruitedCount,
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
            id = partyDto.id,
            title = partyDto.title,
            image = convertToImageUrl(partyDto.image),
            partyType = mapperPartyType(partyDto.partyType)
        )
    }
}