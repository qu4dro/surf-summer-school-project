package orlov.surf.summer.school.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import orlov.surf.summer.school.R
import orlov.surf.summer.school.databinding.ViewLoaderButtonBinding

class LoaderButtonView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val binding = ViewLoaderButtonBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.withStyledAttributes(attrs, R.styleable.LoaderButtonView) {
            text = getString(R.styleable.LoaderButtonView_btnText) ?: ""
            isLoading = getBoolean(R.styleable.LoaderButtonView_isLoading, false)
        }
    }

    var text: String = ""
        set(value) {
            field = value
            binding.tvText.text = value
        }

    var isLoading: Boolean = false
        set(value) {
            field = value
            binding.pbLoading.isVisible = value
            binding.tvText.isVisible = !value
            binding.root.isEnabled = !value
        }
}