package com.bksd.brandapp.ui.brand_detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bksd.brandapp.R
import com.bksd.brandapp.ui.brand_detail.model.BrandDetailUi
import com.bksd.brandapp.ui.brand_detail.model.BrandLinkUi
import com.bksd.brandapp.ui.brand_detail.model.CompanyUi
import com.bksd.brandapp.ui.theme.BrandAppTheme

@Composable
fun BrandDetailBody(modifier: Modifier = Modifier, uiModel: BrandDetailUi) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            uiModel.name?.let { brandName ->
                Text(
                    text = brandName,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            if (uiModel.showVerifiedIcon()) {
                Icon(
                    painter = painterResource(R.drawable.ic_verified),
                    contentDescription = "Brand Verified Icon",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp),
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }

        uiModel.description?.let { desc ->
            uiModel.longDescription?.let { longDesc ->
                BrandDescriptionText(
                    modifier = Modifier,
                    description = desc,
                    longDescription = longDesc
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                uiModel.links.webLink?.let { SocialMediaIcon(R.drawable.ic_web, it) }
                uiModel.links.linkedinLink?.let {
                    SocialMediaIcon(
                        R.drawable.ic_linkedin,
                        it
                    )
                }
                uiModel.links.youtubeLink?.let { SocialMediaIcon(R.drawable.ic_youtube, it) }
                uiModel.links.facebookLink?.let {
                    SocialMediaIcon(
                        R.drawable.ic_facebook,
                        it
                    )
                }
                uiModel.links.instagramLink?.let {
                    SocialMediaIcon(
                        R.drawable.ic_instagram,
                        it
                    )
                }
                uiModel.links.twitterLink?.let { SocialMediaIcon(R.drawable.ic_twitter, it) }
            }

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = 2,
                maxLines = 2,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                uiModel.company.location?.let {
                    MetaInfoRow(
                        icon = Icons.Default.Place,
                        text = it
                    )
                }
                uiModel.company.foundedYear?.let {
                    MetaInfoRow(
                        icon = Icons.Default.Schedule,
                        text = it
                    )
                }
                uiModel.company.kind?.let {
                    MetaInfoRow(
                        icon = Icons.Default.BusinessCenter,
                        text = it
                    )
                }
                uiModel.company.employees?.let {
                    MetaInfoRow(
                        icon = Icons.Default.Groups,
                        text = it
                    )
                }
            }

            uiModel.logos?.let { LogosSection(modifier, it) }
            uiModel.colors?.let { ColorsSection(modifier, it) }
            uiModel.fonts?.let { FontsSection(modifier, it) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BrandDetailBodyPreview() {
    BrandAppTheme {
        BrandDetailBody(
            uiModel = BrandDetailUi(
                name = "Apple",
                icon = "www.apple.com",
                banner = "www.apple.com",
                description = "Apple Inc. is an American multinational",
                longDescription = "Apple Inc. It was founded on April 1, 1976, by Steve Jobs, Steve Wozniak, and Ronald Wayne. The company's headquarters are located in Cupertino, California. Apple is widely known for its flagship products, including the iPhone",
                links = BrandLinkUi(
                    webLink = "www.google.com",
                    linkedinLink = "www.linkedin.com"
                ),
                company = CompanyUi(
                    employees = 10000.toString(),
                    foundedYear = 2000.toString(),
                    kind = "Public Company",
                    location = "Cupertino, US"
                ),
                logos = listOf(""),
                colors = listOf(""),
                fonts = emptyMap(),
            )
        )
    }
}