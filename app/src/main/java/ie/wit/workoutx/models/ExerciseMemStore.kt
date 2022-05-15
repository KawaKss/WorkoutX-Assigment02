package ie.wit.workoutx.models

import android.content.Context
import android.system.Os.read
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ie.wit.workoutx.helpers.*
import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class ExerciseMemStore : ExerciseStore {

    var exercises = ArrayList<ExerciseModel>()
    val JSON_FILE = "workouts.json"
    val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
    val listType = object : TypeToken<java.util.ArrayList<ExerciseModel>>() {}.type

    val context: Context

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }


    override fun findAll(): MutableList<ExerciseModel> {
        deserialize()
        return exercises
    }

    override fun findById(id:Long) : ExerciseModel? {
        val foundExercise: ExerciseModel? = exercises.find { it.id == id }
        return foundExercise
    }




    override fun create(exercise: ExerciseModel) {
        exercise.id = getId()
        exercises.add(exercise)
        serialize()
        logAll()
    }




    override fun update(workout: ExerciseModel) {
        val workoutsList = findAll() as java.util.ArrayList<ExerciseModel>
        var foundExercise: ExerciseModel? = workoutsList.find { p -> p.id == workout.id }
        if (foundExercise != null) {
            println(workout.workoutType)
            foundExercise.workoutDuration = workout.workoutDuration
            foundExercise.workoutType = workout.workoutType
            foundExercise.workoutName = workout.workoutName
        }

        serialize()
    }

    override fun delete(exercise: ExerciseModel) {
        exercises.remove(exercise)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(exercises, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        exercises = Gson().fromJson(jsonString, listType)
    }



    fun logAll() {
        Timber.v("** exercises List **")
        exercises.forEach { Timber.v("workouts {it}") }
    }
}