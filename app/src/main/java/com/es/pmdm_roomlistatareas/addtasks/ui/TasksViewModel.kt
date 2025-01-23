package com.es.pmdm_roomlistatareas.addtasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.es.pmdm_roomlistatareas.addtasks.domain.AddTaskUseCase
import com.es.pmdm_roomlistatareas.addtasks.domain.DeleteTaskUseCase
import com.es.pmdm_roomlistatareas.addtasks.domain.GetTasksUseCase
import com.es.pmdm_roomlistatareas.addtasks.domain.UpdateTaskUseCase
import com.es.pmdm_roomlistatareas.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.es.pmdm_roomlistatareas.addtasks.ui.TaskUiState.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//El parámetro getTasksUseCase del constructor se inyecta sin private val porque no nos hace falta
// ya que lo vamos a utilizar directamente en la variable uiState que gestionará los estados de la ui.
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTasksUseCase: GetTasksUseCase
): ViewModel() {

    //El caso de uso getTasksUseCase() nos devuelve el Flow continuo y cada vez que actualice
    //los datos va a pasarlo a Success (ver data class en TaskUiState).
    //Si por algún motivo falla y existe algún error, lo vamos a capturar y enviar al estado Error
    //con el parámetro de la excepción que ha generado.
    //El último modificador hará que cuando mi app o la pantalla esté en segundo plano hasta que no
    //pasen 5 segundos no bloqueará o cancelará el Flow (por defecto es 0)
    //Por ejemplo nuestra app pasará a segundo plano si nos llaman, o si desplegamos el menú superior
    //para ver una notificación o un whatsapp, etc.
    //Con stateIn, en el último argumento, también estamos asignando el estado inicial a Loading.
    val uiState: StateFlow<TaskUiState> = getTasksUseCase().map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private val _myTaskText = MutableLiveData<String>()
    val myTaskText: LiveData<String> = _myTaskText

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated() {
        onDialogClose()
        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = _myTaskText.value ?: ""))
        }
        _myTaskText.value = ""
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onItemRemove(taskModel: TaskModel) {
        viewModelScope.launch {
            deleteTaskUseCase(taskModel)
        }
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        viewModelScope.launch {
            updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
        }
    }

    fun onTaskTextChanged(taskText: String) {
        _myTaskText.value = taskText
    }

}