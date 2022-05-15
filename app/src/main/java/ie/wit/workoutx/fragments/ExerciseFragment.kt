package ie.wit.workoutx.fragments

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.wit.workoutx.R
import ie.wit.workoutx.databinding.FragmentExerciseBinding
import ie.wit.workoutx.main.WorkoutXApp
import ie.wit.workoutx.models.ExerciseModel

class ExerciseFragment : Fragment()  {

    lateinit var app: WorkoutXApp
    var totalDonated = 0
    private var _fragBinding: FragmentExerciseBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var workType : String
    //lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        app = activity?.application as WorkoutXApp
        setHasOptionsMenu(true)
        //navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentExerciseBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_exercise)

        fillWorkoutList()

        setButtonListener(fragBinding)
        return root;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ExerciseFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    private fun fillWorkoutList(){
        var spinner: Spinner = fragBinding.workoutlist
        var adapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(),R.array.workoutypes,android.R.layout.simple_spinner_item)
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

    fun setButtonListener(layout: FragmentExerciseBinding) {
        layout.workoutButton.setOnClickListener {
            if(fragBinding.workName.length() > 2 && fragBinding.workDuration.length() > 0){
                app.exercisesStore.create(ExerciseModel(workoutName = fragBinding.workName.text.toString(), workoutDuration = fragBinding.workDuration.text.toString(), workoutType = this.workType))


            }else{
                Toast.makeText(context,"Please fill in all workout fields",Toast.LENGTH_LONG).show()
            }
            }
        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_exercise, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()


    }
}


