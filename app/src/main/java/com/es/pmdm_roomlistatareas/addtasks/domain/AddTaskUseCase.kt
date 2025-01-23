package com.es.pmdm_roomlistatareas.addtasks.domain

import com.es.pmdm_roomlistatareas.addtasks.data.TaskRepository
import com.es.pmdm_roomlistatareas.addtasks.ui.model.TaskModel
import javax.inject.Inject

/**
 * Caso de uso para añadir una tarea
 */
class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.add(taskModel)
    }
}