package xyz.maoyanluo.datastagingarea

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.Method


class MainActivity : AppCompatActivity() {

    private var requestPermission: ActivityResultLauncher<Intent?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(MainActivityLifecycleObserver(this))
        val checkPermission = findViewById<Button>(R.id.check_permission)
        checkPermission.setOnClickListener {
            if (checkPermission()) {
                Toast.makeText(this@MainActivity, "Bingo!", Toast.LENGTH_SHORT).show()
            } else {
                requestPermission()
            }
        }
    }

    override fun onStart() {
        requestPermission = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
            if (checkPermission()) {
                Toast.makeText(this@MainActivity, "Bingo!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "您拒绝了权限!", Toast.LENGTH_SHORT).show()
            }
        }
        super.onStart()
    }

    private fun requestPermission() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        requestPermission?.launch(intent)
    }

    fun checkPermission():Boolean {
        return try {
            val clazz: Class<*> = Settings::class.java
            val canDrawOverlays: Method = clazz.getDeclaredMethod("canDrawOverlays", Context::class.java)
            canDrawOverlays.invoke(null, this) as Boolean
        } catch (e: Exception) {
            false
        }
    }

}