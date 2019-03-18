package com.qontak.assignment.favoritepage

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.qontak.assignment.R

import kotlinx.android.synthetic.main.activity_favorite.*

interface FavoriteView {
    fun showList()
}

class FavoriteActivity : AppCompatActivity(), FavoriteView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
