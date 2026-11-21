package cvc.dashingdog.tydgenoot.about

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.m3.Markdown

private data class AppIdentity(
    val appName: String,
    val versionName: String
)

private data class PackagedDocs(
    val aboutMarkdown: String,
    val creditsMarkdown: String,
    val licenseText: String
)

private fun loadAppIdentity(context: Context): AppIdentity {
    return try {
        val pm = context.packageManager
        val appInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pm.getApplicationInfo(
                context.packageName,
                PackageManager.ApplicationInfoFlags.of(0)
            )
        } else {
            @Suppress("DEPRECATION")
            pm.getApplicationInfo(context.packageName, 0)
        }
        val pkgInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pm.getPackageInfo(
                context.packageName,
                PackageManager.PackageInfoFlags.of(0)
            )
        } else {
            @Suppress("DEPRECATION")
            pm.getPackageInfo(context.packageName, 0)
        }
        val resolvedName = pm.getApplicationLabel(appInfo).toString().ifBlank { "Tydgenoot" }
        val resolvedVersion = pkgInfo.versionName ?: "unknown"
        AppIdentity(appName = resolvedName, versionName = resolvedVersion)
    } catch (_: Exception) {
        AppIdentity(appName = "Tydgenoot", versionName = "unknown")
    }
}

private fun readAssetText(context: Context, fileName: String): String {
    return try {
        context.assets.open(fileName).bufferedReader().use { it.readText() }.trim()
    } catch (_: Exception) {
        "_Unable to load $fileName from packaged assets._"
    }
}

private fun loadPackagedDocs(context: Context): PackagedDocs {
    return PackagedDocs(
        aboutMarkdown = readAssetText(context, "ABOUT.md"),
        creditsMarkdown = readAssetText(context, "CREDITS.md"),
        licenseText = readAssetText(context, "LICENSE")
    )
}

@Composable
fun AboutScreen(
    accentColor: Color,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(enabled = true) {
        onDismiss()
    }

    val context = LocalContext.current
    val identity = remember(context) { loadAppIdentity(context) }
    val docs = remember(context) { loadPackagedDocs(context) }

    val combinedMarkdown = remember(docs) {
        buildString {
            append(docs.aboutMarkdown)
            append("\n\n---\n\n")
            append(docs.creditsMarkdown)
            append("\n\n---\n\n")
            append("## License\n\n")
            append("```text\n")
            append(docs.licenseText)
            append("\n```")
        }
    }

    val panelSurface = Color(0xFF14181F)
    val textPrimary = Color(0xFFF3F6FA)
    val textSecondary = Color(0xFFA8B3C2)

    val aboutColorScheme = remember(accentColor) {
        darkColorScheme(
            primary = accentColor,
            onPrimary = Color(0xFF0B0E14),
            background = panelSurface,
            surface = panelSurface,
            surfaceVariant = Color(0xFF1C212B),
            onBackground = textPrimary,
            onSurface = textPrimary,
            onSurfaceVariant = textSecondary,
            outline = Color(0xFF2B3240)
        )
    }

    MaterialTheme(colorScheme = aboutColorScheme) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xE6000000))
                .clickable(enabled = false, onClick = {}),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .fillMaxHeight(0.90f),
                shape = RoundedCornerShape(16.dp),
                color = panelSurface,
                contentColor = textPrimary,
                border = BorderStroke(1.dp, Color(0xFF2B3240))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Header with dynamic App Name & versionName + Back button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = identity.appName,
                                style = MaterialTheme.typography.titleLarge,
                                color = textPrimary
                            )
                            Text(
                                text = "Version ${identity.versionName}",
                                style = MaterialTheme.typography.bodySmall,
                                color = accentColor
                            )
                        }

                        IconButton(onClick = onDismiss) {
                            Text(
                                text = "✕",
                                style = MaterialTheme.typography.titleMedium,
                                color = textPrimary
                            )
                        }
                    }

                    HorizontalDivider(color = Color(0xFF282F3C))

                    // Scrollable Markdown body rendering ABOUT.md, CREDITS.md, and fenced LICENSE
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(20.dp)
                    ) {
                        Markdown(
                            content = combinedMarkdown,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}