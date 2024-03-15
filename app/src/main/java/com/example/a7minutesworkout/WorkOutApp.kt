package com.example.a7minutesworkout

import android.app.Application
import com.example.a7minutesworkout.history.HistoryDatabase

class WorkOutApp: Application() {

    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}