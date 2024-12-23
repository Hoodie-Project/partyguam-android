package com.party.presentation.screen.manage_applicant.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.component.RecruitmentListItem3
import com.party.common.component.RecruitmentListItem6
import com.party.common.component.no_data.NoDataColumn
import com.party.presentation.enum.RecruitmentStatusType
import com.party.presentation.screen.manage_applicant.ManageApplicantState

@Composable
fun ManageApplicantListArea(
    manageApplicantState: ManageApplicantState,
    onRefusal: (Int) -> Unit,
    onAccept: (Int) -> Unit,
) {
    val filteredList = if(manageApplicantState.selectedRecruitmentStatus == "전체"){
        manageApplicantState.recruitmentApplicantList
    } else {
        manageApplicantState.recruitmentApplicantList.filter {
            it.status == RecruitmentStatusType.fromDisplayText(manageApplicantState.selectedRecruitmentStatus)
        }
    }

    if(filteredList.isEmpty()){
        NoDataColumn(
            title = "지원자가 없어요",
            modifier = Modifier.padding(top = 50.dp),
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(
                items = filteredList,
                key = { index, _ ->
                    index
                },
            ) { _, item ->
                RecruitmentListItem6(
                    date = item.createdAt,
                    status = RecruitmentStatusType.fromStatus(item.status).toDisplayText(),
                    statusColor = RecruitmentStatusType.fromStatus(item.status).toColor(),
                    profileImage = item.user.image,
                    nickName = item.user.nickname,
                    message = item.message,
                    onRefusal = { onRefusal(item.id) },
                    onAccept = { onAccept(item.id) },
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ManageApplicantListAreaPreview() {
    ManageApplicantListArea(
        manageApplicantState = ManageApplicantState(),
        onRefusal = {},
        onAccept = {},
    )
}