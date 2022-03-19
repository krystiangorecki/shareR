package kry.sharer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;

class PageVisitor {

    private final Context ctx;

    public PageVisitor(Context ctx) {
        this.ctx = ctx;
    }

    public void visitSearchPageE(String number) {
        Toast.makeText(ctx, number, Toast.LENGTH_LONG).show();
        byte[] decodedBytes = Base64.decode("c2NvcnQ=", Base64.DEFAULT);
        String e = new String(decodedBytes, StandardCharsets.UTF_8);
        visitUrl("https://www.e" + e + ".pl/szukaj/?q=" + number);
    }

    public void visitSearchPageG(String number) {
        Toast.makeText(ctx, number, Toast.LENGTH_LONG).show();
        byte[] decodedBytes = Base64.decode("YXJzb25pZXJh", Base64.DEFAULT);
        String g = new String(decodedBytes, StandardCharsets.UTF_8);
        visitUrl("http://www.g" + g + ".com.pl/forum/index.php?app=core&module=search&do=search&fromMainBar=1&search_term=%22" + number + "%22&search_app=forums");
    }

    private void visitUrl(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(ctx.getPackageManager()) != null) {
            ctx.startActivity(intent);
        }
    }
}
