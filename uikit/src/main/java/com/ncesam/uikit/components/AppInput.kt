package com.ncesam.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider

@Composable
fun AppInput(
    type: AppInputType = AppInputType.Text,
    visiblePassword: Boolean = false,
    focused: Boolean = false,

    helperText: String? = null,
    value: String = "",
    placeholder: String = "Пример",
    errorText: String? = null,

    onChangeText: (text: String) -> Unit,
    onClickVisibility: () -> Unit,
    onFocusChanged: (state: FocusState) -> Unit
) {
    val colors = AppTheme.colors
    val typography = AppTheme.typography
    val shape = RoundedCornerShape(10.dp)

    val isPassword = type == AppInputType.Password
    val isFilled = value.isNotBlank()
    val visualTransformation =
        if (visiblePassword) VisualTransformation.None else PasswordVisualTransformation()
    val icon = if (visiblePassword) AppTheme.icons.EyeOpen else AppTheme.icons.EyeClose


    var inputModifier = Modifier.fillMaxWidth()
    inputModifier = if (errorText != null) inputModifier
        .background(colors.inputBackground)
        .border(
            1.dp,
            colors.error,
            shape = shape
        ) else inputModifier.background(colors.inputBackground, shape)

    inputModifier = if (focused) inputModifier.border(
        1.dp,
        colors.accent,
        shape
    ) else if (isFilled) inputModifier.border(
        1.dp,
        colors.icons,
        shape
    ) else inputModifier.border(1.dp, colors.inputStroke, shape)

    Column {
        if (helperText != null) {
            BasicText(
                text = helperText,
                style = typography.captionRegular,
                color = { colors.description })
        }
        Row(
            modifier = inputModifier.padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicTextField(
                value = value,
                onValueChange = { text -> onChangeText(text) },
                textStyle = typography.textRegular,
                visualTransformation = if (isPassword) visualTransformation else VisualTransformation.None,
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged(onFocusChanged),
                decorationBox = { innerTextField ->
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (!isFilled) {
                            BasicText(
                                text = placeholder,
                                style = typography.textRegular,
                                color = { colors.caption })
                        }
                        innerTextField()
                    }
                }
            )
            if (isPassword) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onClickVisibility() },
                    tint = colors.caption
                )
            }
        }
        if (errorText != null) {
            BasicText(
                text = errorText,
                style = typography.captionRegular,
                color = { colors.error }
            )
        }
    }
}


@Composable
fun AppInputSearch(
    focused: Boolean = false,

    helperText: String? = null,
    value: String = "",
    placeholder: String = "Пример",
    errorText: String? = null,

    onChangeText: (text: String) -> Unit,
    onSearch: () -> Unit,
    onFocusChanged: (state: FocusState) -> Unit,
) {
    val colors = AppTheme.colors
    val typography = AppTheme.typography
    val shape = RoundedCornerShape(5.dp)

    val isFilled = value.isNotBlank()

    var inputModifier = Modifier.fillMaxWidth()
    inputModifier = if (errorText != null) inputModifier.background(
        colors.error,
        shape = shape
    ) else inputModifier.background(colors.inputBackground, shape)

    inputModifier = if (focused) inputModifier.border(
        1.dp,
        colors.accent,
        shape
    ) else if (isFilled) inputModifier.border(
        1.dp,
        colors.icons,
        shape
    ) else inputModifier.border(1.dp, colors.inputStroke, shape)


    Column {
        if (helperText != null) {
            BasicText(
                text = helperText,
                style = typography.captionRegular,
                color = { colors.description })
        }
        Row(
            modifier = inputModifier.padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                AppTheme.icons.Search,
                tint = colors.caption,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            BasicTextField(
                value = value,
                onValueChange = { text -> onChangeText(text) },
                textStyle = typography.textRegular,
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged(onFocusChanged),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearch()
                }),
                decorationBox = { innerTextField ->
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (!isFilled) {
                            BasicText(
                                text = placeholder,
                                style = typography.textRegular,
                                color = { colors.caption })
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}


@Preview
@Composable
fun PreviewAppInput() {
    AppThemeProvider {
        Column {
            AppInput(onChangeText = {}, onClickVisibility = {}) { }
            AppInput(type = AppInputType.Password, visiblePassword = false, value = "543534", onChangeText = {}, onClickVisibility = {}) { }
            AppInputSearch(onChangeText = {}, onSearch = {}) { }
        }
    }
}

enum class AppInputType {
    Text,
    Password
}