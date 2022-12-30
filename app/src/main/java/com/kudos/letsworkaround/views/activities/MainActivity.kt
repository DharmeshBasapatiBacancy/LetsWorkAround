package com.kudos.letsworkaround.views.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.kudos.letsworkaround.databinding.ActivityMainBinding
import com.kudos.letsworkaround.viewmodels.DevBytesViewModel
import com.kudos.letsworkaround.worker.MyTestWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val devBytesViewModel: DevBytesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //observePlaylist()
        //startOneTimeWorker()
        startPeriodicWorker()
    }

    private fun startOneTimeWorker() {
        val workManager = WorkManager.getInstance(this)

        val constraints = Constraints.Builder()
            //.setRequiresBatteryNotLow(true)
            //.setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            //.setRequiresStorageNotLow(true)
            //.setRequiresDeviceIdle(true)
            .build()

        val myTestWorkerRequest = OneTimeWorkRequestBuilder<MyTestWorker>()
            .setInputData(workDataOf("myKey" to "myValue"))
            .setConstraints(constraints)
            .build()

        workManager.enqueue(myTestWorkerRequest)

        workManager.getWorkInfoByIdLiveData(myTestWorkerRequest.id).observe(this) { workInfo ->
            if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                val workerResult = workInfo.outputData.getString("workerKey")
                Log.d("TAG", "IN ACTIVITY: $workerResult")
            } else {
                Log.d("TAG", "IN ACTIVITY: ${workInfo.state}")
            }
        }

    }

    private fun startPeriodicWorker() {
        val workManager = WorkManager.getInstance(this)

        val constraints = Constraints.Builder()
            //.setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            //.setRequiresCharging(true)
            //.setRequiresStorageNotLow(true)
            //.setRequiresDeviceIdle(true)
            .build()

        val myTestWorkerRequest = PeriodicWorkRequestBuilder<MyTestWorker>(15, TimeUnit.MINUTES)
            .setInputData(workDataOf("myKey" to "myValue"))
            .setConstraints(constraints)
            .build()

        workManager.enqueue(myTestWorkerRequest)

        workManager.getWorkInfoByIdLiveData(myTestWorkerRequest.id).observe(this) { workInfo ->
            Log.d("TAG", "IN ACTIVITY: ${workInfo.state}")
            if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                val workerResult = workInfo.outputData.getString("workerKey")
                Log.d("TAG", "IN ACTIVITY: $workerResult")
            }
        }


    }

    private fun observePlaylist() {
        lifecycleScope.launch {

            devBytesViewModel.getPlaylist()

            devBytesViewModel.playlist.observe(this@MainActivity) {
                Log.d("TAG", "observePlaylist: ${it.data}")
            }
        }
    }
}