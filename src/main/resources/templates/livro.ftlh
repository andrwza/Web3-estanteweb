<#include "cabecalho.ftlh">

<h1>${livro.titulo}</h1>

<p>Autor: ${livro.autor}</p>
<p>Ano: ${livro.ano?c}</p>
<p>Preço: R$ ${livro.preco?c}</p>

<p>
    <a href="/livros/${livro.id?c}/editar">Editar</a>
</p>

<#-- Remover altera dados no servidor, então usa POST via formulário (botão), nunca um link GET -->
<form method="post" action="/livros/${livro.id?c}/remover"
      onsubmit="return confirm('Remover este livro do acervo? Essa ação não pode ser desfeita.');">
    <button type="submit">Remover</button>
</form>

<p><a href="/livros">Voltar para o acervo</a></p>
