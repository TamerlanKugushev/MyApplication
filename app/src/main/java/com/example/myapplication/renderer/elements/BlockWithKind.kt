package com.example.myapplication.renderer.elements

import com.google.gson.annotations.SerializedName

class BlockWithKind(
    @SerializedName("kind") override val kind: String = "",
    @SerializedName("delayUnit") override val delayUnit: String? = null,
    @SerializedName("delayStr") override val delayStr: String? = null,
    @SerializedName("delayDetail") override val delayDetail: String? = null,
    @SerializedName("statusStr") override val statusStr: String? = null,
    @SerializedName("statusDetail") override val statusDetail: String? = null,
) : BlockContent(), Kind