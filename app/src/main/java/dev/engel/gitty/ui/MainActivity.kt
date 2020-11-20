package dev.engel.gitty.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.engel.gitty.R
import dev.engel.gitty.di.AutoInject

class MainActivity : AppCompatActivity(), AutoInject {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}

