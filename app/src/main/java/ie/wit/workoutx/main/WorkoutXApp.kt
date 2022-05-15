package ie.wit.workoutx.main

import android.app.Application
import ie.wit.workoutx.models.ExerciseMemStore
import ie.wit.workoutx.models.ExerciseStore
import timber.log.Timber

class WorkoutXApp : Application() {

    lateinit var exercisesStore: ExerciseStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        exercisesStore = ExerciseMemStore(applicationContext)
        Timber.i("WorkoutX Application Started")
    }
}