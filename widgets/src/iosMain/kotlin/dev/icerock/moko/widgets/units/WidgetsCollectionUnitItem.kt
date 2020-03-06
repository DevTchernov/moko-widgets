/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.widgets.units

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.units.CollectionUnitItem
import platform.UIKit.UICollectionView
import platform.UIKit.UICollectionViewCell
import platform.UIKit.translatesAutoresizingMaskIntoConstraints
import dev.icerock.moko.widgets.core.Widget
import dev.icerock.moko.widgets.style.view.WidgetSize

actual abstract class WidgetsCollectionUnitItem<T> actual constructor(
    override val itemId: Long,
    private val data: T
) : CollectionUnitItem {
    actual abstract val reuseId: String
    actual abstract fun createWidget(data: LiveData<T>): Widget<out WidgetSize>

    override val reusableIdentifier: String get() = reuseId

    override fun register(intoView: UICollectionView) {
        intoView.registerClass(
            cellClass = UICollectionViewCell().`class`(),
            forCellWithReuseIdentifier = reusableIdentifier
        )
    }

    override fun bind(collectionViewCell: UICollectionViewCell) {
        collectionViewCell.contentView.setupWidgetContent(data, ::createWidget)
    }
}
