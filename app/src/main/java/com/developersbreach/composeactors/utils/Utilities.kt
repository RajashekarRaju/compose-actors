package com.developersbreach.composeactors.utils

import com.squareup.moshi.Moshi
import java.lang.reflect.Type
import java.util.*

/**
 * @param dateOfBirth received in "dd-mm-yyyy" format. Gets rid of day and month to read year.
 * @return returns calculated age with current year instance of the person.
 */
fun calculateAge(
    dateOfBirth: String?
): Int {
    var age = 0
    // Since we receive the json data for data of birth with null safe as string,
    // Compare with null string to know whether actual date is available.
    // TODO - avoid check with null string
    if (dateOfBirth != "null") {
        val grabYear: Int? = dateOfBirth?.dropLast(6)?.toInt()
        val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        if (grabYear != null) {
            age = currentYear - grabYear
        }
    }

    return age
}

/**
 * @param popularity received in "132.776" format.
 * @return returns number approximately ignoring digits after period.
 */
fun getPopularity(
    popularity: Double?
): String {
    val formatPopularity = popularity.toString().split(".")
    return formatPopularity[0]
}

/**
 * @param location received in "city, state, country".
 * Split them into separate strings with "," delimiter.
 * @return always prioritize returning last string found.
 * if (country == available) -> return country.
 * if (country != available) -> return state.
 * if (state != available) -> return city.
 */
fun getPlaceOfBirth(
    location: String?
): String? {
    val findKnownLocation = location?.split(",")
    val cityStateCountry = findKnownLocation?.size?.minus(1)
    return cityStateCountry?.let {
        findKnownLocation[it].trim()
    }
}
fun toJsonString(value: Any, type: Type): String {
    return Moshi.Builder().build().adapter<Any>(type).toJson(value)
}

fun <T> fromJsonString(value: String, type: Type): T? {
    return Moshi.Builder().build().adapter<T>(type).fromJson(value)
}

fun getMovieRuntimeFormatted(
    runtime: Int?
): String {
    val hours: Int? = runtime?.div(60)
    val minutes: Int? = runtime?.rem(60)
    return "${hours}h:${minutes}m"
}