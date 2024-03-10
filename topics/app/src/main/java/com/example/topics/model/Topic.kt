package com.example.topics.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes val stringResourceId: Int,
    val topicCount: Int,
    @DrawableRes val imageResourceId: Int
)
