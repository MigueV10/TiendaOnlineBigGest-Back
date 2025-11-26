document.addEventListener("DOMContentLoaded", () => {
  const rol = sessionStorage.getItem("rol");
  if (rol !== "admin") {
    alert("Acceso denegado. Inicia sesión primero.");
    window.location.href = "login.html";
  }
});

export function logout() {
  sessionStorage.clear();
  window.location.href = "login.html";
}

export async function loadModule(moduleName) {
  try {
    const res = await fetch(`views/${moduleName}.html`);
    if (!res.ok) throw new Error(`No se pudo cargar el módulo: ${moduleName}`);
    const html = await res.text();
    const container = document.getElementById("adminContent");
    container.innerHTML = html;

    const oldScript = document.querySelector(`#script-${moduleName}`);
    if (oldScript) oldScript.remove();

    const script = document.createElement("script");
    script.src = `js/${moduleName}.js`;
    script.id = `script-${moduleName}`;
    script.type = "module";
    document.body.appendChild(script);
  } catch (error) {
    console.error(error);
    alert("Error al cargar el módulo.");
  }
}
