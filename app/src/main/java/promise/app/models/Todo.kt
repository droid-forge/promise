package promise.app.models

import android.os.Parcel
import android.os.Parcelable
import android.view.View

import promise.model.SModel
import promise.model.Searchable

class Todo : SModel, Searchable {
  private var category: String? = null
  private var name: String? = null
  private var completed: Boolean = false

  private var index: Int = 0

  fun category(): String? = category

  fun category(category: String): Todo {
    this.category = category
    return this
  }

  fun name(): String? = name

  fun name(name: String): Todo {
    this.name = name
    return this
  }

  fun completed(): Boolean = completed

  fun completed(completed: Boolean): Todo {
    this.completed = completed
    return this
  }

  override fun onSearch(query: String): Boolean = false

  override fun layout(): Int = 0

  override fun init(view: View) {

  }

  override fun bind(view: View) {

  }

  override fun index(index: Int) {
    this.index = index
  }

  override fun index(): Int = index


  override fun describeContents(): Int = 0

  override fun writeToParcel(dest: Parcel, flags: Int) {
    super.writeToParcel(dest, flags)
    dest.writeString(this.category)
    dest.writeString(this.name)
    dest.writeByte(if (this.completed) 1.toByte() else 0.toByte())
    dest.writeInt(this.index)
  }

  override fun toString(): String {
    return "Todo{" +
        "category='" + category + '\''.toString() +
        ", namee='" + name + '\''.toString() +
        ", completed=" + completed +
        ", index=" + index +
        '}'.toString()
  }

  /*@Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Todo todo = (Todo) o;
    return completed == todo.completed &&
        index == todo.index &&
        Objects.equals(category, todo.category) &&
        Objects.equals(namee, todo.namee);
  }

  @Override
  public int hashCode() {

    return Objects.hash(category, namee, completed, index);
  }*/

  constructor()

  private constructor(`in`: Parcel) : super(`in`) {
    this.category = `in`.readString()
    this.name = `in`.readString()
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
