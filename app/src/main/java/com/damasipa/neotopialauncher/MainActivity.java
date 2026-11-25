package com.damasipa.neotopialauncher;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.*;
import android.view.Gravity;
import android.graphics.Color;

public class MainActivity extends Activity {

    LinearLayout layout;
    LinearLayout panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(25, 25, 25, 25);
        layout.setBackgroundColor(Color.rgb(25,25,25));

        TextView title = new TextView(this);
        title.setText("NEOTOPIA LAUNCHER");
        title.setTextColor(Color.WHITE);
        title.setTextSize(24);
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        Button open = new Button(this);
        open.setText("BUKA NEOTOPIA DI CHROME");
        layout.addView(open);

        Button menu = new Button(this);
        menu.setText("NEO MENU");
        layout.addView(menu);

        panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(15,15,15,15);
        panel.setBackgroundColor(Color.rgb(45,45,45));
        panel.setVisibility(LinearLayout.GONE);
        layout.addView(panel);

        EditText name = new EditText(this);
        name.setHint("Nama script");
        name.setTextColor(Color.WHITE);
        name.setHintTextColor(Color.LTGRAY);
        panel.addView(name);

        EditText code = new EditText(this);
        code.setHint("Tempel script di sini");
        code.setTextColor(Color.WHITE);
        code.setHintTextColor(Color.LTGRAY);
        code.setMinLines(4);
        panel.addView(code);

        Button save = new Button(this);
        save.setText("SIMPAN SCRIPT");
        panel.addView(save);

        TextView list = new TextView(this);
        list.setTextColor(Color.WHITE);
        panel.addView(list);

        save.setOnClickListener(v -> {
            String n = name.getText().toString().trim();
            String c = code.getText().toString();

            if (n.isEmpty() || c.isEmpty()) {
                Toast.makeText(this, "Isi nama dan script", Toast.LENGTH_SHORT).show();
                return;
            }

            String old = getPreferences(0).getString("scripts", "");
            String updated = old + "\n" + n;
            getPreferences(0).edit().putString("scripts", updated).apply();

            name.setText("");
            code.setText("");

            list.setText("Script tersimpan:\n" + updated);

            Toast.makeText(this, "Script tersimpan", Toast.LENGTH_SHORT).show();
        });

        menu.setOnClickListener(v -> {
            if (panel.getVisibility() == LinearLayout.GONE) {
                panel.setVisibility(LinearLayout.VISIBLE);
                list.setText("Script tersimpan:\n" +
                    getPreferences(0).getString("scripts", ""));
            } else {
                panel.setVisibility(LinearLayout.GONE);
            }
        });

        open.setOnClickListener(v -> {
            Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://playneotopia.com/")
            );
            startActivity(intent);
        });

        setContentView(layout);
    }
        }
