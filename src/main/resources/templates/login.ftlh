<#include "cabecalho.ftlh">

<h2>Entrar</h2>

<#if erro??>
    <p>${erro}</p>
</#if>

<form method="post" action="/login">
    <label for="email">E-mail:</label>
    <input type="email" id="email" name="email" value="${email!}">

    <label for="senha">Senha:</label>
    <input type="password" id="senha" name="senha">

    <button type="submit">Entrar</button>
</form>
