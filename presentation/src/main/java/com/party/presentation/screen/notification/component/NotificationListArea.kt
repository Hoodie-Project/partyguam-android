package com.party.presentation.screen.notification.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.NetworkImageLoad
import com.party.common.component.icon.DrawableIconButton
import com.party.common.convertIsoToCustomDateFormat
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.notification.Notification

@Composable
fun NotificationListArea(
    notification: Notification,
    onClickNotificationItem: (Int) -> Unit,
) {
    HeightSpacer(heightDp = 16.dp)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(
            items = notification.notifications,
            key = { index, _ ->
                index
            },
        ){ _, item ->
            NotificationListAreaItem(
                onClickNotificationItem = { onClickNotificationItem(item.id) },
                imageUrl = item.image ?: "",
                label = item.notificationType.label,
                title = item.title,
                content = item.message,
                time = item.createdAt,
                isRead = item.isRead,
            )
        }
    }
}

@Composable
private fun NotificationListAreaItem(
    onClickNotificationItem: () -> Unit,
    imageUrl: String,
    label: String,
    title: String,
    content: String,
    time: String,
    isRead: Boolean,
) {
    val borderColor = if(isRead) GRAY100 else PRIMARY

    Card(
        onClick = onClickNotificationItem,
        modifier = Modifier
            .width(335.dp)
            .height(144.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(20.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier
                    .width(295.dp),
            ) {
                NotificationListAreaItemTop(
                    onClickNotificationItem = onClickNotificationItem,
                    imageUrl = imageUrl,
                    label = label,
                    title = title,
                    content = content,
                    isRead = isRead,
                )
                NotificationListAreaItemBottom(
                    onClickNotificationItem = onClickNotificationItem,
                    time = time,
                    textColor = RED,
                )
            }
        }
    }
}

@Composable
private fun NotificationListAreaItemTop(
    onClickNotificationItem: () -> Unit,
    imageUrl: String,
    label: String,
    title: String,
    content: String,
    isRead: Boolean,
) {
    Row(
        modifier = Modifier
            .width(295.dp)
            .height(84.dp)
    ) {
        NotificationImageArea(
            imageUrl = imageUrl,
            label = label,
            isRead = isRead,
        )
        WidthSpacer(widthDp = 16.dp)
        NotificationTitleAndContent(
            onClickNotificationItem = onClickNotificationItem,
            title = title,
            content = content,
            modifier = Modifier
                .width(187.dp)
        )
        WidthSpacer(widthDp = 8.dp)
        DrawableIconButton(
            icon = painterResource(id = R.drawable.icon_vertical_more2),
            contentDescription = "",
            onClick = {},
            modifier = Modifier.size(24.dp),
            iconColor = GRAY500
        )
    }
}

@Composable
private fun NotificationImageArea(
    imageUrl: String,
    label: String,
    isRead: Boolean,
) {
    Column(
        modifier = Modifier
            .width(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImageLoad(
            url = imageUrl,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        HeightSpacer(heightDp = 4.dp)
        TextComponent(
            text = label,
            fontSize = B3,
            fontWeight = FontWeight.SemiBold,
            textColor = if(isRead) GRAY500 else BLACK
        )
    }
}

@Composable
private fun NotificationTitleAndContent(
    onClickNotificationItem: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    content: String,
) {
    Column(
        modifier = modifier
    ) {
        TextComponent(
            onClick = onClickNotificationItem,
            text = title,
            fontSize = B2,
            fontWeight = FontWeight.SemiBold,
            textColor = BLACK,
            modifier = Modifier
                .fillMaxWidth()
        )
        HeightSpacer(heightDp = 4.dp)
        TextComponent(
            onClick = onClickNotificationItem,
            text = content,
            fontSize = B2,
            textColor = BLACK,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun NotificationListAreaItemBottom(
    time: String,
    textColor: Color = BLACK,
    onClickNotificationItem: () -> Unit,
) {
    TextComponent(
        onClick = onClickNotificationItem,
        text = convertIsoToCustomDateFormat(time),
        fontSize = B2,
        textColor = textColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .padding(top = 4.dp),
        align = Alignment.CenterEnd,
    )
}

@Preview(showBackground = true)
@Composable
private fun NotificationListAreaItemPreview() {
    NotificationListAreaItem(
        onClickNotificationItem = {},
        imageUrl = "",
        label = "파티 소식",
        title = "[서울] 장소별 루트짜주는 데이트 어플[서울] 장소별 루트짜주는",
        content = "파티가 성공적으로 종료되었어요. 참여해 주셔서 감사합니다.",
        time = "2024-06-05T15:30:45.123Z",
        isRead = true
    )
}

@Preview(showBackground = true)
@Composable
private fun NotificationListAreaItemPreview2() {
    NotificationListAreaItem(
        onClickNotificationItem = {},
        imageUrl = "",
        label = "파티 소식",
        title = "[서울] 장소별 루트짜주는 데이트 어플[서울] 장소별 루트짜주는",
        content = "파티가 성공적으로 종료되었어요. 참여해 주셔서 감사합니다.",
        time = "2024-06-05T15:30:45.123Z",
        isRead = false
    )
}