//CARGA Y NAVEGACION DE LAS SECCIONES DESDE ADMIN.HTML


document.addEventListener("DOMContentLoaded", () => {
  if (sessionStorage.getItem("rol") !== "admin") {
    window.location.href = "login.html";
  } else {
    loadModule("productos"); // Módulo inicial
  }
});

async function loadModule(moduleName) {
  const res = await fetch(`./${moduleName}.html`);
  const html = await res.text();
  document.getElementById("adminContent").innerHTML = html;

  // Carga el JS del módulo
  const script = document.createElement("script");
  script.src = `../js/${moduleName}.js`;
  document.body.appendChild(script);
}

function logout() {
  sessionStorage.clear();
  window.location.href = "login.html";
}
