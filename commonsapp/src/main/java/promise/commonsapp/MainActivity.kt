package promise.commonsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import kotlinx.android.synthetic.main.activity_main.*
import promise.pref.Preferences

class MainActivity : AppCompatActivity() {

  private val preferences: Preferences = Preferences(PREFERENCE_NAMR)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    preferences.save(ArrayMap<String, Any>().apply {
      put("somekey", "key")
      put("somekey1", "key1")
      put("somekey2", "key3")
    })
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    preferences_textview.text = preferences.all.toString()
  }

  companion object {
    const val PREFERENCE_NAMR = "pref_name"
  }
}
