package ie.wit.workoutx.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.workoutx.R
import ie.wit.workoutx.databinding.CardExerciseBinding
import ie.wit.workoutx.models.ExerciseModel

interface Listener{
    fun onWorkoutclick(workout: ExerciseModel)
}

class ExerciseAdapter constructor(private var exercises: List<ExerciseModel>, val listener :Listener)
    : RecyclerView.Adapter<ExerciseAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val exercise = exercises[holder.adapterPosition]
        holder.bind(exercise,listener)
    }

    override fun getItemCount(): Int = exercises.size

    inner class MainHolder(val binding : CardExerciseBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: ExerciseModel, listener: Listener) {

            itemView.setOnClickListener{listener.onWorkoutclick(exercise)}


            binding.workName.text = exercise.workoutName
            binding.workDuration.text = exercise.workoutDuration + " hours"
            binding.workType.text = exercise.workoutType
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
        }
    }
}