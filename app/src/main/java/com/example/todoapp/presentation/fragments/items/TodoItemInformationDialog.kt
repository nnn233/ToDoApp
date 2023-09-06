package com.example.todoapp.presentation.fragments.items

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.todoapp.R
import com.example.todoapp.presentation.fragments.converters.LongDateToStringConverter
import com.example.todoapp.presentation.fragments.todo_item.TodoItemUIState

class TodoItemInformationDialog(
    private val x: Int,
    private val y: Int,
    private val todoItem: TodoItemUIState
) : DialogFragment() {
    private lateinit var creationText: TextView
    private lateinit var modificationText: TextView
    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.information_dialog, container, false)
        creationText = view.findViewById(R.id.creation_text)
        modificationText = view.findViewById(R.id.modification_text)
        creationText.text = activity?.applicationContext?.getString(
            R.string.creation_date,
            LongDateToStringConverter.convertLongToDate(todoItem.creationDate)
        )
        modificationText.text = activity?.applicationContext?.getString(
            R.string.modification_date,
            LongDateToStringConverter.convertLongToDate(todoItem.modificationDate)
        )
        setDialogPosition()
        return view
    }

    private fun setDialogPosition() {
        val window = dialog?.window
        val params=window?.attributes
        window?.setGravity(Gravity.TOP)
        params?.x = x-dpToPx(265)
        params?.y = y-dpToPx(10)
        Log.i("Location", "x=${params?.x} y=${params?.y}")
        window?.attributes = params
    }

    private fun dpToPx(valueInDp:Int):Int {
        val metrics = activity?.resources?.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp.toFloat(), metrics).toInt()
    }
}