package com.kudos.letsworkaround.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.kudos.letsworkaround.repository.MainRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class MyTestWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val mainRepository: MainRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val inputValue = inputData.getString("myKey")
        Log.d("TAG", "IN WORKER - $inputValue")

        val response = mainRepository.getPlaylist()
        Log.d("TAG", "doWork: API RESPONSE RECD")

        return Result.success(workDataOf("workerKey" to "workerResult"))
    }

}