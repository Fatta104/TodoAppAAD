package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var  detailTaskViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val viewModelFactory = ViewModelFactory.getInstance(this)
        detailTaskViewModel =
            ViewModelProvider(this, viewModelFactory)[DetailTaskViewModel::class.java]

        val id = intent.getIntExtra(TASK_ID, 0)
        detailTaskViewModel.setTaskId(id)

        detailTaskViewModel.task.observe(this) { task ->
            task?.let {
                val title = findViewById<TextInputEditText>(R.id.detail_ed_title)
                val description = findViewById<TextInputEditText>(R.id.detail_ed_description)
                val dueDate = findViewById<TextInputEditText>(R.id.detail_ed_due_date)

                title.setText(task.title)
                description.setText(task.description)
                dueDate.setText(DateConverter.convertMillisToString(task.dueDateMillis))
            }
        }

        val delete = findViewById<Button>(R.id.btn_delete_task)
        delete.setOnClickListener {
            detailTaskViewModel.deleteTask()
            finish()
        }
    }
}