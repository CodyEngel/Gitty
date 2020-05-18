package dev.engel.gitty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import dev.engel.gitty.R
import dev.engel.gitty.ui.core.list.MutableListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listViewModel = SampleListViewModel()
        sampleItems.forEach { listViewModel.add(it) }
        val adapter = SampleAdapter(listViewModel)
        sampleList.layoutManager = LinearLayoutManager(this)
        sampleList.adapter = adapter

        MainScope().launch {
            while (true) {
                delay(5000L)
                listViewModel.replaceAll(sampleItems.shuffled())
            }
        }
    }
}

class SampleListViewModel : MutableListViewModel<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

val sampleItems = listOf(
    "draconian",
    "trap",
    "lake",
    "regret",
    "ancient",
    "distribution",
    "wrathful",
    "oval",
    "stingy",
    "hideous",
    "rustic",
    "known",
    "swot",
    "cultured",
    "temper",
    "advertisement",
    "optimal",
    "average",
    "splendid",
    "empower",
    "hum",
    "grade",
    "rub",
    "suppose",
    "curve",
    "reach",
    "frightened",
    "impossible",
    "light",
    "notebook",
    "kitten",
    "skirt",
    "unit",
    "bikes",
    "dine",
    "kitten",
    "horrible",
    "resemble",
    "gain",
    "unequaled",
    "faulty",
    "insurance",
    "abashed",
    "tub",
    "mature",
    "motion",
    "prose",
    "macho",
    "expansion",
    "cute",
    "bottle",
    "preach",
    "intelligent",
    "injure",
    "riddle",
    "toothsome",
    "board",
    "hospital",
    "irritating",
    "disgust",
    "idealize",
    "meal",
    "stride",
    "lucky",
    "hateful",
    "thundering",
    "lazy",
    "languid",
    "normal",
    "distinct",
    "highfalutin",
    "elderly",
    "opt",
    "badge",
    "fifth",
    "vast",
    "start",
    "limping",
    "town",
    "lead",
    "spectacular",
    "betray",
    "rush",
    "place",
    "distribute",
    "annoying",
    "justify",
    "solid",
    "channel",
    "help",
    "spread",
    "rainy",
    "understand",
    "come",
    "hurt",
    "string",
    "longing",
    "ill-fated",
    "industrious",
    "unnatural",
    "summer",
    "hard-to-find",
    "value",
    "dead",
    "help",
    "join",
    "hook",
    "shoes",
    "good",
    "imperil",
    "envy",
    "nation",
    "deer",
    "pocket",
    "gorgeous",
    "flee",
    "son",
    "quince",
    "endurable",
    "geese",
    "cooing",
    "convince",
    "sleepy",
    "determined",
    "convey",
    "shock",
    "thoughtful"
)
