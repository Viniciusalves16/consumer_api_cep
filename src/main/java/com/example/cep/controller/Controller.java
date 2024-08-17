package com.example.cep.controller;

import com.example.cep.dto.Endereco;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class Controller {


    @GetMapping("cep/{numero}")
    public ResponseEntity buscaCep(@Valid @PathVariable(value = "numero") String numero) throws URISyntaxException, IOException, InterruptedException {
        Endereco endereco = null;
        HttpResponse<String> response = null;
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://viacep.com.br/ws/" + numero + "/json"))
                    .build();
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                endereco = new Gson().fromJson(response.body(), Endereco.class);
            } else {
                throw new RuntimeException("erro na busca do cep, código de erro : " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar a requisição: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        return ResponseEntity.ok(endereco);
    }
}



