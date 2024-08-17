package com.example.cep.component;

import com.example.cep.dto.Endereco;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class GeradorDeArquivo {


    public void salvaArquivo(Endereco endereco) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter(endereco.cep() +"json");
        fileWriter.write(gson.toJson(endereco));
        fileWriter.close();
    }
}
