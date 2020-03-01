/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.widgets.factory

import android.graphics.drawable.StateListDrawable
import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.widgets.SwitchWidget
import dev.icerock.moko.widgets.core.ViewBundle
import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.style.applyBackgroundIfNeeded
import dev.icerock.moko.widgets.style.background.Background
import dev.icerock.moko.widgets.style.background.Fill
import dev.icerock.moko.widgets.style.state.CheckableState
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.utils.androidId
import dev.icerock.moko.widgets.utils.bindNotNull

actual class CheckboxSwitchViewFactory actual constructor(
    private val background: Background<out Fill>?,
    private val image: CheckableState<ImageResource>
) : ViewFactory<SwitchWidget<out WidgetSize>> {
    override fun <WS : WidgetSize> build(
        widget: SwitchWidget<out WidgetSize>,
        size: WS,
        viewFactoryContext: ViewFactoryContext
    ): ViewBundle<WS> {
        val context = viewFactoryContext.context
        val lifecycleOwner = viewFactoryContext.lifecycleOwner

        val checkbox = AppCompatCheckBox(context).apply {
            id = widget.id.androidId

            applyBackgroundIfNeeded(this@CheckboxSwitchViewFactory.background)
        }

        val drawable = StateListDrawable().apply {
            addState(
                intArrayOf(android.R.attr.state_checked),
                ContextCompat.getDrawable(context, image.checked.drawableResId)
            )
            addState(
                intArrayOf(-android.R.attr.state_checked),
                ContextCompat.getDrawable(context, image.unchecked.drawableResId)
            )
        }

        checkbox.buttonDrawable = drawable

        widget.state.bindNotNull(lifecycleOwner) { state ->
            checkbox.isChecked = state
        }

        checkbox.setOnCheckedChangeListener { _, isChecked ->
            widget.state.value = isChecked
        }

        return ViewBundle(
            view = checkbox,
            size = size,
            margins = null
        )
    }
}
