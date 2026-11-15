<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Acervo - EstanteWeb</title>
</head>
<body>
    <h1>EstanteWeb</h1>

    <#if livros?size == 0>
        <p>Nenhum livro no acervo no momento.</p>
    <#else>
        <ul>
        <#list livros as livro>
            <li><a href="/livros/${livro.id?c}">${livro.titulo}</a>, de ${livro.autor} (R$ ${livro.preco?c})
                — <a href="/livros/${livro.id?c}/editar">Editar</a>
                | <a href="/livros/${livro.id?c}/remover">Remover</a>
            </li>
        </#list>
        </ul>
    </#if>

</body>
</html>
