package com.damasipa.neotopialauncher;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.*;
import android.view.Gravity;
import android.graphics.Color;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity {

    LinearLayout layout;
    LinearLayout panel;
    EditText name;
    EditText code;
    LinearLayout list;

    JSONArray scripts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scripts = new JSONArray();

        String saved = getPreferences(0)
            .getString("scripts", "[]");

        try {
            scripts = new JSONArray(saved);
        } catch (Exception e) {
            scripts = new JSONArray();
        }

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
        panel.setPadding(12, 12, 12, 12);
        panel.setBackgroundColor(Color.rgb(45,45,45));
        panel.setVisibility(View.GONE);
        layout.addView(panel);

        name = new EditText(this);
        name.setHint("Nama script");
        name.setTextColor(Color.WHITE);
        name.setHintTextColor(Color.LTGRAY);
        panel.addView(name);

        code = new EditText(this);
        code.setHint("Tempel isi script");
        code.setTextColor(Color.WHITE);
        code.setHintTextColor(Color.LTGRAY);
        code.setMinLines(4);
        panel.addView(code);

        Button save = new Button(this);
        save.setText("SIMPAN SCRIPT");
        panel.addView(save);

        list = new LinearLayout(this);
        list.setOrientation(LinearLayout.VERTICAL);
        panel.addView(list);

        open.setOnClickListener(v -> {
            Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://playneotopia.com/")
            );
            startActivity(intent);
        });

        menu.setOnClickListener(v -> {
            if (panel.getVisibility() == View.GONE) {
                panel.setVisibility(View.VISIBLE);
                showScripts();
            } else {
                panel.setVisibility(View.GONE);
            }
        });

        save.setOnClickListener(v -> {
            String n = name.getText().toString().trim();
            String c = code.getText().toString();

            if (n.isEmpty() || c.isEmpty()) {
                Toast.makeText(this,
                    "Isi nama dan script dahulu",
                    Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject item = new JSONObject();
                item.put("name", n);
                item.put("code", c);
                scripts.put(item);

                saveScripts();

                name.setText("");
                code.setText("");

                showScripts();

                Toast.makeText(this,
                    "Script berhasil disimpan",
                    Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this,
                    "Gagal menyimpan script",
                    Toast.LENGTH_SHORT).show();
            }
        });

        setContentView(layout);
    }

    private void saveScripts() {
        getPreferences(0).edit()
            .putString("scripts", scripts.toString())
            .apply();
    }

    private void showScripts() {
        list.removeAllViews();

        for (int i = 0; i < scripts.length(); i++) {
            final int index = i;

            try {
                JSONObject item = scripts.getJSONObject(i);

                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(this);
                title.setText(item.getString("name"));
                title.setTextColor(Color.WHITE);
                title.setTextSize(16);
                row.addView(title);

                Button delete = new Button(this);
                delete.setText("HAPUS");

                delete.setOnClickListener(v -> {
                    JSONArray updated = new JSONArray();

                    for (int j = 0; j < scripts.length(); j++) {
                        if (j != index) {
                            updated.put(scripts.opt(j));
                        }
                    }

                    scripts = updated;
                    saveScripts();
                    showScripts();
                });

                row.addView(delete);
                list.addView(row);

            } catch (Exception e) {
            }
        }
    }
}
