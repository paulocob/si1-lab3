/**
 * Created by andreluiz on 5/26/15.
 */
function erroDeCodigo(mensagem) {
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
}