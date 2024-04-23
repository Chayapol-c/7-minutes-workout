package com.example.a7minutesworkout.ui.exercise

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.R
import com.example.a7minutesworkout.ui.finish.FinishActivity
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import kotlin.math.round

@AndroidEntryPoint
class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExerciseBinding? = null

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null
    private val viewModel: ExerciseViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)

        binding?.apply {
            setContentView(root)
            setSupportActionBar(toolBarExercise)
            toolBarExercise.setNavigationOnClickListener {
                customDialogForBackButton()
            }
            flRestView.visibility = View.GONE
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        tts = TextToSpeech(this, this)

        setUpRestView()
        setUpRv()
        onBackPressedDispatcher.addCallback {
            customDialogForBackButton()
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.restTimer.observe(this) { millisUntilFinished ->
            val formatedTime = round((millisUntilFinished.div(1000) + 1).toDouble()).toInt()
            if (millisUntilFinished == 0L) {
                viewModel.updateCurrentExercise()
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
            binding?.apply {
                progressBar.progress = formatedTime
                tvTimer.text = formatedTime.toString()
            }
        }
        viewModel.exerciseTimer.observe(this) { millisUntilFinished ->
            val formatedTime = round((millisUntilFinished.div(1000) + 1).toDouble()).toInt()
            if (millisUntilFinished == 0L) {
                if (viewModel.isDoneAllExercises().not()) {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                } else {
                    viewModel.doneExercise()
                    setUpRestView()
                }
            }
            binding?.apply {
                progressBarExercise.progress = formatedTime
                tvTimerExercise.text = formatedTime.toString()
            }
        }
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnConfirm.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnReject.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setUpRestView() {
        try {
            val soundURI =
                Uri.parse("android.resource://com/example/a7minutesworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding?.apply {
            flRestView.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            tvExerciseName.visibility = View.INVISIBLE
            flExerciseView.visibility = View.INVISIBLE
            ivImage.visibility = View.INVISIBLE
            tvUpcomingLabel.visibility = View.VISIBLE
            tvUpcomingExerciseName.visibility = View.VISIBLE
        }
        viewModel.resetRestTimer()
        binding?.tvUpcomingExerciseName?.text = viewModel.getCurrentExercise()?.name
        setRestProgressBar()
    }

    private fun setUpRv() {
        exerciseAdapter = ExerciseStatusAdapter(viewModel.exerciseList.value!!)
        binding?.apply {
            rvExerciseStatus.apply {
                layoutManager = LinearLayoutManager(
                    this@ExerciseActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = exerciseAdapter
            }
        }
    }

    private fun setRestProgressBar() {
        viewModel.startRestTimer()
    }

    private fun setupExerciseView() {
        binding?.apply {
            flRestView.visibility = View.INVISIBLE
            tvTitle.visibility = View.INVISIBLE
            tvTitle.text = "Exercise Name"
            flExerciseView.visibility = View.VISIBLE
            tvExerciseName.visibility = View.VISIBLE
            ivImage.visibility = View.VISIBLE
            tvUpcomingLabel.visibility = View.INVISIBLE
            tvUpcomingExerciseName.visibility = View.INVISIBLE
        }

        viewModel.resetExerciseTimer()

        val currentExercise = viewModel.getCurrentExercise()

        currentExercise?.let { exercise ->
            speakOut(exercise.name)

            binding?.apply {
                ivImage.setImageResource(exercise.image)
                tvExerciseName.text = exercise.name
            }
        }

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {
        viewModel.startExerciseTimer()
    }


    override fun onDestroy() {
        viewModel.resetRestTimer()

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

//        if (player != null) {
//            player?.stop()
//        }
        super.onDestroy()
        binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "")
            }
        } else {
            Log.e("TTS", "")
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}