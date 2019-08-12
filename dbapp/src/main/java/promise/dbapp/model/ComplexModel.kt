package promise.dbapp.model

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import promise.dbapp.R
import promise.model.List
import promise.model.SModel
import promise.promiseui.model.Viewable

class ComplexModel constructor() : SModel(), Parcelable, Viewable {
  var index: Int = 0

  override fun layout(): Int = R.layout.complex_layout

  override fun init(view: View?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun bind(view: View?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun index(index: Int) {
   this.index = index
  }

  override fun index(): Int {
    return this.index
  }

  var intVariable: Int? = null

  var floatVariable: Float? = null

  var doubleVariable: Double? = null

  var stringVariable: String? = null

  constructor(source: Parcel) : this() {
    intVariable = source.readInt()
  }

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}
  override fun toString(): String {
    return "ComplexModel(intVariable=$intVariable, floatVariable=$floatVariable, doubleVariable=$doubleVariable, stringVariable=$stringVariable)\n"
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<ComplexModel> = object : Parcelable.Creator<ComplexModel> {
      override fun createFromParcel(source: Parcel): ComplexModel = ComplexModel(source)
      override fun newArray(size: Int): Array<ComplexModel?> = arrayOfNulls(size)
    }

    fun someModels(): List<ComplexModel> = List.fromArray(ComplexModel().apply {
      intVariable = 1
      floatVariable = 0.2f
      doubleVariable = 3.567
      stringVariable = "some string"
    }, ComplexModel().apply {
      intVariable = 2
      floatVariable = 0.5f
      doubleVariable = 3.87
      stringVariable = "some string 2"
    }, ComplexModel().apply {
      intVariable = 5
      floatVariable = 0.7f
      doubleVariable = 10.987655
      stringVariable = "some string 3"
    }, ComplexModel().apply {
      intVariable = 10
      floatVariable = 62f
      doubleVariable = 567.7865
      stringVariable = "some string 4"
    }, ComplexModel().apply {
      intVariable = 18
      floatVariable = 100.3f
      doubleVariable = 456.987
      stringVariable = "some string 5"
    },ComplexModel().apply {
      intVariable = 5
      floatVariable = 0.7f
      doubleVariable = 10.987655
      stringVariable = "some string 3"
    }, ComplexModel().apply {
      intVariable = 10
      floatVariable = 62f
      doubleVariable = 567.7865
      stringVariable = "some string 4"
    }, ComplexModel().apply {
      intVariable = 18
      floatVariable = 100.3f
      doubleVariable = 456.987
      stringVariable = "some string 5"
    },ComplexModel().apply {
      intVariable = 5
      floatVariable = 0.7f
      doubleVariable = 10.987655
      stringVariable = "some string 3"
    }, ComplexModel().apply {
      intVariable = 10
      floatVariable = 62f
      doubleVariable = 567.7865
      stringVariable = "some string 4"
    }, ComplexModel().apply {
      intVariable = 18
      floatVariable = 100.3f
      doubleVariable = 456.987
      stringVariable = "some string 5"
    },ComplexModel().apply {
      intVariable = 5
      floatVariable = 0.7f
      doubleVariable = 10.987655
      stringVariable = "some string 3"
    }, ComplexModel().apply {
      intVariable = 10
      floatVariable = 62f
      doubleVariable = 567.7865
      stringVariable = "some string 4"
    }, ComplexModel().apply {
      intVariable = 18
      floatVariable = 100.3f
      doubleVariable = 456.987
      stringVariable = "some string 5"
    },ComplexModel().apply {
      intVariable = 5
      floatVariable = 0.7f
      doubleVariable = 10.987655
      stringVariable = "some string 3"
    }, ComplexModel().apply {
      intVariable = 10
      floatVariable = 62f
      doubleVariable = 567.7865
      stringVariable = "some string 4"
    }, ComplexModel().apply {
      intVariable = 18
      floatVariable = 100.3f
      doubleVariable = 456.987
      stringVariable = "some string 5"
    },ComplexModel().apply {
      intVariable = 5
      floatVariable = 0.7f
      doubleVariable = 10.987655
      stringVariable = "some string 3"
    }, ComplexModel().apply {
      intVariable = 10
      floatVariable = 62f
      doubleVariable = 567.7865
      stringVariable = "some string 4"
    }, ComplexModel().apply {
      intVariable = 18
      floatVariable = 100.3f
      doubleVariable = 456.987
      stringVariable = "some string 5"
    })
  }
}