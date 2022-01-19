package com.ssr_android.model

data class FarmIssue(
    @SerializedName("edit_num")
    val editNum: String,
    @SerializedName("edit_address")
    val editAddress : String,
    @SerializedName("img_url")
    val imgUrl: String = ""
)