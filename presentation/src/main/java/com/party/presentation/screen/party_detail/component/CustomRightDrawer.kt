package com.party.presentation.screen.party_detail.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.component.icon.DrawableIconButton
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.WHITE
import kotlinx.coroutines.launch

const val DURATION = 300

@Composable
fun CustomRightDrawer(
    drawerState: DrawerState,
    onGotoPartyEdit: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val twoThirdsWidth = maxWidth * 2 / 3
        val oneThirdsWidth = maxWidth * 1 / 3

        var dragOffset by remember { mutableFloatStateOf(0f) }

        // Drawer 애니메이션 상태
        val animatedOffset by animateDpAsState(
            targetValue = if (drawerState.isOpen) dragOffset.dp else maxWidth,
            animationSpec = tween(durationMillis = DURATION),
            label = ""
        )

        // 스와이프 제스처 감지
        val swipeGesture = Modifier.pointerInput(Unit) {
            detectHorizontalDragGestures(
                onDragEnd = {
                    // 드래그 종료 시 Drawer를 닫거나 원래 위치로 복귀
                    if (dragOffset > twoThirdsWidth.value * 0.5f) {
                        scope.launch {
                            drawerState.close()
                            dragOffset = 0f
                        }
                    } else {
                        dragOffset = 0f // Drawer를 원래 위치로 복귀
                    }
                },
                onHorizontalDrag = { change, dragAmount ->
                    change.consume()
                    dragOffset = (dragOffset + dragAmount)
                        .coerceIn(0f, maxWidth.value) // 드래그 범위를 제한
                }
            )
        }

        // 전체 화면 영역
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = animatedOffset)
                    .then(swipeGesture)
            ) {
                // 1/3 까만 투명 영역 (닫기 영역)
                Box(
                    modifier = Modifier
                        .width(oneThirdsWidth)
                        .fillMaxHeight()
                        .background(if(dragOffset != 0f || !drawerState.isOpen) Color.Transparent  else BLACK.copy(alpha = 0.7f))
                        .clickable {
                            scope.launch {
                                drawerState.close()
                                dragOffset = 0f
                            }
                        }
                )

                // 2/3 Drawer 영역
                Column(
                    modifier = Modifier
                        .width(twoThirdsWidth)
                        .fillMaxHeight()
                        .background(WHITE)
                ) {
                    HeightSpacer(heightDp = 60.dp)

                    DrawerContentArea(
                        onGotoPartyEdit = {
                            scope.launch {
                                // Drawer 닫힘 애니메이션이 완료될 때까지 기다림
                                drawerState.close()
                                // 드래그 오프셋 초기화
                                drawerState.snapTo(DrawerValue.Closed)
                                // 애니메이션 완료 후 다음 동작 실행

                                onGotoPartyEdit()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DrawerContentArea(
    onGotoPartyEdit: () -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp)
    ){
        ManageParty(
            onGotoPartyEdit = onGotoPartyEdit
        )
        HeightSpacer(heightDp = 40.dp)
        ManageRecruitment()
    }

}

@Composable
private fun ManageParty(
    onGotoPartyEdit: () -> Unit,
) {
    DrawerTitle(title = "파티 관리")
    DrawerItem(
        text = "파티 수정",
        onClick = onGotoPartyEdit
    )
    DrawerItem(
        text = "파티원 관리",
        onClick = {}
    )
}

@Composable
private fun ManageRecruitment() {
    DrawerTitle(title = "모집 관리")
    DrawerItem(
        text = "모집 편집",
        onClick = {}
    )
    DrawerItem(
        text = "지원자 관리",
        onClick = {}
    )
}

@Composable
private fun DrawerTitle(
    title: String,
) {
    TextComponent(
        modifier = Modifier
            .height(42.dp),
        text = title,
        fontSize = T2,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun DrawerItem(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .noRippleClickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            text = text,
            fontSize = B1,
            fontWeight = FontWeight.SemiBold,
            textColor = GRAY400,
        )

        DrawableIconButton(
            icon = painterResource(id = R.drawable.arrow_right_icon),
            contentDescription = "Arrow Right Icon",
            iconSize = 20.dp,
            iconColor = GRAY400,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomRightDrawerPreview() {
    CustomRightDrawer(
        drawerState = DrawerState(
            initialValue = DrawerValue.Open,
        ),
        onGotoPartyEdit = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun CustomRightDrawerPreview2() {
    CustomRightDrawer(
        drawerState = DrawerState(
            initialValue = DrawerValue.Closed,
        ),
        onGotoPartyEdit = {}
    )
}