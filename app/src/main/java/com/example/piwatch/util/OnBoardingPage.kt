package com.example.piwatch.util

import androidx.annotation.DrawableRes
import com.example.piwatch.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: Int,
) {
    object First : OnBoardingPage(
        image = R.drawable.first,
        title = R.string.first_onboarding,
    )

    object Second : OnBoardingPage(
        image = R.drawable.second,
        title = R.string.second_onboarding,
    )
}