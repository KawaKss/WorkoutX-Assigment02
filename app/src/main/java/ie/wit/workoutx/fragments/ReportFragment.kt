package ie.wit.workoutx.fragments

import android.os.Bundle
import android.view.*
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.workoutx.R
import ie.wit.workoutx.adapters.ExerciseAdapter
import ie.wit.workoutx.adapters.Listener
import ie.wit.workoutx.databinding.FragmentReportBinding
import ie.wit.workoutx.helpers.SwipeToDeleteCallback
import ie.wit.workoutx.helpers.SwipeToEditCallback
import ie.wit.workoutx.main.WorkoutXApp
import ie.wit.workoutx.models.ExerciseModel

class ReportFragment : Fragment(),Listener {

    lateinit var app: WorkoutXApp
    private var _fragBinding: FragmentReportBinding? = null
    private val fragBinding get() = _fragBinding!!
    //lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as WorkoutXApp
        setHasOptionsMenu(true)
        //navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentReportBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_report)
        swipeHandlers()


        return root
    }

    override fun onResume() {
        super.onResume()
        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragBinding.recyclerView.adapter = ExerciseAdapter(app.exercisesStore.findAll(),this)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_report, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ReportFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    private fun swipeHandlers(){
        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = fragBinding.recyclerView.adapter as ExerciseAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                app.exercisesStore.delete(viewHolder.itemView.tag as ExerciseModel)



            }

        }

        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)


        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val action = ReportFragmentDirections.actionReportFragmentToWorkoutFragment(viewHolder.itemView.tag as ExerciseModel)
                findNavController().navigate((action))
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onWorkoutclick(workout: ExerciseModel) {
    }


}