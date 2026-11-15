<#include "cabecalho.ftlh">
<#if erro??>
    <p>${erro}</p>
</#if>

<h1>Contato</h1>

<form method="post" action="/contato">

<label for="nome">Nome:</label>
<input type="text" id="nome" name="nome" value="${nome!}">

<br><br>

<label for="mensagem">Mensagem:</label>
<textarea id="mensagem" name="mensagem"></textarea>

<br><br>

<button type="submit">Enviar</button>
</form>
