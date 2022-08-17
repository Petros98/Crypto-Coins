package task.coinstats.cryptocoins.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Double?.toFormattedAmountWithCurrency(currency: String? = null): String {
    val df = DecimalFormat("#,##0.00")
    df.decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
    val formattedAmount = if (this == null) "--" else df.format(this)
    return currency?.let { "$formattedAmount $it" } ?: formattedAmount
}

fun Double?.toFormattedDeltaPercent(): String {
    val df = DecimalFormat("#,##0.00")
    df.decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
    val deltaPercent = if (this == null) "--" else df.format(this)
    return "$deltaPercent %"
}