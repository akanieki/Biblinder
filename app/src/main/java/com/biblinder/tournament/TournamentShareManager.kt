object TournamentShareManager {

    fun captureWinnerCard(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun shareBitmap(context: Context, bitmap: Bitmap) {
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "KupaGalibi", null)
        val uri = Uri.parse(path)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, "🔥 My 'Kupa Galibi' Winner on Biblinder!")
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}
