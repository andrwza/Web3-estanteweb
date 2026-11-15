<#include "cabecalho.ftlh">

<#if erro??>
    <p>${erro}</p>
</#if>

<form method="post" action="/livros">
    <label for="titulo">Título:</label>
    <input type="text" id="titulo" name="titulo" value="${titulo!}">

    <label for="autor">Autor:</label>
    <input type="text" id="autor" name="autor" value="${autor!}">

    <label for="ano">Ano:</label>
    <input type="text" id="ano" name="ano" value="${ano!}">

    <label for="preco">Preço:</label>
    <input type="text" id="preco" name="preco" value="${preco!}">

    <button type="submit">Cadastrar livro</button>
</form>