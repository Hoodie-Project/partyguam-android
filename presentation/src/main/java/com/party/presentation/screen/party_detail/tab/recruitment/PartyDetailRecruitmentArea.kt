package com.party.presentation.screen.party_detail.tab.recruitment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.utils.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.DARK100
import com.party.common.ui.theme.GRAY500
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.Position1
import com.party.domain.model.user.PartyAuthority
import com.party.domain.model.user.PartyAuthorityPosition
import com.party.presentation.screen.party_detail.PartyDetailState
import com.party.presentation.screen.party_detail.component.PartyDetailTitleArea
import com.party.presentation.screen.party_detail.tab.recruitment.component.PartyDetailRecruitmentFilterArea
import com.party.presentation.screen.party_detail.tab.recruitment.component.PartyDetailRecruitmentListArea

@Composable
fun PartyDetailRecruitmentArea(
    state: PartyDetailState,
    onPositionClick: (String) -> Unit,
    onReset: () -> Unit,
    onApply: () -> Unit,
    onShowPositionFilter: (Boolean) -> Unit,
    onAddRecruitment: () -> Unit,
    onChangeOrderBy: (Boolean) -> Unit,
    onChangeProgress: (Boolean) -> Unit,
    onClickRecruitment: (Int) -> Unit,
) {
    PartyDetailRecruitmentAreaContent(
        state = state,
        authority = state.partyAuthority,
        list = state.partyRecruitment,
        selectedCreateDataOrderByDesc = state.selectedOrderBy,
        onChangeSelected = { onChangeOrderBy(!state.selectedOrderBy) },
        onPositionClick = onPositionClick,
        onReset = onReset,
        onApply = onApply,
        onAddRecruitment = onAddRecruitment,
        onShowPositionFilter = onShowPositionFilter,
        onChangeProgress = onChangeProgress,
        onClickRecruitment = onClickRecruitment
    )
}

@Composable
fun PartyDetailRecruitmentAreaContent(
    state: PartyDetailState,
    list: List<PartyRecruitment>,
    authority: PartyAuthority,
    selectedCreateDataOrderByDesc: Boolean,
    onChangeSelected: (Boolean) -> Unit,
    onPositionClick: (String) -> Unit,
    onReset: () -> Unit,
    onApply: () -> Unit,
    onShowPositionFilter: (Boolean) -> Unit,
    onAddRecruitment: () -> Unit,
    onChangeProgress: (Boolean) -> Unit,
    onClickRecruitment: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        PartyDetailTitleArea(
            title = "모집공고",
            number = "${list.size}",
            progressContent = {
                ChangeProgress(
                    isProgress = state.isProgress,
                    onChangeProgress = onChangeProgress
                )
            }
        )
        HeightSpacer(heightDp = 16.dp)
        PartyDetailRecruitmentFilterArea(
            state = state,
            selectedCreateDataOrderByDesc = selectedCreateDataOrderByDesc,
            onChangeSelected = onChangeSelected,
            onShowPositionFilter = onShowPositionFilter,
            onPositionClick = onPositionClick,
            onReset = onReset,
            onApply = onApply,
        )
        HeightSpacer(heightDp = 8.dp)
        PartyDetailRecruitmentListArea(
            authority = authority,
            list = list,
            onAddRecruitment = onAddRecruitment,
            onClickRecruitment = onClickRecruitment
        )
    }
}

@Composable
private fun ChangeProgress(
    isProgress: Boolean,
    onChangeProgress: (Boolean) -> Unit,
) {
    Row {
        TextComponent(
            text = "진행중",
            fontSize = B2,
            textColor = if(isProgress) DARK100 else GRAY500,
            fontWeight = FontWeight.SemiBold,
        )
        WidthSpacer(widthDp = 2.dp)
        Image(
            painter = if(isProgress) painterResource(id = R.drawable.icon_toggle_on) else painterResource(id = R.drawable.icon_toggle_off),
            contentDescription = "toggle",
            modifier = Modifier
                .size(24.dp)
                .noRippleClickable { onChangeProgress(!isProgress) }
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun PartyDetailRecruitmentAreaContentPreview() {
    PartyDetailRecruitmentAreaContent(
        list = listOf(
            PartyRecruitment(
                id = 2293,
                status = "active",
                position = Position1(
                    main = "개발자",
                    sub = "안드로이드"
                ),
                content = "error",
                recruitingCount = 3,
                recruitedCount = 2,
                applicationCount = 1,
                createdAt = "2024-10-25T21:38:28.850Z",
            ),
            PartyRecruitment(
                id = 2293,
                status = "active",
                position = Position1(
                    main = "개발자",
                    sub = "안드로이드"
                ),
                content = "error",
                recruitingCount = 3,
                recruitedCount = 2,
                applicationCount = 1,
                createdAt = "2024-10-25T21:38:28.850Z",
            )
        ),
        selectedCreateDataOrderByDesc = true,
        onChangeSelected = {},
        onReset = {},
        onApply = {},
        authority = PartyAuthority(
            id = 0,
            authority = "master",
            position = PartyAuthorityPosition(
                id = 0,
                main = "개발자",
                sub = "안드로이드"
            )
        ),
        onAddRecruitment = {},
        onShowPositionFilter = {},
        state = PartyDetailState(),
        onPositionClick = {},
        onChangeProgress = {},
        onClickRecruitment = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyDetailRecruitmentAreaContentPreview1() {
    PartyDetailRecruitmentAreaContent(
        list = listOf(

        ),
        selectedCreateDataOrderByDesc = true,
        onChangeSelected = {},
        onReset = {},
        onApply = {},
        authority = PartyAuthority(
            id = 0,
            authority = "master",
            position = PartyAuthorityPosition(
                id = 0,
                main = "개발자",
                sub = "안드로이드"
            )
        ),
        onAddRecruitment = {},
        onShowPositionFilter = {},
        state = PartyDetailState(),
        onPositionClick = {},
        onChangeProgress = {},
        onClickRecruitment = {},
    )
}