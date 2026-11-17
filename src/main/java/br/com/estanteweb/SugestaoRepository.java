package br.com.estanteweb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SugestaoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Lê do banco, o mesmo lugar onde salvar() grava
    public List<Sugestao> listarTodas() {
        String sql = "SELECT nome_cliente, livro_sugerido FROM sugestao ORDER BY id";

        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);

        List<Sugestao> sugestoes = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            sugestoes.add(new Sugestao(
                (String) linha.get("nome_cliente"),
                (String) linha.get("livro_sugerido")
            ));
        }

        return sugestoes;
    }

    public int contarSugestoes() {
        Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sugestao", Integer.class);
        return total == null ? 0 : total;
    }

    public int salvar(Sugestao sugestao) {
        String sql = "INSERT INTO sugestao (nome_cliente, livro_sugerido) VALUES (?, ?)";

        return jdbcTemplate.update(
            sql,
            sugestao.getNomeCliente(),
            sugestao.getLivroSugerido()
        );
    }
}
