package uz.gita.noteapp.presentation.screen.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import uz.gita.noteapp.R
import uz.gita.noteapp.data.model.NoteData
import uz.gita.noteapp.databinding.ScreenHomeBinding
import uz.gita.noteapp.presentation.adapter.HomeAdapter
import uz.gita.noteapp.presentation.dialog.MyBottomSheetDialog
import uz.gita.noteapp.presentation.screen.home.viewmodel.HomeViewModel
import uz.gita.noteapp.presentation.screen.home.viewmodel.impl.HomeViewModelImpl
import uz.gita.noteapp.utils.myApply

class HomeScreen : Fragment(R.layout.screen_home) {

    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val homeAdapter by lazy { HomeAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val search = menu.findItem(R.id.appSearchBar)
                search.setIcon(R.drawable.ic_search)
                val searchView = search.actionView as SearchView

                val searchPlateId = searchView.context.resources.getIdentifier(
                    "android:id/search_plate",
                    null,
                    null
                )
                val searchPlate: View = searchView.findViewById(searchPlateId)
                searchPlate.setBackgroundResource(0)
                searchView.isIconifiedByDefault = false

                val magId =
                    searchView.resources.getIdentifier("android:id/search_mag_icon", null, null)
                val magImage: ImageView = searchView.findViewById<View>(magId) as ImageView

                val linearLayoutSearchView = magImage.parent as ViewGroup
                linearLayoutSearchView.removeView(magImage)

                searchView.onActionViewExpanded()
                searchView.queryHint = "Search..."

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let { viewModel.searchNote(search = it) }

                        if (newText!!.isEmpty()) {
                            viewModel.getAllNotes()
                        }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        }, viewLifecycleOwner)

        addNoteBtn.setOnClickListener {
            viewModel.openAddNoteScreen()
        }

        viewModel.notesLiveData.observe(viewLifecycleOwner, observerList)

        homeAdapter.setLongClickListener {
            val currentNote = it
            val bottomSheetDialog = BottomSheetDialog(requireContext())


            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog)


            bottomSheetDialog.findViewById<AppCompatTextView>(R.id.text_pin_unpin)?.text = "Pin/UnPin"


            bottomSheetDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))






            bottomSheetDialog.findViewById<LinearLayoutCompat>(R.id.linePin)?.setOnClickListener {
                if (currentNote.isPin == 0) {
//                    currentNote.isPin = 1
//                    viewModel.updateNote(currentNote)
                    viewModel.pinNote(currentNote.id)
                } else {
//                    currentNote.isPin = 0
//                    viewModel.updateNote(currentNote)
                    viewModel.unPinNote(currentNote.id)
                }
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.findViewById<LinearLayoutCompat>(R.id.lineArchive)?.setOnClickListener {
                viewModel.archiveNote(currentNote.id)
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.findViewById<LinearLayoutCompat>(R.id.lineDelete)?.setOnClickListener  {
                viewModel.showDialog(requireContext(), currentNote.id, currentNote.title)
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show()
        }
        homeAdapter.setEditClickListener { note ->
            viewModel.openEditNote(note)
        }
        viewModel.searchNotesLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.imageEmptyBox.visibility = View.VISIBLE
            } else {
                binding.imageEmptyBox.visibility = View.GONE
            }
            homeAdapter.submitList(it)
        }


        viewModel.searchNotesLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.imageEmptyBox.visibility = View.VISIBLE
            } else {
                binding.imageEmptyBox.visibility = View.GONE
            }
            homeAdapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.openEditNoteScreenLiveData.collect {
                val action = HomeScreenDirections.actionHomeScreenToEditScreen(it)
                findNavController().navigate(action)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.openAddNoteScreenLiveData.collect {
                findNavController().navigate(R.id.action_homeScreen_to_addNoteScreen)
            }
        }




        binding.swiper.setOnRefreshListener {
            viewModel.getAllNotes()
            binding.swiper.isRefreshing = false
        }

    }
    private val openAddNoteObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_homeScreen_to_addNoteScreen)
    }
    private val observerList = Observer<List<NoteData>> {
        if (it.isEmpty()) {
            binding.imageEmptyBox.visibility = View.VISIBLE
        } else {
            binding.imageEmptyBox.visibility = View.GONE
            homeAdapter.submitList(it)
            binding.recyclerViewHome.adapter = homeAdapter
        }
        val layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.recyclerViewHome.layoutManager = layoutManager
        binding.recyclerViewHome.itemAnimator = DefaultItemAnimator()

        homeAdapter.submitList(it)
        binding.recyclerViewHome.adapter = homeAdapter
    }
}