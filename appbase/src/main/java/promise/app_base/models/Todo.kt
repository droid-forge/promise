package promise.app_base.models

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.CheckedTextView
import androidx.appcompat.widget.SwitchCompat
import promise.app_base.R

import promise.model.SModel
import promise.model.Searchable

class Todo : SModel, Searchable {
  private var category: String? = null
  private var title: String? = null
  private var completed: Boolean = false

  private var index: Int = 0

  fun category(): String? = category

  fun category(category: String): Todo {
    this.category = category
    return this
  }

  fun name(): String? = title

  fun name(name: String): Todo {
    this.title = name
    return this
  }

  fun completed(): Boolean = completed

  fun completed(completed: Boolean): Todo {
    this.completed = completed
    return this
  }

  override fun onSearch(query: String): Boolean =
      name()?.contains(query) ?: category?.contains(query) ?: false

  private lateinit var switchCompat: SwitchCompat
  private lateinit var textView: CheckedTextView
  override fun layout(): Int = R.layout.todo_layout

  override fun init(view: View) {
    switchCompat = view.findViewById(R.id.checked)
    textView = view.findViewById(R.id.titleText)
  }

  override fun bind(view: View?) {
      switchCompat.isChecked = completed
      textView.text = name()
  }

  override fun index(index: Int) {
    this.index = index
  }

  override fun index(): Int = index

  override fun describeContents(): Int = 0

  override fun writeToParcel(dest: Parcel, flags: Int) {
    super.writeToParcel(dest, flags)
    dest.writeString(this.category)
    dest.writeString(this.title)
    dest.writeByte(if (this.completed) 1.toByte() else 0.toByte())
    dest.writeInt(this.index)
  }

  override fun toString(): String {
    return "Todo{" +
        "category='" + category + '\''.toString() +
        ", namee='" + title + '\''.toString() +
        ", completed=" + completed +
        ", index=" + index +
        '}'.toString()
  }

  constructor()

  private constructor(`in`: Parcel) : super(`in`) {
    this.category = `in`.readString()
    this.title = `in`.readString()
    this.completed = `in`.readByte().toInt() != 0
    this.index = `in`.readInt()
  }

  companion object CREATOR : Parcelable.Creator<Todo> {
    override fun createFromParcel(parcel: Parcel): Todo {
      return Todo(parcel)
    }

    override fun newArray(size: Int): Array<Todo?> {
      return arrayOfNulls(size)
    }
  }
}
