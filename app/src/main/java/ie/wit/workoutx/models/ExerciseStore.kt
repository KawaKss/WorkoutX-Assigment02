package ie.wit.workoutx.models

interface ExerciseStore {
    fun findAll() : MutableList<ExerciseModel>
    fun update(exercise: ExerciseModel)
    fun delete(exercise: ExerciseModel)
    fun findById(id: Long) : ExerciseModel?
    fun create(exercise: ExerciseModel)
}