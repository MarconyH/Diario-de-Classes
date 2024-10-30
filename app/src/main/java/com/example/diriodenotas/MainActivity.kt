package com.example.diriodenotas

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nome = findViewById<EditText>(R.id.editTextText)
        val nota1 = findViewById<EditText>(R.id.editTextNumberDecimal)
        val nota2 = findViewById<EditText>(R.id.editTextNumberDecimal2)
        val nota3 = findViewById<EditText>(R.id.editTextNumberDecimal3)
        val atualizar = findViewById<Button>(R.id.button)
        val excluir = findViewById<Button>(R.id.button2)
        val pesquisar = findViewById<Button>(R.id.button3)

        val sharedPreferences = getSharedPreferences("diario_classe", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        atualizar.setOnClickListener {
            val nomeAluno = nome.text.toString().lowercase().replace(" ", "")
            val notas = "${nota1.text},${nota2.text},${nota3.text}"
            if(nomeAluno.isEmpty() || nota1.text.isEmpty() || nota2.text.isEmpty() || nota3.text.isEmpty()){
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if(nota1.text.toString().toFloat() > 10 || nota2.text.toString().toFloat() > 10 || nota3.text.toString().toFloat() > 10 || nota1.text.toString().toFloat() < 0 || nota2.text.toString().toFloat() < 0 || nota3.text.toString().toFloat() < 0){
                Toast.makeText(this, "As notas devem estar entre 0 e 10!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            editor.putString(nomeAluno, notas)
            editor.apply()

            nome.text.clear()
            nota1.text.clear()
            nota2.text.clear()
            nota3.text.clear()

            Toast.makeText(this, "Aluno atualizado com sucesso!", Toast.LENGTH_SHORT).show()
        }

        excluir.setOnClickListener {
            val nomeAluno = nome.text.toString().lowercase().replace(" ", "")
            editor.remove(nomeAluno)
            editor.apply()

            nome.text.clear()
            nota1.text.clear()
            nota2.text.clear()
            nota3.text.clear()

            Toast.makeText(this, "Aluno excluído com sucesso!", Toast.LENGTH_SHORT).show()
        }

        pesquisar.setOnClickListener {
            val nomeAluno = nome.text.toString().lowercase().replace(" ", "")
            val notas = sharedPreferences.getString(nomeAluno, null)

            if (notas != null) {
                val notasArray = notas.split(",").map { it.toString() }
                nota1.setText(notasArray[0])
                nota2.setText(notasArray[1])
                nota3.setText(notasArray[2])
            } else {
                Toast.makeText(this, "Aluno não encontrado!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}