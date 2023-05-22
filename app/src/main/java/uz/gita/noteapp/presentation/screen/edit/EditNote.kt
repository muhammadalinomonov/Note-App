package uz.gita.noteapp.presentation.screen.edit

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.noteapp.R
import uz.gita.noteapp.data.model.NoteData
import uz.gita.noteapp.data.source.local.convertor.DateConvertor
import uz.gita.noteapp.databinding.ScreenEditBinding
import uz.gita.noteapp.presentation.adapter.RichFeatureAdapter
import uz.gita.noteapp.presentation.adapter.RichFeatureAdapter.*
import uz.gita.noteapp.presentation.adapter.data.RichFeatureType.*
import uz.gita.noteapp.presentation.screen.edit.viewmodel.EditScreenViewModel
import uz.gita.noteapp.presentation.screen.edit.viewmodel.impl.EditScreenViewModelImpl

class EditNote : Fragment(R.layout.screen_edit) {
    private val binding by viewBinding<ScreenEditBinding>()
    private val viewModel: EditScreenViewModel by viewModels<EditScreenViewModelImpl>()
    private val args: EditNoteArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        title.setText(args.note.title)
        richEditor.html = args.note.content
        init()
        setListener()

        richEditor.setPadding(20, 10, 20, 10)

        context?.let { ContextCompat.getColor(it, R.color.window_background) }
            ?.let { richEditor.setEditorBackgroundColor(it) }

        context?.let { ContextCompat.getColor(it, R.color.noteItemTextColor) }
            ?.let { richEditor.setEditorFontColor(it) }

        binding.editNoteBtn.setOnClickListener {
            if (title.text.toString().isEmpty() || etContent.editText?.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Fieldlarni to'ldiring", Toast.LENGTH_SHORT).show()
            } else {
                val title = binding.title.text.toString().trim()
                val content = binding.richEditor.html.toString().trim()
                val time = DateConvertor.getCurrentTime()
                viewModel.updateNote(NoteData(id = args.note.id, title = title, content = content, createdAt = time, 0,0))
                Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.backToHomeScreen.observe(viewLifecycleOwner, backToHomeScreenObserve)
    }

    private var adapter: RichFeatureAdapter? = null
    private fun init() {
        val data = viewModel.getRichFeatures()
        adapter = RichFeatureAdapter()
        binding.rvRich.adapter = adapter
        adapter?.submitList(data)
    }

    private fun setListener() {
        adapter?.setSelectListener { type ->
            when (type) {
                BOLD -> binding.richEditor.setBold()
                ITALIC -> binding.richEditor.setItalic()
                SUBSCRIPT -> binding.richEditor.setSubscript()
                SUPERSCRIPT -> binding.richEditor.setSuperscript()
                STRIKETHROUGH -> binding.richEditor.setStrikeThrough()
                UNDERLINE -> binding.richEditor.setUnderline()
                H1 -> binding.richEditor.setHeading(1)
                H2 -> binding.richEditor.setHeading(2)
                H3 -> binding.richEditor.setHeading(3)
                H4 -> binding.richEditor.setHeading(4)
                H5 -> binding.richEditor.setHeading(5)
                H6 -> binding.richEditor.setHeading(6)
                JUSTIFYCENTER -> binding.richEditor.setAlignCenter()
                JUSTIFYLEFT -> binding.richEditor.setAlignLeft()
                JUSTIFYRIGHT -> binding.richEditor.setAlignRight()
                else -> { }
            }
        }

        binding.richEditor.setOnFocusChangeListener { view, focused ->
            binding.rvRich.isVisible = focused
        }
    }

    private val backToHomeScreenObserve = Observer<Unit> {
        findNavController().navigateUp()
    }
}