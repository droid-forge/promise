package promise.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.sephiroth.android.library.bottomnavigation.BottomNavigation
import kotlinx.android.synthetic.main.activity_main.*
import promise.app.R
import promise.app.ui.activity.login.LoginActivity
import promise.app.ui.fragment.HomeFragment
import promise.app.ui.fragment.di.DIFragment
import promise.app.ui.fragment.todo.TodoFragment
import promise.app.ui.fragment.wallet.WalletFragment
import promise.app.ui.fragment.weather.WeatherFragment


class MainActivity : BaseActivity() {

  private val onBottomMenuSelectListner: BottomNavigation.OnMenuItemSelectionListener = object:  BottomNavigation.OnMenuItemSelectionListener {
    override fun onMenuItemSelect(itemId: Int, position: Int, fromUser: Boolean) {
      when (itemId) {
        /* R.id.navigation_home -> {
           title = "Home"
           viewpager.setCurrentItem(0, true)
           return@OnNavigationItemSelectedListener true
         }
         R.id.navigation_di -> {
           title = "DI"
           viewpager.setCurrentItem(1, true)
           return@OnNavigationItemSelectedListener true
         }*/
        R.id.navigation_todo -> {
          title = "Todo"
          viewpager.setCurrentItem(2, true)
        }
        R.id.navigation_weather -> {
          title = "Weather"
          viewpager.setCurrentItem(3, true)
        }
        R.id.navigation_wallet -> {
          title = "Wallet"
          viewpager.setCurrentItem(4, true)
        }
      }
    }

    override fun onMenuItemReselect(itemId: Int, position: Int, fromUser: Boolean) {
      when (itemId) {
        /* R.id.navigation_home -> {
           title = "Home"
           viewpager.setCurrentItem(0, true)
           return@OnNavigationItemSelectedListener true
         }
         R.id.navigation_di -> {
           title = "DI"
           viewpager.setCurrentItem(1, true)
           return@OnNavigationItemSelectedListener true
         }*/
        R.id.navigation_todo -> {
          title = "Todo"
          viewpager.setCurrentItem(2, true)
        }
        R.id.navigation_weather -> {
          title = "Weather"
          viewpager.setCurrentItem(3, true)
        }
        R.id.navigation_wallet -> {
          title = "Wallet"
          viewpager.setCurrentItem(4, true)
        }
      }
    }
  }

  private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
     /* R.id.navigation_home -> {
        title = "Home"
        viewpager.setCurrentItem(0, true)
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_di -> {
        title = "DI"
        viewpager.setCurrentItem(1, true)
        return@OnNavigationItemSelectedListener true
      }*/
      R.id.navigation_todo -> {
        title = "Todo"
        viewpager.setCurrentItem(0, true)
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_weather -> {
        title = "Weather"
        viewpager.setCurrentItem(1, true)
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_wallet -> {
        title = "Wallet"
        viewpager.setCurrentItem(2, true)
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val actionBar = supportActionBar
    actionBar!!.setIcon(R.drawable.ic_assistant)
    actionBar.setDisplayShowHomeEnabled(true)
    setContentView(R.layout.activity_main)
    nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    viewpager.adapter = SectionsPagerAdapter(supportFragmentManager)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == R.id.action_account) {

      startActivity(Intent(this, LoginActivity::class.java))
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  inner class SectionsPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    /*private var homeFragment: HomeFragment = HomeFragment.newInstance()
    private var diFragment: DIFragment = DIFragment.newInstance()*/
    private var todoFragment: TodoFragment = TodoFragment.newInstance()
    private var weatherFragment: WeatherFragment = WeatherFragment.newInstance()
    private var walletFragment: WalletFragment = WalletFragment.newInstance()

    override fun getItem(position: Int): Fragment = when (position) {
      /*0 -> homeFragment
      1 -> diFragment*/
      0 -> todoFragment
      1 -> weatherFragment
      2 -> walletFragment
      else -> throw IllegalArgumentException("Only allowed three fragments")
    }

    override fun getCount(): Int = 3
  }
}

