package pl.alexbul.bluetuzmodul


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.alexbul.bt_def.BaseActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, BaseActivity::class.java))
    }
}