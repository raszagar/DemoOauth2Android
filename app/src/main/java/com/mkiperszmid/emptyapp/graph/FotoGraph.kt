package com.mkiperszmid.emptyapp.graph

import com.google.gson.annotations.SerializedName

data class FotoGraph (
    @SerializedName("@odata.context")
    val context: String?,
    @SerializedName("@odata.mediaContentType")
    val mediaContentType: String?,
    @SerializedName("@odata.mediaEtag")
    val mediaEtag: String?,
    val height: Number?,
    val width: Number?
)

data class ErrorGraph (
    val error: ErrorApiGraph
)

data class ErrorApiGraph (
    val code: String,
    val message: String
)
