package dev.engel.gitty.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.engel.gitty.R
import dev.engel.gitty.di.AutoInject
import dev.engel.gitty.repository.ViewerCardRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity(), AutoInject {

    @Inject
    lateinit var viewerCardRepository: ViewerCardRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainScope().launch {
            val result = viewerCardRepository.retrieve()
            name.text = "Hello, ${result.viewer.name}"
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}

