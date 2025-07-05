package com.developersbreach.composeactors.core.database.entity

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.composeactors.data.person.model.PersonDetail

@Entity(tableName = "person_detail_table")
data class PersonDetailEntity(
    @Stable
    @PrimaryKey
    @ColumnInfo(name = "column_person_detail_id")
    val personId: Int,
    @ColumnInfo(name = "column_person_detail_name")
    val personName: String,
    @ColumnInfo(name = "column_person_detail_profile_path")
    val profilePath: String?,
    @ColumnInfo(name = "column_person_detail_biography")
    val biography: String,
    @ColumnInfo(name = "column_person_detail_date_of_birth")
    val dateOfBirth: String?,
    @ColumnInfo(name = "column_person_detail_place_of_birth")
    val placeOfBirth: String?,
    @ColumnInfo(name = "column_person_detail_popularity")
    val popularity: Double,
)

fun PersonDetailEntity.toPersonDetail(): PersonDetail {
    return PersonDetail(
        personId = personId,
        personName = personName,
        profilePath = profilePath,
        biography = biography,
        dateOfBirth = dateOfBirth,
        placeOfBirth = placeOfBirth,
        popularity = popularity,
    )
}