//Cancelar
let cancelar = document.getElementById("btnCancelar");
	cancelar.addEventListener("click", function () {
	 	window.location.href = "/venta";
	 });

//Hora, fecha, vendedor
	var fechaActual = new Date().toLocaleDateString('en-CA');
	var horaActual = new Date().toLocaleTimeString('es-ES', {hour: '2-digit', minute: '2-digit'});
	document.getElementById("fecha").value = fechaActual;
	document.getElementById("hora").value = horaActual;
	
	var usuarioText = localStorage.getItem("usuarioText");
    document.getElementById("encarventa").value = usuarioText;
    
//Agregar y borrar filas
function agregarFila() {
  var table = document
    .getElementById("datatable_users")
    .getElementsByTagName("tbody")[0];

  var newRow = table.insertRow(table.rows.length);
  var cell1 = newRow.insertCell(0);
  var cell2 = newRow.insertCell(1);
  var cell3 = newRow.insertCell(2);
  var cell4 = newRow.insertCell(3);
  var cell5 = newRow.insertCell(4);

  cell1.contentEditable = true;
  cell2.contentEditable = true;
  cell3.contentEditable = true;
  cell4.contentEditable = false;
  cell5.contentEditable = false;

  cell2.setAttribute("oninput", "validarNumerico(this);");
  cell3.setAttribute("oninput", "validarNumerico(this);");

  cell5.innerHTML =
    '<button class="btn btn-danger" onclick="eliminarFila(this)"><i class="fas fa-trash"></i> Eliminar</button>';

  var deleteButton = cell5.querySelector("button");
  deleteButton.style.padding = "2px 5px";
  deleteButton.style.display = "inline-block";
  deleteButton.style.width = "70%";
  deleteButton.style.margin = "0 auto";
}

function eliminarFila(button) {
  var row = button.closest("tr");
  row.parentNode.removeChild(row);
  calcularSubtotal();
}

//Cargar productos
async function cargarProductos(uniqueId)
{
  try 
  {
    const response = await fetch("http://localhost:8081/productos/viewproducts");
    const productos = await response.json();
    
    const datalistOptions = document.getElementById(uniqueId);
	
   	productos.forEach((producto) => 
   	{
      const option = document.createElement("option");
      option.value = producto.nombre + " " + 
      				 producto.codCategoria[0].descripcion + " " +
      				 producto.codMarca[0].descripcion + " " + 
      				 producto.unidad[0].descripcion + " - " +
      				 producto.productoId;
      datalistOptions.appendChild(option);
      
      option.dataset.precio = producto.precioVenta;
    });
  } catch (error) {
    console.error('Error durante la carga de productos:', error);
  }
}
document.addEventListener("DOMContentLoaded", cargarProductos("datalistOptions1"));

//campos Cliente
document.addEventListener("DOMContentLoaded", function () {
  var tipoComprobante = document.getElementsByName("opcionVenta");
  var datosCliente = document.querySelector(".datos-clientes");

  function actualizarCamposCliente() {
    var tipoSeleccionado = "";
    for (var i = 0; i < tipoComprobante.length; i++) {
      if (tipoComprobante[i].checked) {
        tipoSeleccionado = tipoComprobante[i].value;
        break;
      }
    }

    // Ocultar todos los conjuntos de campos
    var conjuntosCampos = datosCliente.querySelectorAll(".conjunto-campos");
    conjuntosCampos.forEach(function (conjunto) {
      conjunto.style.display = "none";
    });

    // Mostrar el conjunto de campos correspondiente al tipo seleccionado
    document.getElementById(tipoSeleccionado + "Campos").style.display =
      "block";
  }

  for (var i = 0; i < tipoComprobante.length; i++) {
    tipoComprobante[i].addEventListener("change", actualizarCamposCliente);
  }

  actualizarCamposCliente();
});


//Busqueda del cliente en ruc
async function realizarBusquedaFactura() 
{
    let ruc = document.getElementById("ruc").value;

    try 
    {
		console.log("Estas entrando a rucBD");
        const response = await fetch(`http://localhost:8081/registroventa/cliente/${ruc}`);

        if (!response.ok) 
        {
			console.log("Estas entrando a rucApi");
            await busquedaByRucAPI(ruc);
        } 
        else 
        {
			console.log("Estas en rucBD");
            const detallesFactura = await response.json();
            document.getElementById("raso").value = detallesFactura.razonSocial || '';
        }
    } 
    catch (error) {
        console.error('Error durante la obtención de cliente en la bd', error);
    }
}

async function busquedaByRucAPI(ruc) 
{
    try 
    {
        const response = await fetch(`https://dniruc.apisperu.com/api/v1/ruc/${ruc}?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImRlcmxyZW5naWZvQGdtYWlsLmNvbSJ9.N4REyBveoema1OUAGWs7ZIsU9OrVUNMLM5f-7j1Bb8A`);
		console.log("Estas en rucAPI");
        if (!response.ok) 
        {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        document.getElementById("raso").value = data.razonSocial || '';

        await guardarClienteRuc();
    }
    catch (error) 
    {
        console.error("Error en la solicitud en el API:", error);
    }
}

async function guardarClienteRuc() 
{
    const cliente = 
    {
        "dniRUC": document.getElementById("ruc").value,
        "razonSocial": document.getElementById("raso").value,
    };
    
    console.log("Estas en guardar cliente RUC");
    console.log(cliente);
	
    try 
    {
        const response = await fetch(`http://localhost:8081/registroventa/registro`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(cliente),
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        console.log(data.message);

    } 
    catch (error) {
        console.error('Error al crear cliente', error);
    }
}

//Busqueda del cliente en dni
async function realizarBusquedaBoleta()
{
	let dni = document.getElementById("dni").value;

    try 
    {
		console.log("Estas entrando a dniBD");
        const response = await fetch(`http://localhost:8081/registroventa/cliente/${dni}`);

        if (!response.ok) 
        {
			console.log("Estas entrando a dniApi");
            await busquedaByDniAPI(dni);
        } 
        else 
        {
			console.log("Estas en dniBD");
            const detallesBoleta = await response.json();
            document.getElementById("nombre").value = detallesBoleta.nombre || '';
        }
    } 
    catch (error) {
        console.error('Error durante la obtención de cliente en la bd', error);
    }
}

async function busquedaByDniAPI(dni)
{
	console.log("Estas en busqueda dniAPI")
	const response = await fetch
	(
		"https://dniruc.apisperu.com/api/v1/dni/"+ dni+ "?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImRlcmxyZW5naWZvQGdtYWlsLmNvbSJ9.N4REyBveoema1OUAGWs7ZIsU9OrVUNMLM5f-7j1Bb8A"
	)
    if (!response.ok) 
    {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    document.getElementById("nombre").value = data.nombres || '';

    await guardarClienteDNI();
}

async function guardarClienteDNI() 
{
    const cliente = 
    {
        "dniRUC": document.getElementById("dni").value,
        "nombre": document.getElementById("nombre").value,
    };
    
    console.log("Estas en guardar clienteDNI");
    console.log(cliente);
	
    try 
    {
        const response = await fetch(`http://localhost:8081/registroventa/registro`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(cliente),
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
    } 
    catch (error) {
        console.error('Error al crear cliente', error);
    }
}

//Opciones pago
document.addEventListener("DOMContentLoaded", function () {
  var metodoPagoCheckboxes = document.getElementsByName("opcionPago");
  var pagosTarjetaInput = document.getElementById("pagosTarjeta");
  var pagosYapeInput = document.getElementById("pagosYape");
  var pagosEfectivoInput = document.getElementById("pagosEfectivo");

  // Inicialmente deshabilita los campos de texto
  pagosTarjetaInput.disabled = true;
  pagosYapeInput.disabled = true;
  pagosEfectivoInput.disabled = true;

  function actualizarCamposMetodoPago() {
    // Deshabilita todos los campos de texto al inicio
    pagosTarjetaInput.disabled = true;
    pagosYapeInput.disabled = true;
    pagosEfectivoInput.disabled = true;

    // Habilita los campos correspondientes a los métodos de pago seleccionados
    for (var i = 0; i < metodoPagoCheckboxes.length; i++) {
      if (metodoPagoCheckboxes[i].checked) {
        if (metodoPagoCheckboxes[i].value === "tarjeta") {
          pagosTarjetaInput.disabled = false;
        } else if (metodoPagoCheckboxes[i].value === "yape") {
          pagosYapeInput.disabled = false;
        } else if (metodoPagoCheckboxes[i].value === "efectivo") {
          pagosEfectivoInput.disabled = false;
        }
      }
    }
  }

  // Agrega el evento a cada checkbox de método de pago
  for (var i = 0; i < metodoPagoCheckboxes.length; i++) {
    metodoPagoCheckboxes[i].addEventListener(
      "change",
      actualizarCamposMetodoPago
    );
  }

  // Llamada inicial para asegurar que los campos se actualicen correctamente
  actualizarCamposMetodoPago();
});

//Calculos
function actualizarSubtotalDinamico(cells) {
  var cantidad = parseFloat(cells[1].innerText) || 0;
  var precioUnitario = parseFloat(cells[2].innerText) || 0;

  if (isNaN(cantidad) || isNaN(precioUnitario)) {
    cells[3].innerText = "0.00";
  } else {
    cells[3].innerText = (cantidad * precioUnitario).toFixed(2);
  }
  calcularSubtotal();
}
function validarNumerico(input) {
  var valor = input.innerText.replace(/[^\d.]/g, "");
  var cursorPosition = getCaretPosition(input);

  input.innerText = valor;
  setCaretPosition(input, cursorPosition);
  var cells = input.parentNode.getElementsByTagName("td");
  setTimeout(function () {
    actualizarSubtotalDinamico(cells);
  }, 0);
}
document.addEventListener("input", function (event) {
  var target = event.target;
  if (
    target.tagName === "TD" &&
    (target.cellIndex === 1 || target.cellIndex === 2)
  ) {
    validarNumerico(target);
  }
});

document.addEventListener("input", function (event) {
  var target = event.target;
  if (
    target.tagName === "TD" &&
    (target.cellIndex === 1 || target.cellIndex === 2)
  ) {
    var cells = target.parentNode.getElementsByTagName("td");
    actualizarSubtotalDinamico(cells);
  }
});

function calcularSubtotal() {
  var table = document.getElementById("datatable_users");
  var rows = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");

  var subtotalTotal = 0;

  for (var i = 0; i < rows.length; i++) {
    var cells = rows[i].getElementsByTagName("td");
    var cantidad = parseFloat(cells[1].innerText.replace(/[^\d.]/g, "")) || 0;
    var precioUnitario =
      parseFloat(cells[2].innerText.replace(/[^\d.]/g, "")) || 0;
    subtotalTotal += cantidad * precioUnitario;
  }

  var totalDiv = document.getElementById("total");
  if (totalDiv) {
    totalDiv.value = subtotalTotal.toFixed(2);
  }

  return subtotalTotal;
}

function actualizarSubtotal(cell, cantidad, precioUnitario) {
  if (cantidad !== 0 && precioUnitario !== 0) {
    cell.innerText = (cantidad * precioUnitario).toFixed(2);
  } else {
    // para q el subtotal se actualice a 0 cuando no hay valores en las celsad
    cell.innerText = "0.00";
  }
}

function calcularSubtotalTotal() {
  var table = document.getElementById("datatable_users");
  var rows = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");

  var subtotalTotal = 0;

  for (var i = 0; i < rows.length; i++) {
    var cells = rows[i].getElementsByTagName("td");
    var cantidad = parseFloat(cells[1].innerText.replace(/[^\d.]/g, "")) || 0;
    var precioUnitario =
      parseFloat(cells[2].innerText.replace(/[^\d.]/g, "")) || 0;
    subtotalTotal += cantidad * precioUnitario;
  }

  //suma del subtotal en el div de abajo
  var totalDiv = document.getElementById("total");
  if (totalDiv) {
    totalDiv.value = subtotalTotal.toFixed(2);
  }

  return subtotalTotal;
}

function getCaretPosition(element) {
  return element.innerText.length;
}

function setCaretPosition(element, position) {
  var range = document.createRange();
  var selection = window.getSelection();
  range.setStart(element.childNodes[0], position);
  range.setEnd(element.childNodes[0], position);
  selection.removeAllRanges();
  selection.addRange(range);
}
