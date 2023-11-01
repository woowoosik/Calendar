package com.woo.calendarapp

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import javax.inject.Inject

class LoadingDialog  @Inject constructor(context: Context) : Dialog(context){
    init {
        setCanceledOnTouchOutside(false)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(R.layout.dialog_loading)
    }

}