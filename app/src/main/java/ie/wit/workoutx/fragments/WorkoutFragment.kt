package ie.wit.workoutx.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ie.wit.workoutx.R
import ie.wit.workoutx.R.string.action_workout
import ie.wit.workoutx.databinding.FragmentWorkoutBinding
import ie.wit.workoutx.main.WorkoutXApp
import ie.wit.workoutx.models.ExerciseModel


class WorkoutFragment : Fragment() {

    lateinit var app: WorkoutXApp
    private var _fragBinding: FragmentWorkoutBinding? = null
    private val fragBinding get() = _fragBinding!!

    private lateinit var model: ExerciseModel

    //variables
    lateinit var workoutName: String
    lateinit var workoutType: String
    lateinit var workoutDuration: String
    //Spinner
    private lateinit var workType : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as WorkoutXApp

        setHasOptionsMenu(true)

        //Receiving model
        val bundle = arguments
        val getModel = bundle?.get("model") as ExerciseModel
        //setting model
        model = getModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentWorkoutBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        fillWorkoutList()
        updateListener()
        deleteListener()
        variablesetter()
        activity?.title = getString(action_workout)
return root
    }




    private fun updateListener(){
        //Add button
        //Set on click listener
        //update()

        fragBinding.updateButton.setOnClickListener{

            update()
            back()
        }
    }

    private fun update(){
        //Validation
        if(fragBinding.WorkoutName.length() > 2){
        if(fragBinding.WorkoutDuration.length() > 0){
            Toast.makeText(context,workType,Toast.LENGTH_SHORT).show()
            //Update
            app.exercisesStore.update(ExerciseModel(workoutName = fragBinding.WorkoutName.text.toString(), workoutDuration = fragBinding.WorkoutDuration.text.toString(), workoutType = workType))

        }else{
            Toast.makeText(context,"Duration cannot be empty",Toast.LENGTH_LONG).show()
        }
        }else{

            Toast.makeText(context,"Name too short",Toast.LENGTH_LONG).show()
        }

    }
    fun deleteListener(){
        fragBinding.deleteButton.setOnClickListener{
            delete()
        }
    }
     fun delete() {
        app.exercisesStore.delete(model)
        back()
    }

    private fun fillWorkoutList(){
        var spinner: Spinner = fragBinding.WorkoutType
        var adapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(),
            R.array.workoutypes,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                workType = spinner.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun back(){
        val action = WorkoutFragmentDirections.actionWorkoutFragmentToReportFragment()
        findNavController().navigate((action))
    }


    //Sets the variables from received model
    private fun variablesetter(){
        workoutName = model.workoutName.toString()
        workoutType = model.workoutType.toString()
        workoutDuration = model.workoutDuration.toString()


//        <item>Chest</item>  0
//        <item>Stomach</item> 1
//        <item>Shoulders</item> 2
//        <item>Back</item> 3
//        <item>Legs</item> 4
//        <item>Biceps</item> 5
//        <item>Triceps</item> 6

        if(workoutType.equals("Chest")){
            fragBinding.WorkoutType.setSelection(0)

        }
        if(workoutType.equals("Stomach")){
            fragBinding.WorkoutType.setSelection(1)
        }

        if(workoutType.equals("Shoulders")){
            fragBinding.WorkoutType.setSelection(2)
        }

         if(workoutType.equals("Back")){
             fragBinding.WorkoutType.setSelection(3)
        }

         if(workoutType.equals("Legs")){
             fragBinding.WorkoutType.setSelection(4)
        }

         if(workoutType.equals("Biceps")){
             fragBinding.WorkoutType.setSelection(5)
        }

         if(workoutType.equals("Triceps")){
             fragBinding.WorkoutType.setSelection(6)
        }

        fragBinding.WorkoutName.setText(workoutName)

        fragBinding.WorkoutDuration.setText(workoutDuration)



    }









    companion object {

        @JvmStatic
        fun newInstance() =
            WorkoutFragment().apply {

            }
    }
}