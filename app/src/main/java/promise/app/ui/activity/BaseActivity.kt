package promise.app.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import promise.app.App

open class BaseActivity : AppCompatActivity(), LifecycleOwner {
  private lateinit var lifecycleRegistry: LifecycleRegistry

  lateinit var app: App

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    app = application as App
    lifecycleRegistry = LifecycleRegistry(this)
    lifecycleRegistry.markState(Lifecycle.State.CREATED)
  }

  public override fun onStart() {
    super.onStart()
    lifecycleRegistry.markState(Lifecycle.State.STARTED)
  }

  override fun getLifecycle(): Lifecycle {
    return lifecycleRegistry
  }
}