package com.nicolas.maltesp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.nicolas.maltesp.domain.models.ParametersState

@Entity(
    indices = [Index(value = ["recipeName"], unique = true)]
)
data class MaltingRecipe(
    @PrimaryKey val uid: Int,
    @ColumnInfo val recipeName: String,
    @ColumnInfo(name = "steeping_submerged_time") val steepingSubmergedTime: String?,
    @ColumnInfo(name = "steeping_water_volume") val steepingWaterVolume: String?,
    @ColumnInfo(name = "steeping_rest_time") val steepingRestTime: String?,
    @ColumnInfo(name = "steeping_cycles") val steepingCycles: String?,
    @ColumnInfo(name = "germination_rotation_level") val germinationRotationLevel: String?,
    @ColumnInfo(name = "germination_total_time") val germinationTotalTime: String?,
    @ColumnInfo(name = "germination_water_volume") val germinationWaterVolume: String?,
    @ColumnInfo(name = "germination_water_addition") val germinationWaterAddition: String?,
    @ColumnInfo(name = "kilning_temperature") val kilningTemperature: String?,
    @ColumnInfo(name = "kilning_time") val kilningTime: String?
)

fun newRecipe(uid: Int, recipeName: String, parametersState: ParametersState): MaltingRecipe {
    return MaltingRecipe(
        uid = uid,
        recipeName = recipeName,
        steepingSubmergedTime = parametersState.steeping.submergedTime.value,
        steepingWaterVolume = parametersState.steeping.waterVolume.value,
        steepingRestTime = parametersState.steeping.restTime.value,
        steepingCycles = parametersState.steeping.cycles.value,
        germinationRotationLevel = parametersState.germination.rotationLevel.value,
        germinationTotalTime = parametersState.germination.totalTime.value,
        germinationWaterVolume = parametersState.germination.waterVolume.value,
        germinationWaterAddition = parametersState.germination.waterAddition.value,
        kilningTemperature = parametersState.kilning.temperature.value,
        kilningTime = parametersState.kilning.time.value
    )
}






