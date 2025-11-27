package org.utl.rvpark_movil.utils.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.utl.rvpark_movil.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroCarousel(
    navHostController: NavHostController
) {
    data class CarouselItem(
        val id: Int,
        @DrawableRes val imageResId: Int,
        val contentDescription: String
    )

    val items = remember {
        listOf(
            CarouselItem(0, R.drawable.camion, "camion"),
            CarouselItem(1, R.drawable.family, "familia"),
            CarouselItem(2, R.drawable.festival, "festival"),
            CarouselItem(3, R.drawable.tienda, "tienda"),
            CarouselItem(4, R.drawable.trailers, "trailers"),
        )
    }

    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { items.count() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp, bottom = 16.dp),
    preferredItemWidth = 186.dp,
    itemSpacing = 8.dp,
    contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
            i ->
        val item = items[i]
        Image(
            modifier = Modifier
                .height(205.dp).clickable(true, onClick = {navHostController.navigate("home")})
                .maskClip(MaterialTheme.shapes.large),
            painter = painterResource(id = item.imageResId),
            contentDescription = item.contentDescription,
            contentScale = ContentScale.Crop,

        )
    }
}