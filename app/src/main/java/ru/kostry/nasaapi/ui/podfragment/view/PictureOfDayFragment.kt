package ru.kostry.nasaapi.ui.podfragment.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.kostry.nasaapi.R
import ru.kostry.nasaapi.databinding.FragmentPictureOfDayBinding
import ru.kostry.nasaapi.ui.MainActivity
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

        bottomSheetResponseText =
            BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_response_text_include)!!)

        bottomSheetSettings =
            BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_settings_include)!!)

        binding.podSearchInputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.podSearchInputEditText.text.toString()}")
            })
        }
        val observer = Observer<PODApiStatus> { renderExplanation(it) }
        podViewModel.status.observe(viewLifecycleOwner, observer)

        setBottomAppBar(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_load_hd -> {
                if (isLoadedHD) {
                    loadImageHD(podViewModel.uri.value, false)
                }else{
                    loadImageHD(podViewModel.uriHD.value, true)

                }
            }
            R.id.app_bar_explanation -> {
                bottomSheetState(bottomSheetResponseText)
            }
            R.id.app_bar_settings -> {
                bottomSheetState(bottomSheetSettings)
            }
            R.id.fab -> Toast.makeText(context, "fab", Toast.LENGTH_SHORT).show()
            android.R.id.home -> Toast.makeText(context, "home", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadImageHD(imgUrl: String?, isBoolean: Boolean) {
        imgUrl?.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            binding.imageView.load(imgUri)
            binding.imageView.load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        }
        isLoadedHD = isBoolean
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

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        binding.bottomAppBar.overflowIcon =
            ContextCompat.getDrawable(context, R.drawable.ic_settings_menu)
    }

    private fun renderExplanation(apiStatus: PODApiStatus) {
        when (apiStatus) {
            PODApiStatus.DONE -> {
                bindResponseText(podViewModel.explanation.value, podViewModel.title.value)
            }
            PODApiStatus.LOADING -> {
                bindResponseText(PODApiStatus.LOADING.toString(), PODApiStatus.LOADING.toString())
            }
            PODApiStatus.ERROR -> {
                bindResponseText(PODApiStatus.ERROR.toString(), PODApiStatus.ERROR.toString())
            }
        }
    }

    private fun bindResponseText(explanation: String?, title: String?) {
        binding.apply {
            bottomSheetResponseTextInclude.bottomSheetResponseTextExplanation.text = explanation
            podTitleTextView.text = title
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

