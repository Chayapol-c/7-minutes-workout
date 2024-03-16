package com.example.a7minutesworkout.ui.exercise

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.data.exercise.Constants
import com.example.a7minutesworkout.data.exercise.ExerciseModel
import com.example.a7minutesworkout.ui.finish.FinishActivity
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress: Int = 0
    private var restTimerDuration: Long = 3

    private var excerciseTimer: CountDownTimer? = null
    private var excerciseProgress: Int = 0
    private var exerciseTimerDuration: Long = 10

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null


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

        exerciseList = Constants.defaultExerciseList()

        tts = TextToSpeech(this, this)

        setUpRestView()
        setUpRv()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
        super.onBackPressed()
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
//        try {
//            val soundURI =
//                Uri.parse("android.resource://com/example/a7minutesworkout/" + R.raw.press_start)
//            player = MediaPlayer.create(applicationContext, soundURI)
//            player?.isLooping = false
//            player?.start()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
        binding?.apply {
            flRestView.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            tvExerciseName.visibility = View.INVISIBLE
            flExerciseView.visibility = View.INVISIBLE
            ivImage.visibility = View.INVISIBLE
            tvUpcomingLabel.visibility = View.VISIBLE
            tvUpcomingExerciseName.visibility = View.VISIBLE
        }
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition + 1].name
        setRestProgressBar()
    }

    private fun setUpRv() {
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
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
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(restTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = restTimerDuration.toInt() - restProgress
                binding?.tvTimer?.text = (restTimerDuration.toInt() - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].isSelected = true
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()
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
        if (excerciseTimer != null) {
            excerciseTimer?.cancel()
            excerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].name)

        binding?.apply {
            ivImage.setImageResource(exerciseList!![currentExercisePosition].image)
            tvExerciseName.text = exerciseList!![currentExercisePosition].name
        }
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = excerciseProgress
        excerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                excerciseProgress++
                binding?.progressBarExercise?.progress =
                    exerciseTimerDuration.toInt() - excerciseProgress
                binding?.tvTimerExercise?.text =
                    (exerciseTimerDuration.toInt() - excerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList!!.size - 1) {
                    exerciseList!![currentExercisePosition].isSelected = false
                    exerciseList!![currentExercisePosition].isCompleted = true
                    exerciseAdapter?.notifyDataSetChanged()
                    setUpRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }


    override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

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