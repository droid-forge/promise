package promise.app_base.models

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.CheckedTextView
import androidx.appcompat.widget.SwitchCompat
import promise.app_base.R
import promise.model.SModel
import promise.model.Searchable

class Task() : SModel(), Searchable {
  override fun index(index: Int) {
   this.index = index
  }

  override fun index(): Int {
    return index
  }

  override fun layout(): Int = R.layout.todo_layout

  private var category: String? = null
  private var title: String? = null
  private var completed: Boolean = false

  private var index: Int = 0

  fun category(): String? = category

  fun category(category: String): Task {
    this.category = category
    return this
  }

  fun name(): String? = title

  fun name(name: String): Task {
    this.title = name
    return this
  }

  fun completed(): Boolean = completed

  fun completed(completed: Boolean): Task {
    this.completed = completed
    return this
  }

  override fun onSearch(query: String): Boolean =
      name()?.contains(query) ?: category?.contains(query) ?: false

  private lateinit var switchCompat: SwitchCompat
  private lateinit var textView: CheckedTextView

  constructor(parcel: Parcel) : this() {
    category = parcel.readString()
    title = parcel.readString()
    completed = parcel.readByte() != 0.toByte()
    index = parcel.readInt()
  }

  override fun init(view: View) {
    switchCompat = view.findViewById(R.id.checked)
    textView = view.findViewById(R.id.titleText)
  }

  override fun bind(view: View, args: Any?) {
      switchCompat.isChecked = completed
      textView.text = name()
  }

  override fun toString(): String = "Todo{" +
      "category='" + category + '\''.toString() +
      ", namee='" + title + '\''.toString() +
      ", completed=" + completed +
      ", index=" + index +
      '}'.toString()

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    super.writeToParcel(parcel, flags)
    parcel.writeString(category)
    parcel.writeString(title)
    parcel.writeByte(if (completed) 1 else 0)
    parcel.writeInt(index)
  }

  override fun describeContents(): Int = 0

  companion object CREATOR : Parcelable.Creator<Task> {
    override fun createFromParcel(parcel: Parcel): Task = Task(parcel)

    override fun newArray(size: Int): Array<Task?> = arrayOfNulls(size)
  }


}
