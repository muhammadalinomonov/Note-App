package uz.gita.noteapp.presentation.screen.archive

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.noteapp.R
import uz.gita.noteapp.databinding.ScreenArchiveBinding
import uz.gita.noteapp.presentation.adapter.TrashAdapter
import uz.gita.noteapp.presentation.screen.archive.viewmodel.ArchiveViewModel
import uz.gita.noteapp.presentation.screen.archive.viewmodel.impl.ArchiveViewModelImpl
import uz.gita.noteapp.utils.myApply

class Archives:Fragment(R.layout.screen_archive) {
    private val viewModel: ArchiveViewModel by viewModels<ArchiveViewModelImpl>()
    private val binding by viewBinding(ScreenArchiveBinding::bind)
    private val trashAdapter by lazy { TrashAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {




        viewModel.notesInArchive.observe(viewLifecycleOwner) {
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