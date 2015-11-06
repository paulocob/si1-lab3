/**
 * Created by andreluiz on 5/14/15.
 * Javascript para a checagem da validade de formulário.
 * http://stackoverflow.com/questions/3502354/how-to-check-if-a-textbox-is-empty-using-javascript
 */
function validarFormulario(event)
{
    var radioboxes = document.getElementsByName("ocasional");
    var okay = false;

    var mensagem = "Banda ou ocasional? Escolha uma opção!";

    for(var i=0,l=radioboxes.length;i<l;i++)
        if(radioboxes[i].checked) {
            okay = true;
            mensagem = "";
            break;
        }

    var fb = $("#fbInput").val();

    var email = $("#emailInput").val();

    if(!fb.match(/\S/) && !email.match(/\S/)) {
        okay = false;
        mensagem = "Você deve preencher ao menos uma forma de contato." + "fb: " + fb + "email: " + email;
    }
    if(!okay)
        var n = noty({
            text: mensagem,
            type: "error",
            layout: "bottom",
            theme: "relax",
            animation: {
                open: 'animated flipInX', // Animate.css class names
                close: 'animated flipOutX', // Animate.css class names
                easing: 'swing', // unavailable - no need
                speed: 500 // unavailable - no need
            }
        });
    return okay;
}

function adicionarItem(item, elemento) {
    var novoItem = "<li>$item</li>";
    var nome = elemento.attr("id");
    $("ul[id*='nome']").append(novoItem);

}
var listas = [$( "#instrumentosInput" ), $("#gostosInput"), $("#desgostosInput")];

for(i = 0; i < listas.length; i++) {
    console.log("i = " + i);
    console.log("Entrou aqui com elemento = " + listas[i]);
    listas[i].bind("enterKey", function (e) {
        console.log("bindou?" + listas[i].id);
        adicionarItem(listas[i].val(), listas[i]);
        listas[i].val("");
        return false;
    });

    listas[i].keyup(function (e) {
        if (e.keyCode == 13) {
            console.log("Entrou aqui, deu enter!!");
            e.preventDefault();
            e.cancelBubble = trye;
            e.returnValue = false;
            $(this).trigger("enterKey");
        }
    });

};


