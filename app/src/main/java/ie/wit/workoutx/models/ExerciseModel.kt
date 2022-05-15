package ie.wit.workoutx.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ExerciseModel(var id: Long = 0,
                         var workoutName: String = "",
                         var workoutType: String = "N/A",
                         var workoutDuration: String = "") : Parcelable