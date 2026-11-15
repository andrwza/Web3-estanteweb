<#include "cabecalho.ftlh">

<h2>Corrigir livro</h2>

<#if erro??>
    <p>${erro}</p>
</#if>

<form method="post" action="/livros/${livro.id?c}/editar">
    <label for="titulo">Título:</label>
    <input type="text" id="titulo" name="titulo" value="${livro.titulo!}">

    <label for="autor">Autor:</label>
    <input type="text" id="autor" name="autor" value="${livro.autor!}">

    <label for="ano">Ano:</label>
    <input type="text" id="ano" name="ano" value="${livro.ano?c}">

    <label for="preco">Preço:</label>
    <input type="text" id="preco" name="preco" value="${livro.preco?c}">

    <button type="submit">Salvar alterações</button>
</form>

<p><a href="/livros/${livro.id?c}">Cancelar</a></p>
