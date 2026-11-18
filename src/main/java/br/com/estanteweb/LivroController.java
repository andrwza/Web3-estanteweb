package br.com.estanteweb;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Controller;

@Controller
public class LivroController {

    @Autowired
    private LivroRepository repositorio;

    @GetMapping("/livros")
    public String listarLivros(Model model) {

        model.addAttribute("livros", repositorio.listarTodos());

        return "livros";
    }

    @GetMapping("/livros/total")
    @ResponseBody
    public String totalLivros() {
        return "O acervo tem " + repositorio.contarLivros() + " livros.";
    }

    @GetMapping("/livros/busca")
    public ResponseEntity<String> buscarPorPreco(@RequestParam(defaultValue = "999") double precoMaximo) {

    List<Livro> livros = repositorio.listarAtePreco(precoMaximo);

    if (livros.isEmpty()) {
        return ResponseEntity
                .status(404)
                .body("Nenhum livro encontrado até o preço de R$ " + precoMaximo);
    }

    String resposta = "EstanteWeb:<br>";

    for (Livro livro : livros) {
        resposta += livro.getTitulo()
                + ", de " + livro.getAutor()
                + " (R$ " + livro.getPreco() + ")<br>";
    }

    return ResponseEntity
            .ok()
            .header("X-Total-Encontrados", String.valueOf(livros.size()))
            .body(resposta);
    }

    @GetMapping("/livros/quem-acessa")
    @ResponseBody
    public String quemAcessa(@RequestHeader("User-Agent") String userAgent) {
        return "Acesso realizado por: " + userAgent;
    }

    @GetMapping(value = "/contato", produces = "text/html")
    @ResponseBody
    public String formularioContato() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                <meta charset="UTF-8">
                <title>Contato</title>
                </head>
                <body>
                    <h1>Formulário de Contato</h1>
                    <form method="post" action="/contato">
                        <label>Nome:</label><br>
                        <input type="text" name="nome"><br><br>
                        <label>Mensagem:</label><br>
                        <textarea name="mensagem"></textarea><br><br>
                        <button type="submit">Enviar</button>
                    </form>
                </body>
                </html>
                """;
    }

    @GetMapping("/livros/novo")
    public String novo() {
        return "livro-novo";
    }

    @GetMapping("/livros/html")
    public String livrosHtml(@RequestParam(required = false) String ordem, Model model) {
        if (ordem == null) {
            model.addAttribute("livros", repositorio.listarTodos());
        } else {
            model.addAttribute("livros", repositorio.listarOrdenadoPor(ordem));
        }
        return "livros";
    }

    // Recebe os dados enviados pelo formulário livro-novo.ftlh
    @PostMapping("/livros")
    public String salvar(
        @RequestParam String titulo,
        @RequestParam String autor,
        @RequestParam int ano,
        @RequestParam double preco,
        Model model) {

    // Verifica se o título está vazio
    if (titulo.isBlank()) {
        model.addAttribute("erro", "O título não pode ficar em branco.");
        model.addAttribute("titulo", titulo);
        model.addAttribute("autor", autor);
        model.addAttribute("ano", ano);
        model.addAttribute("preco", preco);

        return "livro-novo";
    }

    // Verifica se o autor está vazio
    if (autor.isBlank()) {
        model.addAttribute("erro", "O autor não pode ficar em branco.");
        model.addAttribute("titulo", titulo);
        model.addAttribute("autor", autor);
        model.addAttribute("ano", ano);
        model.addAttribute("preco", preco);

        return "livro-novo";
    }

    // Verifica se o preço é menor ou igual a zero
    if (preco <= 0) {
        model.addAttribute("erro", "O preço deve ser maior que zero.");
        model.addAttribute("titulo", titulo);
        model.addAttribute("autor", autor);
        model.addAttribute("ano", ano);
        model.addAttribute("preco", preco);

        return "livro-novo";
    }

    Livro livro = new Livro();

    livro.setTitulo(titulo);
    livro.setAutor(autor);
    livro.setAno(ano);
    livro.setPreco(preco);

    repositorio.salvar(livro);

    return "redirect:/livros/html";
}

    @GetMapping("/livros/{id}")
    public String detalhar(@PathVariable Long id, Model model) {
        Livro livro = repositorio.buscarPorId(id);
        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("livro", livro);
        return "livro";
    }

    // Busca o livro ou responde 404 se ele não existir
    private Livro buscarOu404(Long id) {
        Livro livro = repositorio.buscarPorId(id);
        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado");
        }
        return livro;
    }

    // Mesmas regras usadas no cadastro. Retorna a mensagem de erro, ou null se estiver tudo certo.
    private String validar(String titulo, String autor, double preco) {
        if (titulo.isBlank()) {
            return "O título não pode ficar em branco.";
        }
        if (autor.isBlank()) {
            return "O autor não pode ficar em branco.";
        }
        if (preco <= 0) {
            return "O preço deve ser maior que zero.";
        }
        return null;
    }

    // ===== Corrigir um livro =====

    // Só exibe o formulário já preenchido com os dados atuais (GET não grava nada)
    @GetMapping("/livros/{id}/editar")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        // Só quem está logado pode corrigir livros
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        model.addAttribute("livro", buscarOu404(id));
        return "livro-editar";
    }

    @PostMapping("/livros/{id}/editar")
    public String atualizar(
            @PathVariable Long id,
            @RequestParam String titulo,
            @RequestParam String autor,
            @RequestParam int ano,
            @RequestParam double preco,
            Model model,
            HttpSession session) {

        // Esconder o link não basta: o POST também precisa conferir a sessão
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        buscarOu404(id);

        Livro livro = new Livro();
        livro.setId(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno(ano);
        livro.setPreco(preco);

        String erro = validar(titulo, autor, preco);
        if (erro != null) {
            // Nada é gravado; o formulário volta com o que o usuário digitou
            model.addAttribute("erro", erro);
            model.addAttribute("livro", livro);
            return "livro-editar";
        }

        repositorio.atualizar(livro);

        // Post/Redirect/Get: recarregar a página não repete a alteração
        return "redirect:/livros/" + id;
    }

    // ===== Remover um livro =====

    // Tela de confirmação: mostra qual livro será removido (GET não apaga nada)
    @GetMapping("/livros/{id}/remover")
    public String confirmarRemocao(@PathVariable Long id, Model model) {
        model.addAttribute("livro", buscarOu404(id));
        return "livro-remover";
    }

    // A remoção só acontece via POST, disparado pelo botão do formulário de confirmação
    @PostMapping("/livros/{id}/remover")
    public String remover(@PathVariable Long id) {
        if (repositorio.remover(id) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado");
        }
        return "redirect:/livros/html";
    }

    /*
    @PostMapping("/contato")
    public ResponseEntity<String> receberContato(
            @RequestParam String nome,
            @RequestParam String mensagem) {

        if (mensagem.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("Erro: a mensagem não pode estar em branco.");
        }

        return ResponseEntity
                .ok()
                .body("Obrigado, " + nome + "! Sua mensagem foi recebida.");
    }
    */
}
