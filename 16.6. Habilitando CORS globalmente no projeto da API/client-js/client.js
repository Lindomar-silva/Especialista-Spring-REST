function consultarRestaurante() {
    $.ajax({
        url: "http://api.algafood.local:8080/restaurantes",
        type: "get",
        
        success: function(response) {
            $("#conteudo").text(JSON.stringify(response));
        }
    });
}

function fecharRestaurante() {
    $.ajax({
        url: "http://api.algafood.local:8080/restaurantes/1/fechamento",
        type: "put",

        success: function(response) {
            console.log("Restaurante foi fechado!");
        }
    });
}

$("#botao1").click(consultarRestaurante);
$("#botao2").click(fecharRestaurante);
