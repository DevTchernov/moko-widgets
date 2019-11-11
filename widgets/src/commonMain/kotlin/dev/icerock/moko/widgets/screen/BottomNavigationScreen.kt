/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.widgets.screen

import dev.icerock.moko.resources.DrawableResource
import dev.icerock.moko.resources.desc.StringDesc
import kotlin.reflect.KClass

expect abstract class BottomNavigationScreen() : Screen<Args.Empty> {
    abstract val items: List<BottomNavigationItem>
}

data class BottomNavigationItem(
    val id: Int,
    val title: StringDesc,
    val icon: DrawableResource? = null,
    val screen: KClass<out Screen<Args.Empty>>
)