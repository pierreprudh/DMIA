package com.pp.todopierre.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pp.todopierre.detail.ui.theme.TodoPierreTheme
import com.pp.todopierre.list.Task
import java.util.UUID

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inittask = intent.getSerializableExtra("task") as Task?
            ?: Task(id = UUID.randomUUID().toString(), title = "", description = "")

        setContent {
            TodoPierreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Detail(onValidate = {task ->
                        intent.putExtra("task", task)
                        setResult(RESULT_OK, intent )
                        finish()
                    },
                        initTask = inittask
                    )
                }
            }
        }
    }
}

@Composable
fun Detail(modifier: Modifier = Modifier, onValidate: (Task) -> Unit, initTask : Task) {

    var task by remember { mutableStateOf(initTask)}

    Column(
        modifier = Modifier
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Task Detail",
            modifier = modifier,
            style = MaterialTheme.typography.headlineLarge
        )

        OutlinedTextField(
            value = task.title ?: "",
            onValueChange = { task = task.copy(title = it) },
            label = { Text("Title") }
        )

        OutlinedTextField(
            value = task.description ?: "",
            onValueChange = { task = task.copy(description = it) },
            label = { Text("Description") },
        )

        Button(
            onClick = {onValidate(task)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Valider", )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    TodoPierreTheme {
        Detail(onValidate = {}, initTask = Task(id = "prout", title = "pas important"))
    }
}