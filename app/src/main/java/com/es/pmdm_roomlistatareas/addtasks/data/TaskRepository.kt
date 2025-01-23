package com.es.pmdm_roomlistatareas.addtasks.data

import com.es.pmdm_roomlistatareas.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
//
//    //Podríamos haber creado directamente una lista de TaskEntity de la siguiente forma:
//    //val tasks: Flow<List<TaskModel>> = taskDao.getTasks()
//    //Pero a nivel de arquitectura no es una buena práctica por el acoplamiento de capas.
//    //Estamos en data, y ni dominio ni ui deberían conocer que existe un objeto TaskEntity...
//    //Para desarrollarlo correctamente, vamos a generar una lista de TaskModel y mapearemos
//    //los items del TaskEntity (data) al TaskModel (modelo de la ui)
//    //Nosotros vamos a mantener el mismo modelo (TaskModel) para la capa de dominio y ui,
//    //pero para la capa de data el modelo es diferente (TaskEntity).
//    //Un mapper es cuando recibes unos datos y los devuelves transformados para cada una de las capas.
//    //El método map es cómo un forEach, pero me va a devolver una lista de cada item
//    //con la transformación que le hagamos mediante la expresión lambda.
//    val tasks: Flow<List<TaskModel>> = taskDao.getTasks().map { items -> items.map { TaskModel(it.id, it.task, it.selected) } }
//
//    suspend fun add(taskModel: TaskModel) {
//        taskDao.addTask(
//            TaskEntity(taskModel.id, taskModel.task, taskModel.selected)
//        )
//    }
//
//    suspend fun update(taskModel: TaskModel) {
//        taskDao.updateTask(taskModel.toData())
//    }
//
//    suspend fun delete(taskModel: TaskModel) {
//        taskDao.deleteTask(taskModel.toData())
//    }
//}
//
///**
// * Esto es una función de extensión
// */
//fun TaskModel.toData(): TaskEntity {
//    return TaskEntity(this.id, this.task, this.selected)
//}