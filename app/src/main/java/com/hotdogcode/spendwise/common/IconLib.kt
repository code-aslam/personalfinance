package com.hotdogcode.spendwise.common

import androidx.compose.ui.graphics.Color
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.ui.theme.DarkForestGreenColor
import com.hotdogcode.spendwise.ui.theme.DeepBurgundy
import com.hotdogcode.spendwise.ui.theme.Purple40
import com.hotdogcode.spendwise.ui.theme.SoftPinkColor
import com.hotdogcode.spendwise.ui.theme.brightGreen
import com.hotdogcode.spendwise.ui.theme.googleDarkblue
import com.hotdogcode.spendwise.ui.theme.googleyellow
import com.hotdogcode.spendwise.ui.theme.orange
import com.hotdogcode.spendwise.ui.theme.red


object IconLib {
    fun getIcon(iconName : IconName): Int {
        return when (iconName) {
            IconName.AWARD -> R.drawable.awards
            IconName.BABY -> R.drawable.baby
            IconName.BEAUTY -> R.drawable.beauty
            IconName.BILLS -> R.drawable.bills
            IconName.CAR -> R.drawable.car
            IconName.CLOTHING -> R.drawable.clothing
            IconName.COUPONS -> R.drawable.coupons
            IconName.EDUCATION -> R.drawable.education
            IconName.ELECTRONICS -> R.drawable.electronics
            IconName.ENTERTAINMENT -> R.drawable.entertainment
            IconName.FOOD -> R.drawable.food
            IconName.GRANTS -> R.drawable.grants
            IconName.HEALTH -> R.drawable.health
            IconName.HOME -> R.drawable.home
            IconName.INSURANCE -> R.drawable.insurance
            IconName.LOTTERY -> R.drawable.lottery
            IconName.REFUNDS -> R.drawable.refund
            IconName.RENTALS -> R.drawable.rentals
            IconName.SALARY -> R.drawable.salary
            IconName.SALE -> R.drawable.sale
            IconName.SHOPPING -> R.drawable.shopping
            IconName.SOCIAL -> R.drawable.social
            IconName.SPORT -> R.drawable.sport
            IconName.WALLET_BIG -> R.drawable.walletbig
            IconName.CATEGORY_BIG -> R.drawable.category
            else -> {
                R.drawable.grants
            }
        }
    }
}

enum class IconName(val color : Color){
    AWARD(red),
    BABY(brightGreen),
    BEAUTY(Purple40),
    BILLS(red),
    CAR(brightGreen),
    CLOTHING(googleyellow),
    COUPONS(googleyellow),
    EDUCATION(DarkForestGreenColor),
    ELECTRONICS(SoftPinkColor),
    ENTERTAINMENT(orange),
    FOOD(googleyellow),
    GRANTS(googleDarkblue),
    HEALTH(googleyellow),
    HOME(DeepBurgundy),
    INSURANCE(Purple40),
    LOTTERY(SoftPinkColor),
    REFUNDS(googleyellow),
    RENTALS(googleDarkblue),
    SALARY(googleyellow),
    SALE(DeepBurgundy),
    SHOPPING(red),
    SOCIAL(brightGreen),
    SPORT(googleyellow),
    WALLET_BIG(Color.Black),
    CATEGORY_BIG(Color.Black)
}