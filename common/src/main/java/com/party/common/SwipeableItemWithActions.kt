package com.party.common

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeItemWithActions(
    isRevealed: Boolean,
    actions: @Composable RowScope.() -> Unit,
    onExpanded: () -> Unit = {},
    onCollapsed: () -> Unit = {},
    content: @Composable () -> Unit
) {
    // 아이콘 영역의 너비를 명시적으로 설정
    val density = LocalDensity.current
    val contextMenuWidth = with(density) { 80.dp.toPx() } // 120dp → px 변환
    val offset = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = isRevealed) {
        if (isRevealed) {
            offset.animateTo(-contextMenuWidth) // 음수 방향 스와이프
        } else {
            offset.animateTo(0f) // 원래 위치
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .shadow(
                elevation = if(isRevealed) 0.dp else 4.dp,
                shape = RoundedCornerShape(LARGE_CORNER_SIZE), // 둥근 모서리
            )
            .clip(RoundedCornerShape(LARGE_CORNER_SIZE))
    ) {
        Row(
            modifier = Modifier
                .width(80.dp) // 너비 고정
                .fillMaxHeight()
                .padding(start = 8.dp)
                .align(Alignment.CenterEnd) // 오른쪽 끝에 배치
                .clip(RoundedCornerShape(16.dp)) // 테두리 둥글게 (12.dp)
                .background(Color(0XFFFF6262)), // 배경색

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            actions() // 아이콘 표시
        }

        // 2. 움직이는 Surface
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(LARGE_CORNER_SIZE))
                .offset { IntOffset(offset.value.roundToInt(), 0) } // 좌우 이동
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount)
                                    .coerceIn(-contextMenuWidth, 0f) // 음수 범위로 제한
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            if (offset.value <= -contextMenuWidth / 2f) {
                                scope.launch {
                                    offset.animateTo(-contextMenuWidth)
                                    onExpanded()
                                }
                            } else {
                                scope.launch {
                                    offset.animateTo(0f)
                                    onCollapsed()
                                }
                            }
                        }
                    )
                }
        ) {
            content() // 메인 콘텐츠
        }
    }
}

@Composable
fun ActionIcon(
    onClick: () -> Unit,
    backgroundColor: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Color.White
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(backgroundColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}