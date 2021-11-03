package ru.kostry.nasaapi.ui.podfragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomappbar.BottomAppBar
import ru.kostry.nasaapi.R
import ru.kostry.nasaapi.databinding.FragmentPictureOfDayBinding
import ru.kostry.nasaapi.ui.podfragment.viewmodel.PictureOfTheDayViewModel

class PictureOfDayFragment : Fragment() {

    private var _binding: FragmentPictureOfDayBinding? = null
    private val binding get() = _binding!!

    private val pcdViewModel: PictureOfTheDayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentPictureOfDayBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = pcdViewModel
            pcdFragment = this@PictureOfDayFragment
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun touchFAB(){
        if (pcdViewModel.onMainFAB.value == true){
            pcdViewModel.setOnMainFAB(false)
            binding.fab.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.ic_back_fab) })
            binding.bottomAppBar.apply {
                navigationIcon = null
                fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                replaceMenu(R.menu.menu_bottom_bar_other_screen)
            }
        }else{
            pcdViewModel.setOnMainFAB(true)
            binding.fab.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.ic_plus_fab) })
            binding.bottomAppBar.apply {
                navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }
}