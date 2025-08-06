package com.bksd.brandapp.ui.brand_detail.model

data class BrandDetailUi(
    val name: String ? = null,
    val icon: String? = null,
    val banner: String? = null,
    val verified: Boolean = false,
    val description: String? = null,
    val longDescription: String? = null,
    val links: BrandLinkUi = BrandLinkUi(),
    val company: CompanyUi = CompanyUi(),
    val logos: List<String>? = null,
    val colors: List<String>? = null,
    val fonts: Map<String, String>? = null
) {
    fun showVerifiedIcon() = verified
}
