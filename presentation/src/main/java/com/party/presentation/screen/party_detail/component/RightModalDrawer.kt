package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.component.icon.DrawableIconButton
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B1
import com.party.guam.design.GRAY400
import com.party.guam.design.PRIMARY
import com.party.guam.design.T2
import com.party.guam.design.WHITE

@Composable
fun RightModalDrawer(
    currentTitle: String,
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    gesturesEnabled: Boolean = true,
    scrimColor: Color = DrawerDefaults.scrimColor,
    content: @Composable () -> Unit,
    onGotoPartyEdit: () -> Unit,
    onGotoPartyUser: () -> Unit,
    onGotoPartyRecruitmentEdit: () -> Unit,
    onGotoManageApplicant: () -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            modifier = Modifier,
            drawerState = drawerState,
            gesturesEnabled = gesturesEnabled,
            scrimColor = scrimColor,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val twoThirdsWidth = maxWidth * 2 / 3

                        Column(
                            modifier = Modifier
                                .width(twoThirdsWidth)
                                .fillMaxHeight()
                                .align(Alignment.CenterEnd)
                                .background(WHITE),
                        ){
                            HeightSpacer(heightDp = 60.dp)
                            DrawerContentArea(
                                currentTitle = currentTitle,
                                onGotoPartyEdit = onGotoPartyEdit,
                                onGotoPartyUser = onGotoPartyUser,
                                onGotoPartyRecruitmentEdit = onGotoPartyRecruitmentEdit,
                                onGotoManageApplicant = onGotoManageApplicant
                            )
                        }
                    }
                }
            },
            content = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    content()
                }
            },
        )
    }
}

@Composable
private fun DrawerContentArea(
    currentTitle: String,
    onGotoPartyEdit: () -> Unit,
    onGotoPartyUser: () -> Unit,
    onGotoPartyRecruitmentEdit: () -> Unit,
    onGotoManageApplicant: () -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp)
    ){
        ManageParty(
            currentTitle = currentTitle,
            onGotoPartyEdit = onGotoPartyEdit,
            onGotoPartyUser = onGotoPartyUser
        )
        HeightSpacer(heightDp = 40.dp)
        ManageRecruitment(
            currentTitle = currentTitle,
            onGotoPartyRecruitmentEdit = onGotoPartyRecruitmentEdit,
            onGotoManageApplicant = onGotoManageApplicant
        )
    }

}

@Composable
private fun ManageParty(
    currentTitle: String,
    onGotoPartyEdit: () -> Unit,
    onGotoPartyUser: () -> Unit,
) {
    DrawerTitle(title = "파티 관리")
    DrawerItem(
        currentTitle = currentTitle,
        text = "파티 수정",
        onClick = onGotoPartyEdit
    )
    DrawerItem(
        currentTitle = currentTitle,
        text = "파티원 관리",
        onClick = onGotoPartyUser
    )
}

@Composable
private fun ManageRecruitment(
    currentTitle: String,
    onGotoPartyRecruitmentEdit: () -> Unit,
    onGotoManageApplicant: () -> Unit,
) {
    DrawerTitle(title = "모집 관리")
    DrawerItem(
        currentTitle = currentTitle,
        text = "모집 편집",
        onClick = onGotoPartyRecruitmentEdit
    )
    DrawerItem(
        currentTitle = currentTitle,
        text = "지원자 관리",
        onClick = onGotoManageApplicant
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
    currentTitle: String,
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .noRippleClickable { if(currentTitle != text) onClick() }, // 이미 선택된 메뉴는 클릭되지 않게
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            text = text,
            fontSize = B1,
            fontWeight = FontWeight.SemiBold,
            textColor = if(currentTitle == text) PRIMARY else GRAY400,
            onClick = onClick,
        )

        DrawableIconButton(
            icon = painterResource(id = R.drawable.icon_arrow_right),
            contentDescription = "Arrow Right Icon",
            iconSize = 20.dp,
            iconColor = GRAY400,
            onClick = {
                if(currentTitle != text) onClick()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RightModalDrawerPreview() {
    RightModalDrawer(
        currentTitle = "",
        drawerState = DrawerState(DrawerValue.Closed), // Preview용 상태 고정
        content = {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val twoThirdsWidth = maxWidth * 2 / 3

                    Column(
                        modifier = Modifier
                            .width(twoThirdsWidth)
                            .fillMaxHeight()
                            .align(Alignment.CenterEnd)
                            .background(WHITE),
                    ){
                        HeightSpacer(heightDp = 60.dp)
                        DrawerContentArea(
                            currentTitle = "",
                            onGotoPartyEdit = {},
                            onGotoPartyUser = {},
                            onGotoPartyRecruitmentEdit = {},
                            onGotoManageApplicant = {}
                        )
                    }
                }
            }
        },
        onGotoPartyEdit = {},
        onGotoPartyUser = {},
        onGotoPartyRecruitmentEdit = {},
        onGotoManageApplicant = {}
    )
}