package uz.gita.noteapp.presentation.screen.addnote

import android.annotation.SuppressLint
import uz.gita.noteapp.presentation.adapter.data.RichFeatureType.*
import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.noteapp.R
import uz.gita.noteapp.data.model.NoteData
import uz.gita.noteapp.data.source.local.convertor.DateConvertor
import uz.gita.noteapp.databinding.ScreenAddBinding
import uz.gita.noteapp.presentation.adapter.RichFeatureAdapter
import uz.gita.noteapp.presentation.screen.addnote.viewmodel.AddViewModel
import uz.gita.noteapp.presentation.screen.addnote.viewmodel.impl.AddViewModelImpl
import uz.gita.noteapp.utils.myApply


class AddNote : Fragment(R.layout.screen_add) {
    private val viewModel: AddViewModel by viewModels<AddViewModelImpl>()
    private val binding by viewBinding(ScreenAddBinding::bind)
    private lateinit var dialog: Dialog
    private var isPin = true
    private var isSelected = true
    private lateinit var parentLinearLayout: LinearLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.closeAddNoteScreen.observe(this, closeAddNoteObserver)
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        init()
        setListener()
        addNoteBtn.setOnClickListener {
            if (saveNote()) {
                return@setOnClickListener
            }
            viewModel.closeAddNote()
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.more_menu, menu)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {


                    R.id.action_undo -> {
                        binding.richEditor.undo()
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner)

        richEditor.setBullets()

        richEditor.setPadding(20, 10, 20, 10)
        richEditor.setBackgroundColor(Color.WHITE)
        richEditor.setPlaceholder("Insert content here...")

        context?.let { ContextCompat.getColor(it, R.color.window_background) }
            ?.let { richEditor.setEditorBackgroundColor(it) }

        context?.let { ContextCompat.getColor(it, R.color.noteItemTextColor) }
            ?.let { richEditor.setEditorFontColor(it) }
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

    private var adapter: RichFeatureAdapter? = null
    private fun init() {
        val data = viewModel.getRichFeatures()
        adapter = RichFeatureAdapter()
        binding.rvRich.adapter = adapter
        adapter?.submitList(data)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveNote(): Boolean {
        val title = binding.etTitle.editText?.text.toString().trim()
        val time = DateConvertor.getCurrentTime()

        if (title.isEmpty()) {
            binding.etTitle.error = "Title must not be empty"
            binding.etTitle.requestFocus()
            return true
        }

        val content = if (binding.richEditor.html == null) "" else binding.richEditor.html.toString().trim()

        if (!isPin) {
            viewModel.addNote(
                NoteData(
                    title = title,
                    content = content,
                    createdAt = time,
                    isPin = 1
                )
            )
            isPin = false
        } else {

            viewModel.addNote(
                NoteData(
                    title = title,
                    content = content,
                    createdAt = time,
                    isPin = 0
                )
            )
            isPin = false
        }
        return false
    }





    private val closeAddNoteObserver = Observer<Unit> {
        findNavController().navigateUp()
    }


}