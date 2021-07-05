package test.sukhov.natife

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import test.sukhov.natife.ui.models.GifItemResponse

@BindingAdapter("imageUrl")
fun ImageView.bindImage(gif: GifItemResponse?) {
    gif?.let {
        Glide.with(context)
            .asGif()
            .load(gif.url)
            .thumbnail(
                Glide.with(context).asGif().load(gif.thumbnailUrl).apply(RequestOptions().override(100, 100))
            ).apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_gif_black_24dp)
                    .error(R.drawable.ic_warning_black_24dp)
            ).into(this)
    }
}