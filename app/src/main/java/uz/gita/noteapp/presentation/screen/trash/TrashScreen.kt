package uz.gita.noteapp.presentation.screen.trash

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.noteapp.R
import uz.gita.noteapp.databinding.ScreenTrashBinding
import uz.gita.noteapp.presentation.adapter.TrashAdapter
import uz.gita.noteapp.presentation.screen.trash.viewmodel.TrashViewModel
import uz.gita.noteapp.presentation.screen.trash.viewmodel.impl.TrashViewModelImpl
import uz.gita.noteapp.utils.myApply

class TrashScreen : Fragment(R.layout.screen_trash) {
    private val viewModel: TrashViewModel by viewModels<TrashViewModelImpl>()
    private val binding by viewBinding(ScreenTrashBinding::bind)
    private val trashAdapter by lazy { TrashAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {


        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.trash_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.clear_notes -> {
                        viewModel.notesInTrash.observe(viewLifecycleOwner) {
                            if (it.isEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    "Trash is empty",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                viewModel.showDeleteDialogNotesInTrash(requireContext())
                            }
                        }
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner)

        viewModel.notesInTrash.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.imageRecyclerBin.visibility = View.VISIBLE
                trashAdapter.submitList(it)
                recyclerViewTrash.adapter = trashAdapter
            } else {
                binding.imageRecyclerBin.visibility = View.GONE
                trashAdapter.submitList(it)
                recyclerViewTrash.adapter = trashAdapter
            }
        }

        trashAdapter.setLongClickListener {
            viewModel.showDialog(requireContext(), it.id, it.title)
        }
    }
}