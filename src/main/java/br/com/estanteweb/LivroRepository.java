package br.com.estanteweb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class LivroRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public LivroRepository() {
    }

    public List<Livro> listarTodos() {

        String sql = "SELECT * FROM livro";

        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);

        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            Livro livro = Livro.deMap(linha);
            livros.add(livro);
        }

        return livros;
    }

    // Ordena apenas por colunas da lista permitida; qualquer outro valor
    // cai na listagem padrão (evita SQL injection no ORDER BY)
    public List<Livro> listarOrdenadoPor(String coluna) {

        List<String> colunasPermitidas = List.of("titulo", "autor", "ano", "preco");

        if (coluna == null || !colunasPermitidas.contains(coluna)) {
            return listarTodos();
        }

        String sql = "SELECT * FROM livro ORDER BY " + coluna;

        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);

        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            Livro livro = Livro.deMap(linha);
            livros.add(livro);
        }

        return livros;
    }

    public int contarLivros() {
        String sql = "SELECT COUNT(*) FROM livro";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }   

    public List<Livro> listarAtePreco(double precoMaximo) {

        String sql = "SELECT * FROM livro WHERE preco <= ?";

        List<Map<String, Object>> linhas =
                jdbcTemplate.queryForList(sql, precoMaximo);

        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            Livro livro = Livro.deMap(linha);
            livros.add(livro);
        }

        return livros;
    }
        
        public int salvar(Livro livro) {
        String sql = "INSERT INTO livro (titulo, autor, ano, preco) VALUES (?, ?, ?, ?)";

        return jdbcTemplate.update(
            sql,
            livro.getTitulo(),
            livro.getAutor(),
            livro.getAno(),
            livro.getPreco()
        );
    }

    public Livro buscarPorId(Long id) {
        String sql = "SELECT * FROM livro WHERE id = ?";
        List<Map<String, Object>> linhas =
        jdbcTemplate.queryForList(sql, id);
        if (linhas.isEmpty()) {
            return null;
        }
        return Livro.deMap(linhas.get(0));
    }

    // Atualiza SOMENTE o livro com o id informado (o WHERE id = ? impede
    // que um descuido altere o acervo inteiro). Retorna quantas linhas mudaram.
    public int atualizar(Livro livro) {
        String sql = "UPDATE livro SET titulo = ?, autor = ?, ano = ?, preco = ? WHERE id = ?";

        return jdbcTemplate.update(
            sql,
            livro.getTitulo(),
            livro.getAutor(),
            livro.getAno(),
            livro.getPreco(),
            livro.getId()
        );
    }

    // Remove SOMENTE o livro com o id informado. Retorna quantas linhas foram apagadas.
    public int remover(Long id) {
        String sql = "DELETE FROM livro WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
