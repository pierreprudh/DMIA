package com.pp.todopierre.data

import com.pp.todopierre.list.Task
import retrofit2.Response
import retrofit2.http.GET

interface TasksWebService {
    @GET("/rest/v2/tasks/")
    suspend fun fetchTasks(): Response<List<Task>>
}