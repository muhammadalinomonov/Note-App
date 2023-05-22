package uz.gita.noteapp.presentation.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.noteapp.R

class MyBottomSheetDialog (context:Context):/*BottomSheetDialogFragment(R.layout.bottom_sheet_dialog)*/BottomSheetDialog(context){
    private var clickPinButtonListener : (()-> Unit)? = null
    private var clickArchiveButtonListener : (()-> Unit)? = null
    fun setClickPinButtonListener(block: () -> Unit) {
        clickPinButtonListener = block
    }


    fun setClickArchiveButtonListener(block: () -> Unit) {
        clickArchiveButtonListener = block
    }




    private var clickDeleteButtonListener : (()-> Unit)? = null
    fun setClickDeleteButtonListener(block: () -> Unit) {
        clickDeleteButtonListener = block
    }

    private var isPin = 0

    @SuppressLint("SetTextI18n")
    fun isPin(pin: Int) {
        isPin = pin
    }


    @SuppressLint("SetTextI18n")
     fun onViewCreated(view: View) {


        if (isPin == 1) {
            view.findViewById<AppCompatTextView>(R.id.text_pin_unpin).text = "UnPin"
        } else {
            view.findViewById<AppCompatTextView>(R.id.text_pin_unpin).text = "Pin"
        }

        view.findViewById<LinearLayoutCompat>(R.id.linePin).setOnClickListener {
            clickPinButtonListener?.invoke()
        }

        view.findViewById<LinearLayoutCompat>(R.id.lineDelete).setOnClickListener {
            clickDeleteButtonListener?.invoke()
        }
        view.findViewById<LinearLayoutCompat>(R.id.lineArchive).setOnClickListener {
            clickArchiveButtonListener?.invoke()
        }

    }
}
