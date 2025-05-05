package com.party.presentation.screen.auth_setting.component

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.party.common.TextComponent
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.T3

@Composable
fun TermsArea(
    context: Context,
    onGotoServiceIntroduce: () -> Unit,
    onGotoCustomerInquiries: () -> Unit,
    onGotoTerms: () -> Unit,
    onGotoPrivacyPolicy: () -> Unit,
) {
    val packageInfo = remember {
        context.packageManager.getPackageInfo(context.packageName, 0)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TermsAreaItem(
            text = "서비스 소개",
            onClick = onGotoServiceIntroduce
        )
        TermsAreaItem(
            text = "고객문의",
            onClick = onGotoCustomerInquiries
        )
        TermsAreaItem(
            text = "이용약관",
            onClick = onGotoTerms
        )
        TermsAreaItem(
            text = "개인정보 처리방침",
            onClick = onGotoPrivacyPolicy
        )
        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(start = 20.dp),
            text = "오픈소스 라이센스",
            fontSize = T3,
            fontWeight = FontWeight.SemiBold,
            onClick = {
                context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextComponent(
                modifier = Modifier
                    .height(52.dp)
                    .padding(start = 20.dp),
                text = "버전 정보",
                fontSize = T3,
                fontWeight = FontWeight.SemiBold,
            )

            TextComponent(
                modifier = Modifier
                    .height(52.dp)
                    .padding(end = 20.dp),
                text = packageInfo.versionName ?: "1.0.00",
                fontSize = T3,
                fontWeight = FontWeight.Normal,
                textColor = GRAY500,
            )
        }
    }
}

@Composable
private fun TermsAreaItem(
    text: String,
    onClick: () -> Unit,
) {
    TextComponent(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(start = 20.dp),
        text = text,
        fontSize = T3,
        fontWeight = FontWeight.SemiBold,
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
private fun TermsAreaPreview() {
    TermsArea(
        context = LocalContext.current,
        onGotoServiceIntroduce = {},
        onGotoCustomerInquiries = {},
        onGotoTerms = {},
        onGotoPrivacyPolicy = {}
    )
}