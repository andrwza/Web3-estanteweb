package br.com.estanteweb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SugestaoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<Sugestao> sugestoes = new ArrayList<>();

    public void adicionar(Sugestao s) {
        sugestoes.add(s);
    }

    public List<Sugestao> listarTodas() {
        return sugestoes;
    }

    public int contarSugestoes() {
        return sugestoes.size();
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