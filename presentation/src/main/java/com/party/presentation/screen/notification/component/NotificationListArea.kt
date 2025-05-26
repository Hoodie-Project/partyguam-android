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
import com.party.common.R
import com.party.common.component.NetworkImageLoad
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.WHITE
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.utils.convertIsoToCustomDateFormat
import com.party.domain.model.user.notification.Notification
import java.time.ZoneId
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun NotificationListArea(
    notification: Notification,
    onClickNotificationItem: (Int) -> Unit,
    onDeleteNotification: (Int) -> Unit,
) {
    HeightSpacer(heightDp = 16.dp)

    if(notification.notifications.isEmpty()){
        HeightSpacer(heightDp = 44.dp)
        TextComponent(
            text = "새로운 알림이 없어요.",
            fontWeight = FontWeight.Normal,
            fontSize = B2,
            modifier = Modifier
                .fillMaxWidth(),
            align = Alignment.Center,
            textColor = GRAY600,
        )
    }else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
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
                    onDeleteNotification = { onDeleteNotification(item.id) }
                )
            }
        }
    }

}

@Composable
private fun NotificationListAreaItem(
    onClickNotificationItem: () -> Unit,
    onDeleteNotification: () -> Unit,
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
            .fillMaxWidth()
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
                    .fillMaxWidth(),
            ) {
                NotificationListAreaItemTop(
                    onClickNotificationItem = onClickNotificationItem,
                    imageUrl = imageUrl,
                    label = label,
                    title = title,
                    content = content,
                    isRead = isRead,
                    onDeleteNotification = onDeleteNotification,
                )
                NotificationListAreaItemBottom(
                    onClickNotificationItem = onClickNotificationItem,
                    time = time,
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
    onDeleteNotification: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .padding(horizontal = 20.dp)
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
                .weight(1f)
        )
        WidthSpacer(widthDp = 8.dp)
        DrawableIconButton(
            icon = painterResource(id = R.drawable.icon_vertical_more2),
            contentDescription = "",
            onClick = onDeleteNotification,
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
    onClickNotificationItem: () -> Unit,
) {
    val formatted = formatServerDate(time)

    TextComponent(
        onClick = onClickNotificationItem,
        text = formatted.text, // convertIsoToCustomDateFormat(time),
        fontSize = B2,
        textColor = if (formatted.isRed) Color.Red else Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .padding(top = 4.dp, end = 20.dp),
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
        isRead = true,
        onDeleteNotification = {}
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
        isRead = false,
        onDeleteNotification = {}
    )
}

data class FormattedTime(
    val text: String,
    val isRed: Boolean,
)

fun formatServerDate(isoString: String): FormattedTime {
    val instant = Instant.parse(isoString)
    val now = Instant.now()
    val zone = ZoneId.systemDefault()

    val dateTime = instant.atZone(zone)
    val nowDateTime = now.atZone(zone)

    val durationMinutes = ChronoUnit.MINUTES.between(dateTime, nowDateTime)
    val durationHours = ChronoUnit.HOURS.between(dateTime, nowDateTime)

    val date = dateTime.toLocalDate()
    val nowDate = nowDateTime.toLocalDate()

    return when {
        durationMinutes <= 10 -> FormattedTime("방금 전", true)
        durationMinutes in 11..59 -> FormattedTime("1시간 전", true)
        durationHours in 1..1 -> FormattedTime("2시간 전", true)
        durationHours in 2..2 -> FormattedTime("3시간 전", true)
        date == nowDate -> FormattedTime("오늘", false)
        date == nowDate.minusDays(1) -> FormattedTime("1일 전", false)
        else -> {
            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
            FormattedTime(dateTime.format(formatter), false)
        }
    }
}