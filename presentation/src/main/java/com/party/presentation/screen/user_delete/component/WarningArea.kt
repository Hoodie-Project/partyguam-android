package com.party.presentation.screen.user_delete.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.WidthSpacer
import com.party.common.utils.calculateLetterSpacing
import com.party.guam.design.B2
import com.party.guam.design.T3

@Composable
fun WarningArea() {
    Column {
        DescriptionText(Description.WARNING)
        HeightSpacer(heightDp = 40.dp)
        DescriptionText(Description.BACKUP)
        HeightSpacer(heightDp = 40.dp)
        DescriptionText(Description.PARTY_RECORD)
    }
}

@Composable
private fun DescriptionText(description: Description) {
    Column {
        WarningAreaItemTitle(
            text = description.title
        )
        HeightSpacer(heightDp = 12.dp)
        description.descriptions.forEach { (descText, boldTexts) ->
            val annotatedString = buildAnnotatedString {
                append(descText) // Description 텍스트 추가

                boldTexts.forEach { boldText ->
                    val startIndex = descText.indexOf(boldText)
                    val endIndex = startIndex + boldText.length

                    if (startIndex >= 0) {
                        addStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold),
                            start = startIndex,
                            end = endIndex
                        )
                    }
                }
            }

            Row {
                WidthSpacer(widthDp = 10.dp)
                Text(text = "·")
                WidthSpacer(widthDp = 4.dp)
                Text(
                    text = annotatedString,
                    fontSize = B2,
                    modifier = Modifier.padding(bottom = 8.dp),  // 각 설명 간 간격 추가
                    letterSpacing = calculateLetterSpacing(B2, (-2.5f))
                )
            }
        }
    }
}

@Composable
private fun WarningAreaItemTitle(
    text: String,
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        fontSize = T3,
        fontWeight = FontWeight.Bold,
        letterSpacing = calculateLetterSpacing(T3, (-2.5f))
    )
}

@Preview(showBackground = true)
@Composable
private fun WarningAreaPreview() {
    WarningArea()
}

enum class Description(
    val title: String,
    val descriptions: List<Pair<String, List<String>>> // Description과 BoldTexts의 연결
) {
    WARNING(
        title = "회원 탈퇴 전 유의사항",
        descriptions = listOf(
            "회원 탈퇴 시, 소셜 로그인 계정을 통해 등록된 모든 정보가 영구적으로 삭제되며, 데이터 복구가 불가능합니다." to listOf("영구적으로 삭제", "데이터 복구"),
            "회원 탈퇴 후에는 진행 중인 모든 지원과 파티 참여가 자동으로 취소되며, 이에 따른 혜택이나 보상을 받을 수 없습니다." to listOf("모든 지원과 파티 참여가 자동으로 취소", "혜택이나 보상")
        )
    ),
    BACKUP(
        title = "데이터 백업",
        descriptions = listOf(
            "회원 탈퇴 전에 소셜 로그인 계정과 연결된 데이터를 반드시 백업하세요. 탈퇴 후에는 데이터를 복구하거나 접근할 수 없습니다." to listOf("반드시 백업"),
        )
    ),
    PARTY_RECORD(
        title = "파티 맟 참여 기록 관리",
        descriptions = listOf(
            "회원 탈퇴 후에도 서비스의 안정성과 다른 사용자들의 활동 이력을 보장하기 위해 파티 내 파티 참여 기록은 남아있을 수 있습니다\n그러나, 이 기록은 귀하의 개인정보를 포함하지 않으며, 탈퇴 후에는 익명 처리됩니다.." to listOf(""),
            "회원 탈퇴 후 동일 계정으로 재가입하더라도 이전에 참여했던 파티 및 기록은 복구되지 않습니다." to listOf(""),
        )
    )
}