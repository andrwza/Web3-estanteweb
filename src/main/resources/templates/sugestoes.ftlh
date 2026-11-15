<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sugestões - EstanteWeb</title>
</head>
<body>

    <h1>Sugestões de livros</h1>

    <#if sugestoes?size == 0>
        <p>Nenhuma sugestão recebida no momento.</p>
    <#else>
        <ul>
        <#list sugestoes as sugestao>
            <li>${sugestao.nomeCliente} sugeriu: ${sugestao.livroSugerido}</li>
        </#list>
        </ul>
    </#if>

</body>
</html>
