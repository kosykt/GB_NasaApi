package ru.kostry.nasaapi.ui.podfragment.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.kostry.nasaapi.R
import ru.kostry.nasaapi.databinding.FragmentPictureOfDayBinding
import ru.kostry.nasaapi.ui.MainActivity
import ru.kostry.nasaapi.ui.podfragment.view.screens.FirstScreenPodFragment
import ru.kostry.nasaapi.ui.podfragment.view.screens.SecondScreenPodFragment
import ru.kostry.nasaapi.ui.podfragment.view.screens.ViewPagerAdapter
import ru.kostry.nasaapi.ui.podfragment.viewmodel.PODApiStatus
import ru.kostry.nasaapi.ui.podfragment.viewmodel.PictureOfTheDayViewModel

class PictureOfDayFragment : Fragment() {

    private var _binding: FragmentPictureOfDayBinding? = null
    private val binding get() = _binding!!

    private val podViewModel: PictureOfTheDayViewModel by activityViewModels()

    private lateinit var bottomSheetResponseText: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetSettings: BottomSheetBehavior<ConstraintLayout>

    private var isLoadedHD = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val fragmentBinding = FragmentPictureOfDayBinding.inflate(inflater, container, false)

        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = podViewModel
            pcdFragment = this@PictureOfDayFragment
        }

        val fragmentList =
            arrayListOf<Fragment>(FirstScreenPodFragment(), SecondScreenPodFragment())

        val adapter =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        binding.podSearchInputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.podSearchInputEditText.text.toString()}")
            })
        }
        val observer = Observer<PODApiStatus> { renderData(it) }
        podViewModel.status.observe(viewLifecycleOwner, observer)

        initBottomAppBar(view)
        initBottomSheet()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_load_hd -> {

            }
            R.id.app_bar_explanation -> {
                bottomSheetOpener(bottomSheetSettings, bottomSheetResponseText)
            }
            R.id.app_bar_settings -> {
                bottomSheetOpener(bottomSheetResponseText, bottomSheetSettings)
            }
            android.R.id.home -> Toast.makeText(context, "home", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initBottomSheet() {
        bottomSheetResponseText =
            BottomSheetBehavior.from(view?.findViewById(R.id.bottom_sheet_response_text_include)!!)

        bottomSheetSettings =
            BottomSheetBehavior.from(view?.findViewById(R.id.bottom_sheet_settings_include)!!)

    }

    private fun initBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        binding.bottomAppBar.overflowIcon =
            ContextCompat.getDrawable(context, R.drawable.ic_settings_menu)
    }

    private fun renderData(apiStatus: PODApiStatus) {
        when (apiStatus) {
            PODApiStatus.DONE -> {
                bindResponseText(podViewModel.explanation.value)
            }
            PODApiStatus.LOADING -> {
                bindResponseText(PODApiStatus.LOADING.toString())
            }
            PODApiStatus.ERROR -> {
                bindResponseText(PODApiStatus.ERROR.toString())
            }
        }
    }

    private fun bottomSheetState(
        bottomSheet: BottomSheetBehavior<ConstraintLayout>,
    ) {
        if (bottomSheet.state == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheet.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        } else {
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun bottomSheetOpener(
        closedBottomSheet: BottomSheetBehavior<ConstraintLayout>,
        openedBottomSheet: BottomSheetBehavior<ConstraintLayout>,
    ) {
        closedBottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetState(openedBottomSheet)
    }


    private fun bindResponseText(explanation: String?) {
        binding.apply {
            bottomSheetResponseTextInclude.bottomSheetResponseTextExplanation.text = explanation
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

