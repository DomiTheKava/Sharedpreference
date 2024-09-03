package com.example.sharedpreference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch

class MainActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var pick_class: EditText
    lateinit var pick_num: EditText

    val sharedPreferenceName = "MySharedPreference"

    val dark_mode_key = "dark_mode"

    override fun onCreate(savedInstanceState: Bundle?) {
        val shared_preferance = getSharedPreferences(sharedPreferenceName, MODE_PRIVATE)
        val useDarkTheme = shared_preferance.getBoolean(dark_mode_key, false)
        if (useDarkTheme) {
            setTheme(androidx.appcompat.R.style.Base_ThemeOverlay_AppCompat_Dark)
        } else {
            setTheme(androidx.appcompat.R.style.Base_ThemeOverlay_AppCompat_Light)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.name1)
        pick_class = findViewById(R.id.name2)
        pick_num = findViewById(R.id.name3)

        val toggle = findViewById<Switch>(R.id.switch1)
        toggle.isChecked = useDarkTheme
        toggle.setOnCheckedChangeListener() {
            view, isChecked -> toggleTheme(isChecked)
        }
    }

    private fun toggleTheme(darkTheme: Boolean) {
        val editor = getSharedPreferences(sharedPreferenceName, MODE_PRIVATE).edit()



        editor.apply {
            putBoolean(dark_mode_key, darkTheme)
            apply()
        }

        // restart screen and start from scratch
        val intent = intent
        finish()
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val shared_preferance = getSharedPreferences(sharedPreferenceName, MODE_PRIVATE)

        val key1 = shared_preferance.getString("name", "") // default value
        val key2 = shared_preferance.getString("pick_class", "")
        val key3 = shared_preferance.getInt("pick_num", 0)


        // assign them

        name.setText(key1)
        pick_class.setText(key2)
        pick_num.setText(key3.toString() )

    }

    override fun onPause() {
        super.onPause()

        // open in private for writing
        val shared_preferance = getSharedPreferences(sharedPreferenceName, MODE_PRIVATE)
        val edit = shared_preferance.edit()

        //write
        edit.putString("name", name.text.toString())
        edit.putString("pick_class", pick_class.text.toString())
        edit.putInt("pick_num", pick_num.text.toString().toInt())

        // commit / apply
        edit.apply() // commits in backround

//        edit.commit() // commits and then proceeds
    }

//    override fun onStop() {
//        // save
//        super.onStop()
//    }
}