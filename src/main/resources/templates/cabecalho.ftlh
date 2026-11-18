<header>
    <h1>EstanteWeb</h1>

    <nav>
        <a href="/">Início</a> |
        <a href="/sobre">Sobre</a> |
        <a href="/contato">Contato</a>
    </nav>

    <div>
        <#if usuarioLogado??>
            Olá, ${usuarioLogado.nome}
            <form action="/logout" method="post" style="display: inline;">
                <button type="submit">Sair</button>
            </form>
        <#else>
            <a href="/login">Entrar</a>
        </#if>
    </div>
</header>

<hr>
