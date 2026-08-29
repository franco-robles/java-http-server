// 1. Capturamos los elementos que necesitamos de la pantalla
const btnTema = document.getElementById('btn-tema');
const cuerpoPagina = document.body;

// 2. Le decimos al botón que escuche el evento "click"
btnTema.addEventListener('click', function() {
    
    // 3. La magia: "toggle" prende la clase si está apagada, y la apaga si está prendida
    cuerpoPagina.classList.toggle('light-theme');
    
    // 4. Cambiamos el texto y el emoji del botón según el tema activo
    if (cuerpoPagina.classList.contains('light-theme')) {
        btnTema.innerText = 'Modo Oscuro';
    } else {
        btnTema.innerText = 'Modo Claro';
    }
});