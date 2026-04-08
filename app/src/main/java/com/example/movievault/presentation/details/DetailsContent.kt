package com.example.movievault.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movievault.R
import com.example.movievault.domain.model.MovieDetails
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailsContent(
    movie: MovieDetails,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Box {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/original${movie.backdropPath}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp)
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.9f)
                                )
                            )
                        )
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .offset(y = (-80).dp)
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Row {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                        contentDescription = null,
                        modifier = Modifier
                            .width(130.dp)
                            .height(195.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Spacer(Modifier.width(16.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.headlineSmall
                            )

                            FilledIconButton(
                                onClick = onFavoriteClick,
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor =
                                        if (isFavorite)
                                            MaterialTheme.colorScheme.error
                                        else
                                            MaterialTheme.colorScheme.tertiaryContainer
                                )
                            ) {
                                Icon(
                                    imageVector =
                                        if (isFavorite)
                                            Icons.Default.Favorite
                                        else
                                            Icons.Default.FavoriteBorder,
                                    contentDescription = null
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            Text(
                                text = stringResource(R.string.rating_format).format(movie.rating),
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = movie.releaseDate.formatDate(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(Modifier.height(6.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            movie.genres.forEach {
                                SuggestionChip(
                                    onClick = {},
                                    label = { Text(it) },
                                    colors = SuggestionChipDefaults.suggestionChipColors(
                                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                        labelColor = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(22.dp))

                Text(
                    text = stringResource(R.string.overview),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(8.dp))

                movie.overview?.let {overview ->
                    Text(
                        text = overview,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 22.sp
                    )
                }

                Spacer(Modifier.height(60.dp))
            }
        }
    }
}

fun String?.formatDate(): String {
    if (this.isNullOrEmpty()) return ""

    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        val date = parser.parse(this) ?: return this
        formatter.format(date)
    } catch (e: Exception) {
        this
    }
}