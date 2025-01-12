/*
 * Copyright (C) 2025 The FlorisBoard Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.patrickgold.florisboard.app.ext

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Input
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.patrickgold.florisboard.BuildConfig
import dev.patrickgold.florisboard.R
import dev.patrickgold.florisboard.app.LocalNavController
import dev.patrickgold.florisboard.app.Routes
import dev.patrickgold.florisboard.extensionManager
import dev.patrickgold.florisboard.lib.compose.FlorisOutlinedBox
import dev.patrickgold.florisboard.lib.compose.FlorisScreen
import dev.patrickgold.florisboard.lib.compose.FlorisTextButton
import dev.patrickgold.florisboard.lib.compose.defaultFlorisOutlinedBox
import dev.patrickgold.florisboard.lib.compose.stringRes
import dev.patrickgold.florisboard.lib.util.launchUrl
import dev.patrickgold.jetpref.datastore.ui.Preference

@Composable
fun ExtensionHomeScreen() = FlorisScreen {
    title = stringRes(R.string.ext__home__title)
    previewFieldVisible = false

    val context = LocalContext.current
    val navController = LocalNavController.current
    val extensionManager by context.extensionManager()
    val extensionIndex = extensionManager.combinedExtensionList()

    content {
        FlorisOutlinedBox(
            modifier = Modifier.defaultFlorisOutlinedBox(),
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 4.dp),
                text = stringRes(id = R.string.ext__home__info),
                style = MaterialTheme.typography.bodySmall,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
            ) {
                FlorisTextButton(
                    onClick = {
                        context.launchUrl("https://${BuildConfig.FLADDONS_STORE_URL}/")
                    },
                    icon = Icons.Default.Shop,
                    text = stringRes(id = R.string.ext__home__visit_store),
                )
                Spacer(modifier = Modifier.weight(1f))
                FlorisTextButton(
                    onClick = {
                        navController.navigate(Routes.Ext.Import(ExtensionImportScreenType.EXT_ANY, null))
                    },
                    icon = Icons.AutoMirrored.Filled.Input,
                    text = stringRes(R.string.action__import),
                )
            }
        }

        UpdateBox(extensionIndex = extensionIndex)

        Preference(
            icon = Icons.Default.Palette,
            title = stringRes(R.string.ext__list__ext_theme),
            onClick = {
                navController.navigate(Routes.Ext.List(ExtensionListScreenType.EXT_THEME, false))
            },
        )
        Preference(
            icon = Icons.Default.Keyboard,
            title = stringRes(R.string.ext__list__ext_keyboard),
            onClick = {
                navController.navigate(Routes.Ext.List(ExtensionListScreenType.EXT_KEYBOARD, false))
            },
        )
        Preference(
            icon = Icons.Default.Language,
            title = stringRes(R.string.ext__list__ext_languagepack),
            onClick = {
                navController.navigate(Routes.Ext.List(ExtensionListScreenType.EXT_LANGUAGEPACK, false))
            },
        )
    }
}
