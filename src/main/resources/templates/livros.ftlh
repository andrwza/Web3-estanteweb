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
            <li>${livro.titulo}, de ${livro.autor} (R$ ${livro.preco?c})
                — <a href="/livros/${livro.id?c}">Detalhes</a>
                <#if usuarioLogado??>
                | <a href="/livros/${livro.id?c}/editar">Editar</a>
                <form method="post" action="/livros/${livro.id?c}/remover" style="display:inline"
                      onsubmit="return confirm('Remover este livro do acervo? Essa ação não pode ser desfeita.');">
                    | <button type="submit">Remover</button>
                </form>
                </#if>
            </li>
        </#list>
        </ul>
    </#if>

</body>
</html>
