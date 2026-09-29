package com.example.request

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.request.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.btnBuscar.setOnClickListener {
            val cepDigitado = binding.edtxtCep.text.toString()

            if(cepDigitado.isNotEmpty()){
                buscarDados(cepDigitado)

            }else {
                binding.exibir.text = "digite um cep valido"
            }

        }


    }

    private fun buscarDados(cep: String){
        lifecycleScope.launch {

            try {

                val resultadoCep = ApiClient.service.buscarCep(cep)

                exibirTexto("Rua: ${resultadoCep.logradouro}\n Bairro: ${resultadoCep.bairro}\n: ${resultadoCep.unidade}")

            }catch (e: Exception){
                Toast.makeText(this@MainActivity,"erro ao buscar cep", Toast.LENGTH_SHORT).show()
                exibirTexto("Erro na Requisição")


            }

        }
    }

    private fun exibirTexto(texto: String){
        binding.exibir.text = texto
    }



}