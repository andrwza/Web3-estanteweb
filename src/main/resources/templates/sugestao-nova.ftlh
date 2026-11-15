<#include "cabecalho.ftlh">

<h1>Nova sugestão de leitura</h1>

<#if erro??>
    <p>${erro}</p>
</#if>

<form method="post" action="/sugestoes">

    <label for="nomeCliente">Nome:</label>
    <input type="text" id="nomeCliente" name="nomeCliente" value="${nomeCliente!}">

    <br><br>

    <label for="livroSugerido">Livro sugerido:</label>
    <input type="text" id="livroSugerido" name="livroSugerido" value="${livroSugerido!}">

    <br><br>

    <button type="submit">Enviar sugestão</button>

</form>