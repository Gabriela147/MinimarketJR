//Barra de navegacion
	let sidebarToggle = document.querySelector("#sidebar-toggle");
	
	sidebarToggle.addEventListener("click", function () {
	  document.querySelector("#sidebar").classList.toggle("collapsed");
	});
	
	let usuariosLink = document.getElementById("usuarios");
	usuariosLink.addEventListener("click", function () {
	 	window.location.href = "/verusuario";
	 });
	
	let categoriaLink = document.getElementById("categorias");
	categoriaLink.addEventListener("click", function () {
	 	window.location.href = "/categoria";
	 });
	 
	let productosLink = document.getElementById("productos");
	productosLink.addEventListener("click", function () {
	 	window.location.href = "/producto";
	 });
	 
	let marcasLink = document.getElementById("marcas");
	marcasLink.addEventListener("click", function () {
	 	window.location.href = "/marca";
	 });
	
	let unidadLink = document.getElementById("unidad");
	unidadLink.addEventListener("click", function () {
	 	window.location.href = "/unidad";
	 });
	
	let proveedorLink = document.getElementById("proveedor");
	proveedorLink.addEventListener("click", function () {
	 	window.location.href = "/proveedor";
	 });
	
	let comprasLink = document.getElementById("compras");
	comprasLink.addEventListener("click", function () {
	 	window.location.href = "/compras";
	 });

	let aperturaLink = document.getElementById("apertura");
	aperturaLink.addEventListener("click", function () {
		window.location.href = "/apertura";
	});

	let ventaLink = document.getElementById("pedido");
	ventaLink.addEventListener("click", function () {
        window.location.href = "/venta";
    });

	let ventaReLink = document.getElementById("btnVentas");
	ventaReLink.addEventListener("click", function () {
        window.location.href = "/registrarventa";
    });

//Recuperar el user de la pagina anterior
	var usuarioText = localStorage.getItem("usuarioText");
    document.getElementById("user").innerText = usuarioText;	
	 
//Cerrar Sesion
	let sesionElement = document.getElementById("sesion");
	    sesionElement.addEventListener("click", async () => 
	    {
	        let logoutData = {};
	        try 
	        {
	            const response = await fetch("http://localhost:8081/auth/logout", 
	            {
	                method: "POST",
	                headers: 
	                {
						'Accept': 'application/json',
	                    'Content-Type': 'application/json'
	                },
	                body: JSON.stringify(logoutData)
	            });
	
	            // Verificar si la solicitud fue exitosa (código de estado 2xx)
	            if (response.ok) 
	            {
	                console.log("Logout exitoso");
	                alert("Se cerro la sesion");
	                window.location.href = "/logout";
	            } 
	            else 
	            {
					alert("No se pudo cerrar la sesion");
	                console.error(`Error durante el logout. Código de estado: ${response.status}`);
	            }
	        } 
	        catch (error) 
	        {
	            console.error('Error durante el logout:', error);
	        }
	    });

//Obtener tabla
	let dataTable;
	let dataTableIsInitialized = false;
	
	const dataTableOptions = {
	  //scrollX: "2000px",
	  lengthMenu: [5, 10, 15, 20],
	  columnDefs: [
	    { className: "centered", targets: [0, 1, 2, 3, 4, 5] },
	    { orderable: true, targets: [1] },
	    { searchable: true, targets: [1,2,3,4] },
	    //{ width: "50%", targets: [0] }
	  ],
	  pageLength: 4,
	  destroy: true,
	  language: {
	    lengthMenu: "Mostrar _MENU_ registros por página",
	    zeroRecords: "Ninguna venta encontrada",
	    info: "Mostrando de _START_ a _END_ de un total de _TOTAL_ registros",
	    infoEmpty: "Ninguna venta encontrada",
	    infoFiltered: "(filtrados desde _MAX_ registros totales)",
	    search: "Buscar:",
	    loadingRecords: "Cargando...",
	    paginate: {
	      first: "Primero",
	      last: "Último",
	      next: "Siguiente",
	      previous: "Anterior",
	    },
	  },
	};
	
	const initDataTable = async () => {
	  if (dataTableIsInitialized) {
	    dataTable.destroy();
	  }
	
	  await listVentas();
	  dataTable = $("#datatable_ventas").DataTable(dataTableOptions);
	
	  dataTableIsInitialized = true;
	};
	
	const listVentas = async () => {
  	try {
	    const response = await fetch("http://localhost:8081/ventas/all");
	    const ventas = await response.json();
		console.log(ventas);
	    let content = ``;
	    ventas.forEach((venta) =>
	    {
			
		  let cliente;
	      if (venta.tipocomprobante === "Boleta") 
	      {
	        cliente = venta.dnicliente;
	      } 
	      else if (venta.tipocomprobante === "Factura")
	      {
			cliente = venta.ruccliente;
		  }
	      
	      content += `
	        <tr>
	          <td class="centered">${venta.ventaId}</td>
	          <td class="centered">${cliente}</td>
	          <td class="centered">${venta.fecha}</td>
	          <td class="centered">${venta.hora}</td>
	          <td class="centered">${venta.dniempleado}</td>
	          <td class="centered">${venta.monto}</td>
	          <td class="centered">${venta.tipocomprobante}</td>
	          <td style="text-align: center; vertical-align: middle; height: 50px;">
	              <button id="edit" class="btn btn-sm btn-primary" onclick="verdetalle('${venta.ventaId}')">
	                <i class="fa-solid fa-eye"></i>
	              </button>
	          </td>
	        </tr>`;
	    });
	    tableBody_ventas.innerHTML = content;
	  } catch (ex) {
	    alert(ex);
	  }
	};

//Ver los detalles
async function verdetalle(ventaId) 
{
	try 
	{
    	const response = await fetch(`http://localhost:8081/ventas/${ventaId}`);
    	if (!response.ok) {
      	throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const ventadetalle = await response.json();
    console.log(ventadetalle);
    
	} 
	catch (error) 
	{
    	console.error('Error durante la obtención de detalles de la venta', error);
  	}

}
	
window.addEventListener("load", async () => {
	await initDataTable();
});