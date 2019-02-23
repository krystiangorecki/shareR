package kry.sharer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

class PageVisiter {

    private final Context ctx;

    public PageVisiter(Context ctx) {
        this.ctx = ctx;
    }

    public void visitSearchPageG(String number) {
        Toast.makeText(ctx, number, Toast.LENGTH_LONG).show();
        visitUrl("https://www.google.pl/search?q=" + number);

    }

    public void visitSearchPageR(String number) {
        Toast.makeText(ctx, number, Toast.LENGTH_LONG).show();
        visitUrl("https://www.google.pl/search?q=" + number);
    }

    private void visitUrl(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(ctx.getPackageManager()) != null) {
            ctx.startActivity(intent);
        }
    }
}
