package com.ncesam.uikit.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.ncesam.uikit.R

object LightAppIcons {
    val ArrowLeft: Painter
        @Composable get() = painterResource(R.drawable.arrowleft)
    val ArrowRight: Painter
        @Composable get() = painterResource(R.drawable.arrowright)
    val ArrowBottom: Painter
        @Composable get() = painterResource(R.drawable.arrowbottom)
    val Search: Painter
        @Composable get() = painterResource(R.drawable.search)
    val Plus: Painter
        @Composable get() = painterResource(R.drawable.plus)
    val Minus: Painter
        @Composable get() = painterResource(R.drawable.minus)
    val Message: Painter
        @Composable get() = painterResource(R.drawable.message)
    val Filter: Painter
        @Composable get() = painterResource(R.drawable.filter)
    val Download: Painter
        @Composable get() = painterResource(R.drawable.download)
    val Map: Painter
        @Composable get() = painterResource(R.drawable.map)
    val More: Painter
        @Composable get() = painterResource(R.drawable.more)
    val Cross: Painter
        @Composable get() = painterResource(R.drawable.cross)
    val CrossRounded: Painter
        @Composable get() = painterResource(R.drawable.crossrounded)
    val Trash: Painter
        @Composable get() = painterResource(R.drawable.trash)
    val ShopCart: Painter
        @Composable get() = painterResource(R.drawable.shopcart)
    val Mark: Painter
        @Composable get() = painterResource(R.drawable.mark)
    val Document: Painter
        @Composable get() = painterResource(R.drawable.document)
    val Send: Painter
        @Composable get() = painterResource(R.drawable.send)
    val Mic: Painter
        @Composable get() = painterResource(R.drawable.mic)
    val PaperClip: Painter
        @Composable get() = painterResource(R.drawable.paperclip)
    val EyeOpen: Painter
        @Composable get() = painterResource(R.drawable.eyeopen)
    val EyeClose: Painter
        @Composable get() = painterResource(R.drawable.eyeclose)
    val Point: Painter
        @Composable get() = painterResource(R.drawable.point)
    val Calendar: Painter
        @Composable get() = painterResource(R.drawable.calendar)
    val Home: Painter
        @Composable get() = painterResource(R.drawable.home)


}