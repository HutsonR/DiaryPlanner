package com.example.diaryplanner

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment


class AddDialogFragment(private val layoutResourceId: Int) : DialogFragment() {
    private val TAG = "debugTag"
    private var editTextValue: String = ""
    private lateinit var editText: TextView
//    private lateinit var dialogListener: DialogListener

    //  проверка, что активити, вызывающая DialogFragment, реализует интерфейс DialogListener
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        try {
//            dialogListener = parentFragment as DialogListener
//        } catch (e: ClassCastException) {
//            throw ClassCastException("Parent fragment must implement DialogListener")
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutResourceId, container, false)

        val saveButton = view.findViewById<ImageButton>(R.id.add_save)
        val cancelButton = view.findViewById<ImageButton>(R.id.add_close)
        saveButton.setOnClickListener {
            handleSaveButtonClicked()
            dismiss()
        }
        cancelButton.setOnClickListener {
            dismiss()
        }

        // Изначально деактивируем кнопку "Сохранить"
        updateSaveButtonState(saveButton)


        return view
    }


    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window?.setLayout(width, height)
    }

    private fun updateSaveButtonState(saveButton: ImageButton) {
//        val isButtonSelected = selectedButtonId != 0
//        val isEditTextFilled = editTextValue.isNotEmpty()

        saveButton.isEnabled = false
    }


    private fun handleSaveButtonClicked() {
//        dialogListener.onConfirmAddDialogResult(editTextValue)
    }

}