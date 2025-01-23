package com.es.pmdm_roomlistatareas.addtasks.domain

import com.es.pmdm_roomlistatareas.addtasks.data.TaskRepository
import com.es.pmdm_roomlistatareas.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para recuperar las tareas
 *
 * Para acceder al data vamos a necesitar el repositorio, ya que es nuestra puerta de entrada al data.
 * Gracias a Dagger Hilt lo vamos a inyectar en el constructor.
 */
class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskModel>> = taskRepository.tasks
}