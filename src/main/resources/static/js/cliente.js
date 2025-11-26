//FUNCIONES CRUD PARA CLIENTES
import { apiBase } from "../js/api.js";
// ------------------------ CLIENTES ------------------------
document.getElementById("clienteForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const data = {
    nombre: document.getElementById("nombre").value,
    apellido: document.getElementById("apellido").value,
    email: document.getElementById("email").value,
    ine: document.getElementById("ine").value,
    numCliente: document.getElementById("numCliente").value
  };

  try {
    const res = await fetch(`${apiBase}/clientes`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });
    if (!res.ok) throw new Error("Error al crear cliente");
    const nuevoCliente = await res.json();
    document.getElementById("clienteResumen").textContent = `Cliente creado: ${JSON.stringify(nuevoCliente, null, 2)}`;
    getClientes();
  } catch (error) {
    alert(error.message);
  }
});

//-LISTAR CLIENTES POR SU ID-//
async function getClientes() {
  try {
    const res = await fetch(`${apiBase}/clientes`);
    if (!res.ok) throw new Error("No se pudo obtener la lista de clientes");

    const clientes = await res.json();
    const list = document.getElementById("clienteList");
    list.innerHTML = clientes.map(c => `
      <div>
        <strong>ID: ${c.id} </strong> | <p>Nombre: </pS> ${c.nombre} ${c.apellido} - <br> Email: ${c.email}</br>
        Puntos totales: ${c.puntosTotales}
        <br>
        <button onclick="deleteCliente(${c.id})">Eliminar</button>
        </br>
      </div>
      <hr/>
    `).join('');
  } catch (error) {
    alert("Error al cargar clientes: " + error.message);
  }
}

async function deleteCliente(id) {
  try {
    const res = await fetch(`${apiBase}/clientes/${id}`, {
      method: "DELETE"
    });

    if (!res.ok) {
      const msg = await res.text(); // Ver mensaje de error
      throw new Error(msg || "Error desconocido al eliminar cliente");
    }

    alert("Cliente eliminado correctamente!");
    getClientes(); // recargar lista
  } catch (error) {
    alert("Error al eliminar cliente: " + error.message);
  }
}

document.getElementById("actualizarClienteFormNuevo").addEventListener("submit", async (e) => {
  e.preventDefault();
  const id = document.getElementById("updateIdNuevo").value;

  const data = {
    nombre: document.getElementById("updateNombre").value,
    apellido: document.getElementById("updateApellido").value,
    email: document.getElementById("updateEmail").value,
    ine: document.getElementById("updateIne").value,
    numCliente: document.getElementById("updateNumCliente").value
  };

  try {
    const res = await fetch(`${apiBase}/clientes/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });

    if (!res.ok) throw new Error("Error al actualizar cliente");
    const clienteActualizado = await res.json();
    document.getElementById("actualizarClienteResumen").textContent = `Cliente actualizado: ${JSON.stringify(clienteActualizado, null, 2)}`;
    getClientes();
  } catch (error) {
    alert("❌ Error actualizando cliente: " + error.message);
  }
});