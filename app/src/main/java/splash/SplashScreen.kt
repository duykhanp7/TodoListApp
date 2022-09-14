package splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.HandlerThread
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.todolistapp.R
import com.example.todolistapp.databinding.ActivitySplashScreenBinding
import main.MainActivity
import java.util.logging.Handler

class SplashScreen : AppCompatActivity() {

    //PROPERTIES
    private lateinit var binding:ActivitySplashScreenBinding

    //ON CREATE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen)

        //CHANGE STATUS BAR COLOR
        window.statusBarColor = ContextCompat.getColor(applicationContext,R.color.background_splash)

        //SET DELAY AND CHANGE ACTIVITY
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }, 2000)
    }
}