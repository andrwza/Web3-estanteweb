package br.com.estanteweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SugestaoController {

    @Autowired
    private SugestaoRepository repository;

    @GetMapping(value = "/sugestoes/nova", produces = "text/html")
    @ResponseBody
    public String novaSugestao() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Nova Sugestão</title>
                </head>
                <body>
                    <h1>Sugira um livro</h1>

                    <form method="post" action="/sugestoes">
                        <label>Nome:</label><br>
                        <input type="text" name="nomeCliente"><br><br>

                        <label>Livro sugerido:</label><br>
                        <input type="text" name="livroSugerido"><br><br>

                        <button type="submit">Enviar sugestão</button>
                    </form>
                </body>
                </html>
                """;
    }

    @PostMapping("/sugestoes")
    public ResponseEntity<String> receberSugestao(
            @RequestParam String nomeCliente,
            @RequestParam String livroSugerido) {

        Sugestao sugestao = new Sugestao(nomeCliente, livroSugerido);

        repository.salvar(sugestao);

        return ResponseEntity
                .status(201)
                .body("Obrigado, " + nomeCliente
                        + "! Sua sugestão foi recebida.");
    }

    @GetMapping("/sugestoes")
    public String listarSugestoes(Model model) {
        model.addAttribute("sugestoes", repository.listarTodas());
        return "sugestoes";
    }
}