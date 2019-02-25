package kry.sharer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;

class PageVisiter {

    private final Context ctx;

    public PageVisiter(Context ctx) {
        this.ctx = ctx;
    }

    public void visitSearchPageR(String number) {
        // Toast.makeText(ctx, number, Toast.LENGTH_LONG).show();
        byte[] decodedBytes = Base64.decode("b2tzYQ==", Base64.DEFAULT);
        String r = new String(decodedBytes, StandardCharsets.UTF_8);
        visitUrl("https://www.r" + r + ".pl/pl/szukaj/?anons_type=0&anons_state=0&anons_city_part=&cenaod=0&cenado=0&cenapoldo=0&cena15do=0&cenanocdo=0&wiekod=0&wiekdo=0&wagaod=0&wagado=0&wzrostod=0&wzrostdo=0&biustod=0&biustdo=0&jezyk=&dzien=0&hod=&hdo=&wyjazdy=0&name=&nr_tel=" + number + "&key_word=#show");
    }

    public void visitSearchPageG(String number) {
        // Toast.makeText(ctx, number, Toast.LENGTH_LONG).show();
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
