package com.developersbreach.composeactors.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import com.developersbreach.designsystem.components.CaButtonOutlined
import com.developersbreach.designsystem.components.CaHorizontalSpacer
import com.developersbreach.designsystem.components.CaIcon
import com.developersbreach.designsystem.components.CaTextBody1
import com.developersbreach.designsystem.components.CaTextSubtitle1

/**
 * A generic dropdown component placed here temporarily to avoid runtime crashes
 * caused by missing Compose Material stubs (e.g. androidx.compose.material.Menu_commonStubsKt)
 * when this module isn’t configured as an Android/Compose library.
 *
 * The design-system module is currently a plain JVM library without
 * `com.android.library` or `buildFeatures { compose = true }`, so Compose’s material stubs aren’t generated there,
 * leading to `ClassNotFoundException: androidx.compose.material.Menu_commonStubsKt`.
 * Until the design-system module is converted to an Android/Compose library
 * (or we migrate fully to Material3 in a multiplatform setup), this code must live in an Android-enabled module where
 * the Compose compiler can emit and package the required material stubs.
 */
@Composable
fun <T> CaDropdown(
    modifier: Modifier,
    items: List<T>,
    selectedItem: T,
    isDropdownExpanded: Boolean,
    onExpanded: () -> Unit,
    onDismissRequest: () -> Unit = { },
    onItemClick: (T) -> Unit,
    itemText: (T) -> String,
    defaultSelectionTitle: String,
) {
    CaButtonOutlined(
        modifier = Modifier,
        onClick = onExpanded,
    ) {
        CaTextSubtitle1(
            text = selectedItem?.let(itemText) ?: defaultSelectionTitle,
            modifier = Modifier.padding(vertical = 2.dp),
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.primary,
        )
        CaHorizontalSpacer(8)
        CaIcon(
            modifier = Modifier,
            imageVector = when {
                isDropdownExpanded -> Icons.Filled.ArrowDropUp
                else -> Icons.Filled.ArrowDropDown
            },
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
        )
    }

    DropdownMenu(
        expanded = isDropdownExpanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                onClick = { onItemClick(item) },
            ) {
                CaTextBody1(
                    text = itemText(item),
                    modifier = Modifier,
                )
            }
        }
    }
}