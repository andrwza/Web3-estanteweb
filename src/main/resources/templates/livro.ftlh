<#include "cabecalho.ftlh">

<h1>${livro.titulo}</h1>

<p>Autor: ${livro.autor}</p>
<p>Ano: ${livro.ano?c}</p>
<p>Preço: R$ ${livro.preco?c}</p>

<p>
    <a href="/livros/${livro.id?c}/editar">Editar</a> |
    <a href="/livros/${livro.id?c}/remover">Remover</a>
</p>

<p><a href="/livros">Voltar para o acervo</a></p>
