package ai.pams.android.kotlin.models.consent.contact


import com.google.gson.annotations.SerializedName

data class CustomUrl(
    @SerializedName("en")
    val en: String? = null,
    @SerializedName("th")
    val th: String? = null
)