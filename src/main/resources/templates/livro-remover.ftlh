<#include "cabecalho.ftlh">

<h2>Remover livro</h2>

<p>Você está prestes a remover este livro do acervo:</p>

<ul>
    <li>Título: ${livro.titulo}</li>
    <li>Autor: ${livro.autor}</li>
    <li>Ano: ${livro.ano?c}</li>
    <li>Preço: R$ ${livro.preco?c}</li>
</ul>

<p>Essa ação não pode ser desfeita.</p>

<form method="post" action="/livros/${livro.id?c}/remover">
    <button type="submit">Sim, remover</button>
</form>

<p><a href="/livros/${livro.id?c}">Não, voltar para a ficha</a></p>
