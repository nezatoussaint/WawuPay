package com.hviewtech.wawupay.common.app

import com.bumptech.glide.load.resource.bitmap.CircleCrop
import jp.wasabeef.glide.transformations.*

/**
 * Transformation<Bitmap>
 */
object TransformationManagers {

  fun blur() = BlurTransformation()

  fun blur(radius: Int) = BlurTransformation(radius)

  fun blur(radius: Int, sampling: Int) = BlurTransformation(radius, sampling)

  fun color(color: Int) = ColorFilterTransformation(color)

  fun cropCircle() = CircleCrop()

  fun cropSquare() = CropSquareTransformation()

  fun crop(width: Int, height: Int) = CropTransformation(width, height)

  fun crop(width: Int, height: Int, cropType: CropTransformation.CropType) = CropTransformation(width, height, cropType)

  fun grayScale() = GrayscaleTransformation()

  fun mask(maskId: Int) = MaskTransformation(maskId)

  fun round(radius: Int, margin: Int) = RoundedCornersTransformation(radius, margin)

  fun round(radius: Int, margin: Int, cornerType: RoundedCornersTransformation.CornerType) =
    RoundedCornersTransformation(radius, margin, cornerType)
}
