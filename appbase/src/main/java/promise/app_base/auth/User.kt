package promise.app_base.auth

import android.os.Parcel
import android.os.Parcelable
import promise.model.List
import promise.model.SModel

class User() : SModel() {

  private var email: String? = null
  private var password: String? = null
  private var names: String? = null
  private var roles: List<Role>? = null

  constructor(parcel: Parcel) : this() {
    email = parcel.readString()
    password = parcel.readString()
    names = parcel.readString()
  }

  fun email(): String? = email

  fun email(email: String): User {
    this.email = email
    return this
  }

  fun password(): String? = password

  fun password(password: String): User {
    this.password = password
    return this
  }

  fun names(): String? = names

  fun names(names: String): User {
    this.names = names
    return this
  }

  fun roles(): List<Role>? = roles

  fun roles(roles: List<Role>): User {
    this.roles = roles
    return this
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    super.writeToParcel(parcel, flags)
    parcel.writeString(email)
    parcel.writeString(password)
    parcel.writeString(names)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<User> {
    override fun createFromParcel(parcel: Parcel): User {
      return User(parcel)
    }

    override fun newArray(size: Int): Array<User?> {
      return arrayOfNulls(size)
    }
  }
}
