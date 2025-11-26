document.getElementById("registroForm").addEventListener("submit", (e) => {
  e.preventDefault();
  const nombre = document.getElementById("nombre").value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  console.log("Datos de registro:", nombre, email, password);
  alert("Cuenta creada exitosamente 🎉");
  window.location.href = "index.html";
});
