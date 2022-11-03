function consultar() {
    $.ajax({
        url: "http://api.algafood.local:8080/formas-pagamentos",
        type: "get",

        success: function (response) {
            preencherTabela(response);
        }
    });
}

function cadastrar() {
    var formaPagamentoJson = JSON.stringify({
        "descricao": $("#campo-descricao").val()
    });

    $.ajax({
        url: "http://api.algafood.local:8080/formas-pagamentos",
        type: "post",
        data: formaPagamentoJson,
        contentType: "application/json",

        success: function (response) {
            consultar();
            alert("Forma de pagamento adiocionada");
        },

        error: function (error) {
            if (error.status == 400) {
                var problem = JSON.parse(error.responseText);
                alert(problem.userMsg);
            } else {
                alert("Erro ao cadastrar forma de pagamento!");
            }
        }
    });
}

function excluir(formaPagamento) {

    $.ajax({
        url: "http://api.algafood.local:8080/formas-pagamentos/" + formaPagamento.id,
        type: "delete",

        success: function (response) {
            consultar();
            alert("Forma de pagamento '" + formaPagamento.descricao + "' removida!");
        },

        error: function (error) {
            // tratando todos os erros da categoria 4xx
            if (error.status >= 404 && error.status <= 499) {
                var problem = JSON.parse(error.responseText);
                alert(problem.userMsg);
            } else {
                alert("Erro ao remover forma de pagamento!");
            }
        }
    });
}

function preencherTabela(formasPagamento) {
    $("#tabela tbody tr").remove();

    $.each(formasPagamento, function (i, formaPagamento) {
        var linha = $("<tr>");

        var linkAcao = $("<a href='#'>")
            .text("Excluir")
            .click(function (event) {
                event.preventDefault();
                excluir(formaPagamento);
            });

        linha.append(
            $("<td>").text(formaPagamento.id),
            $("<td>").text(formaPagamento.descricao),
            $("<td>").append(linkAcao)
        );

        linha.appendTo("#tabela");
    });
}

$("#btn-consultar").click(consultar);
$("#btn-cadastrar").click(cadastrar);