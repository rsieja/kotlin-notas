package br.com.rodie.notas

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import android.widget.EditText
import java.io.OutputStreamWriter


class MainActivity : AppCompatActivity() {

    var EditText1: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            save("Note1.txt")
        }

        EditText1 = findViewById(R.id.EditText1)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.action_settings -> true
                else -> super.onOptionsItemSelected(item)
            }

    private fun save(fileName: String) {
        try {
            val out = OutputStreamWriter(openFileOutput(fileName, 0))
            out.write(EditText1!!.text.toString())
            out.close()

            Toast.makeText(this, "Nota salva!", Toast.LENGTH_SHORT).show()
        } catch (t: Throwable) {
            Toast.makeText(this, "Erro: " + t.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun fileExists(fname: String): Boolean {
        val file = baseContext.getFileStreamPath(fname)
        return file.exists()
    }

    private fun open(fileName: String): String {
        var content = ""

        if (fileExists(fileName)) {
            try {
                val `in` = openFileInput(fileName)

                if (`in` != null) {
                    val tmp = InputStreamReader(`in`)
                    val reader = BufferedReader(tmp)
                    var str: String
                    val buf = StringBuilder()

                    while (reader.readLine() != null) {
                        str = reader.readLine()
                        buf.append(str + "\n")
                    }

                    `in`.close()
                    content = buf.toString()
                }
            } catch (e: java.io.FileNotFoundException) {
                Toast.makeText(this, "Arquivo não encontrado!", Toast.LENGTH_LONG).show()
            } catch (t: Throwable) {
                Toast.makeText(this, "Erro: " + t.toString(), Toast.LENGTH_LONG).show()
            }
        }

        return content
    }

}
